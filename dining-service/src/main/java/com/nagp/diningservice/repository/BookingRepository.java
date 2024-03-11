package com.nagp.diningservice.repository;

import com.nagp.diningservice.entity.DineBooking;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<DineBooking, String> {

    @Query(value = "SELECT COALESCE(SUM(no_of_guests), 0) FROM dine_booking " +
            "WHERE restaurant_id = :restaurantId " +
            "AND date = :bookingDate " +
            "AND booking_status=0 AND start_time=:startTime AND end_time=:endTime", nativeQuery = true)
    Integer countGuestsForRestaurantOnDate(String restaurantId, String bookingDate, String startTime, String endTime);

    @Modifying
    @Query(value = "update DINE_BOOKING set booking_status=1 where booking_id=?1", nativeQuery = true)
    Integer updateBookingStatus(String bookingId);

    List<DineBooking> findByUsername(String username);
}
