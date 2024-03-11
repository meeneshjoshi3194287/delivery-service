package com.nagp.diningservice.service;


import com.nagp.diningservice.enums.BookingStatus;
import com.nagp.diningservice.entity.DineBooking;
import com.nagp.diningservice.exception.CustomException;
import com.nagp.diningservice.model.DiningModel;
import com.nagp.diningservice.model.Restaurant;
import com.nagp.diningservice.notification.DiningNotification;
import com.nagp.diningservice.repository.BookingRepository;
import com.nagp.diningservice.response.Booking;
import com.nagp.diningservice.response.BookingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    public static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private static final String RESTAURANTS_MENU_SERVICE_URL = "http://restaurants-and-menu-service/";

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }


    public BookingResponse dineBooking(DiningModel diningModel, String username){
        Restaurant restaurant = fetchRestaurantDetails(diningModel.getRestaurantId());
        if(!isValidDateString(diningModel.getDate(),"dd-MM-yyyy"))
            return BookingResponse.builder().bookingId("")
                    .status("1").message("Please provide date in format :dd-MM-yyyy and present or future date").build();
        LocalTime startTime = diningModel.getBookingSlot().getStartTime();
        LocalTime endTime = diningModel.getBookingSlot().getEndTime();
        if (!startTime.isBefore(endTime)) {
            return BookingResponse.builder().bookingId("")
                    .status("1").message("Slot start time can't be equal or greater than end time").build();

        }
        if (startTime.compareTo(restaurant.getAddress().getOpenTime()) < 0 ||
                endTime.compareTo(restaurant.getAddress().getCloseTime()) > 0) {
            return BookingResponse.builder().bookingId("")
                    .status("1").message("This restaurant's operating hours are : " + restaurant.getAddress().getOpenTime() + "-" + restaurant.getAddress().getCloseTime() + ".Please enter the slot timings accordingly").build();
        }
        if (startTime.getMinute() != 0 ||
                endTime.getSecond() != 0) {
            return BookingResponse.builder().bookingId("")
                    .status("1").message("Please enter slot timings in absolute values only e.g. 04:00-05:00 , 05:00-06:00").build();
        }
        if (Duration.between(startTime, endTime).toMinutes() != 60) {
            return BookingResponse.builder().bookingId("")
                    .status("1").message("You can book slot for 1 hour only").build();
        }
        Integer capacity = restaurant.getAddress().getDiningCapacity();
        log.info(capacity.toString());
        Integer countGuestsOnGivenDate = bookingRepository.countGuestsForRestaurantOnDate(diningModel.getRestaurantId(), diningModel.getDate(), startTime.toString(), endTime.toString());
        log.info(String.valueOf(countGuestsOnGivenDate + diningModel.getNoOfGuests()));
        if (Integer.valueOf(capacity) >= countGuestsOnGivenDate + diningModel.getNoOfGuests()) {
            DineBooking booking = new DineBooking(diningModel.getRestaurantId(),
                    username, startTime, endTime, diningModel.getUserEMail(), diningModel.getUserContactNo(), BookingStatus.CONFIRMED, diningModel.getDate(), diningModel.getNoOfGuests());

            DineBooking bookingDetails = bookingRepository.save(booking);
            log.info("=====  Congratulations !! Your booking is confirmed ====");
            log.info("====== Please find below the details of the booking. =====");
            log.info("====== Booking Id         :: " + bookingDetails.getBookingId() + " =====");
            log.info("====== User Name          :: " + username + " =====");
            log.info("====== Booked for date    :: " + diningModel.getDate() + " ======");
            log.info("====== Number of Guests   :: " + diningModel.getNoOfGuests() + " ======");
            log.info("====== Booking Slot       :: " + startTime + "-" + endTime + " ======");
            log.info("====== Contact No         :: " + diningModel.getUserContactNo() + " ======");
            log.info("====== Please find below the restaurant details. ======");
            log.info("====== Restaurant Name    :: " + restaurant.getRestaurantName() + " ======");
            log.info("====== Restaurant Address :: " + restaurant.getAddress().getStreetAddress() + " ======");
            log.info("====== City               :: " + restaurant.getAddress().getCity() + " ======");
            log.info("====== Area Code          :: " + restaurant.getAddress().getAreaCode() + " ======");
            sendNotificationToRestaurantService(DiningNotification.builder()
                    .dineBooking(bookingDetails).restaurant(restaurant).username(username).build());
            return BookingResponse.builder().bookingId(bookingDetails.getBookingId())
                    .status("0").message("Booking confirmed").build();
        } else if (Integer.valueOf(capacity) > countGuestsOnGivenDate) {
            Integer capacityLeft = Integer.valueOf(capacity) - countGuestsOnGivenDate;
            return BookingResponse.builder().bookingId("")
                    .status("1")
                    .message("Sorry!! the restaurant has space left for only " + capacityLeft + "  people in this slot.").build();
        } else {
            return BookingResponse.builder().bookingId("")
                    .status("1")
                    .message("Sorry!! this restaurant is out of space in this slot.").build();
        }
    }


    private Restaurant fetchRestaurantDetails(String restaurantId) {
        String endpointUrl = RESTAURANTS_MENU_SERVICE_URL + "/restaurant/getRestaurantById?restaurantId=" + restaurantId;
        return restTemplate.getForObject(endpointUrl, Restaurant.class);
    }


    @Transactional
    public BookingResponse cancelBooking(String bookingId) {
        if (!bookingRepository.findById(bookingId).isPresent())
            BookingResponse.builder().status("0").message("Booking id doesn't exist").build();
        if (!bookingRepository.updateBookingStatus(bookingId).equals(1))
            return BookingResponse.builder().status("1").message("There was some error processing your request.Please try again later.").build();
        return BookingResponse.builder().status("0").bookingId(bookingId).message("Your booking with booking id " + bookingId + " is cancelled").build();
    }


    private String sendNotificationToRestaurantService(DiningNotification diningNotification) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<DiningNotification> entity = new HttpEntity<>(diningNotification, headers);
        restTemplate.exchange(RESTAURANTS_MENU_SERVICE_URL + "/restaurant/dining-order-notification",
                HttpMethod.POST,
                entity,
                String.class).getBody();
        return "Restaurant has received your order ";

    }

    public List<Booking> viewBookings(String username) {
        List<DineBooking> bookingList = bookingRepository.findByUsername(username);
        return bookingList.stream()
                .map(i -> convertDineBookingToBooking(i))
                .collect(Collectors.toList());
    }

    private Booking convertDineBookingToBooking(DineBooking dineBooking) {
        Booking booking = new Booking();
        booking.setBookingId(dineBooking.getBookingId());
        booking.setRestaurant(fetchRestaurantDetails(dineBooking.getRestaurantId()));
        booking.setDate(dineBooking.getDate());
        booking.setStartTime(dineBooking.getStartTime());
        booking.setEndTime(dineBooking.getEndTime());
        booking.setBookingStatus(dineBooking.getBookingStatus());
        booking.setBookedOn(dineBooking.getBookedOn());
        booking.setNoOfGuests(dineBooking.getNoOfGuests());
        return booking;
    }

    private  boolean isValidDateString(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate parsedDate = LocalDate.parse(dateString, formatter);
            LocalDate currentDate = LocalDate.now();
            return parsedDate.isEqual(currentDate) || parsedDate.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
