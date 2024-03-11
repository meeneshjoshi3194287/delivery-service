package com.nagp.deliveryservice.service;

import com.nagp.deliveryservice.dto.RestaurantDTO;
import com.nagp.deliveryservice.entity.*;
import com.nagp.deliveryservice.enums.OrderStatus;
import com.nagp.deliveryservice.enums.UpdateItem;
import com.nagp.deliveryservice.exception.CustomException;
import com.nagp.deliveryservice.model.*;
import com.nagp.deliveryservice.repository.FoodCartRepository;
import com.nagp.deliveryservice.repository.OrderRepository;
import com.nagp.deliveryservice.response.*;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodDeliveryServiceImpl implements FoodDeliveryService {

    @Autowired
    private FoodCartRepository foodCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PAYMENT_SERVICE_URL = "http://payment-service/";
    private static final String RESTAURANTS_MENU_SERVICE_URL = "http://restaurants-and-menu-service/";

    private static final Logger log = LoggerFactory.getLogger(FoodDeliveryServiceImpl.class);

    @Transactional
    public String addItem(AddFoodItem addFoodItem, String username) throws CustomException{
        FoodCart foodCartResult;
        FoodItem foodItem = fetchFoodItemDetails(addFoodItem.getItemId());
        if(foodItem==null) throw new CustomException( "There is no item with this item id.Please provide a valid one.");
        log.info("Quantity available :: "+foodItem.getQuantity());
        if(foodItem.getQuantity()==null) throw new CustomException("Sorry the item is not available right now!");
        if(foodItem.getQuantity() < addFoodItem.getQuantity()) throw new CustomException("Sorry  !!  "+foodItem.getQuantity()+" available right now");
        if (foodItem.getQuantity() <= 0) {
            return "Sorry ! " + addFoodItem.getItemId().toString() + " is not available right now as it is out of stock";
        }
        UpdateItemQuantity updateItemQuantity=UpdateItemQuantity.builder()
                .update("REMOVE")
                .quantity(addFoodItem.getQuantity())
                .itemId(addFoodItem.getItemId())
                .build();
        String updateResult = updateFoodItemQuantity(updateItemQuantity);
        log.info(updateResult);
        if(updateResult.contains("only") || updateResult.contains("'add' or 'remove'")) throw new CustomException(updateResult);
        if (updateResult.contains("decreased"))
            log.info("Item quantity updated !! ");
        FoodCart foodCart = new FoodCart(username, addFoodItem.getItemId(),
                addFoodItem.getQuantity(), OrderStatus.PENDING, foodItem.getPrice());
        Optional<FoodCart> foodCartForUser = foodCartRepository.findByUserNameAndItemIdAndOrderStatus(username, addFoodItem.getItemId(),OrderStatus.PENDING);
        if (foodCartForUser.isPresent() && foodCartForUser.get().getOrderStatus().equals(OrderStatus.PENDING)) {
            foodCartRepository.updateCart(username, addFoodItem.getItemId(), addFoodItem.getQuantity());
            foodCartResult = foodCartForUser.get();
            return "Item added to your cart with cart id : " + foodCartResult.getCartId();
        } else{
            foodCartResult = foodCartRepository.save(foodCart);
            return "Item added to your cart with cart id : " + foodCartResult.getCartId();
        }
    }


    @Transactional
    public String removeItem(String itemId, String username) {
        FoodItem foodItem = fetchFoodItemDetails(itemId);
        Optional<FoodCart> foodCartForGivenItemId = foodCartRepository.findByUserNameAndItemIdAndOrderStatus(username, itemId,OrderStatus.PENDING);
        if (!foodCartForGivenItemId.isPresent()) return "Item doesn't exist in the cart";
        foodCartRepository.removeFromCart(itemId);
        UpdateItemQuantity updateItemQuantity=UpdateItemQuantity.builder()
                .update("ADD")
                .quantity(foodCartForGivenItemId.get().getQuantity())
                .itemId(itemId)
                .build();
        updateFoodItemQuantity(updateItemQuantity);
        return "Item removed from your cart with cart id : " + foodCartForGivenItemId.get().getCartId();
    }

    public CartViewResponse viewCart(String username) {
        Optional<List<FoodCart>> foodCartList = foodCartRepository.findByUserNameAndOrderStatus(username, OrderStatus.PENDING);
        return mapFoodCartToCartViewResponse(foodCartList.get());
    }

    private CartViewResponse mapFoodCartToCartViewResponse(List<FoodCart> foodCartList) {
        if(foodCartList.isEmpty()) return CartViewResponse.builder().cartDataList(new ArrayList<>()).numberOfItems("You don't have anything added to your cart right now").totalPrice("0.0").build();
        CartViewResponse cartViewResponse = new CartViewResponse();

        long numberOfItems = foodCartList.stream().mapToLong(FoodCart::getQuantity).sum();
        cartViewResponse.setNumberOfItems(String.valueOf(numberOfItems));

        double totalPrice = foodCartList.stream().mapToDouble(cart -> cart.getPrice() * cart.getQuantity()).sum();
        cartViewResponse.setTotalPrice(String.valueOf(totalPrice));

        List<CartData> cartDataList = foodCartList.stream().map(this::mapToCartData).collect(Collectors.toList());
        cartViewResponse.setCartDataList(cartDataList);

        return cartViewResponse;
    }

    private CartData mapToCartData(FoodCart foodCart) {
        CartData cartData = new CartData();
        cartData.setItemId(foodCart.getItemId());
        cartData.setItemName(fetchFoodItemDetails(foodCart.getItemId()).getItemName());
        cartData.setQuantity(foodCart.getQuantity());
        cartData.setPrice(foodCart.getPrice());
        cartData.setOrderStatus(foodCart.getOrderStatus());

        // Assuming you have a method to get RestaurantDetails from FoodCart
        Restaurant restaurantDetails = mapToRestaurantDetails(foodCart);
        cartData.setRestaurantDetails(restaurantDetails);

        return cartData;
    }

    private Restaurant mapToRestaurantDetails(FoodCart foodCart) {
        Restaurant restaurant = fetchRestaurantByItemId(foodCart.getItemId());
        Restaurant restaurantDetails = new Restaurant();
        restaurantDetails.setRestaurantName(restaurant.getRestaurantName());
        Address addressDetails = mapToAddressDetails(restaurant);
        restaurantDetails.setAddress(addressDetails);

        return restaurantDetails;
    }

    private Address mapToAddressDetails(Restaurant restaurant) {
        Address addressDetails = new Address();
        addressDetails.setAddressId(restaurant.getAddress().getAddressId());
        addressDetails.setStreetAddress(restaurant.getAddress().getStreetAddress());
        addressDetails.setCity(restaurant.getAddress().getCity());
        addressDetails.setAreaCode(restaurant.getAddress().getAreaCode());
        return addressDetails;
    }

    public OrderResponse confirmOrder(ConfirmOrder confirmOrder, String username) {
        Optional<List<FoodCart>> foodCartList = foodCartRepository.findByUserNameAndOrderStatus(username, OrderStatus.PENDING);
        if (foodCartList.get().size() == 0)
            return OrderResponse.builder().orderId("").status("1").message("You don't have any items on the cart yet.Please add items before proceeding for order confirmation").build();
        Double orderAmount = Double.valueOf(viewCart(username).getTotalPrice());
        Map paymentResponse = makePayment(orderAmount);
        if (paymentResponse.get("message").toString().toLowerCase().contains("success"))
            log.info("===== We have received your order payment of Rs " + orderAmount + ".Thanks for the payment ====");
        else
            log.info("===== Payment failed !! Sorry your order couldn't be placed due to payment failure.Please try again ====");
        Orders order = orderRepository.save(new Orders(foodCartList.get(), paymentResponse.get("transactionId").toString(), orderAmount, confirmOrder.getContactNo(), confirmOrder.getStreetAddress(), confirmOrder.getAreaCode(), confirmOrder.getCity(),OrderStatus.CONFIRMED));
        for (FoodCart foodCart : foodCartList.get()) {
            foodCart.setOrderStatus(OrderStatus.CONFIRMED);
            foodCartRepository.save(foodCart);
        }
        log.info("======== Congratulations !! Your order has been placed !! =======");
        log.info("====== Here is your order id in case of any queries : " + order.getOrderId() + " =====");
        log.info("======== Please find below your order details  =======");
        log.info("======== Order Id  : "+order.getOrderId()+" =======");
        for(FoodCart i :foodCartList.get()) {
            Restaurant restaurant= fetchRestaurantByItemId(i.getItemId());
            FoodItem foodItem = fetchFoodItemDetails(i.getItemId());
            log.info("====== RestaurantName : " +restaurant.getRestaurantName() + " =========");
            log.info("====== Restaurant Address : " + restaurant.getAddress().getStreetAddress() + " =========");
            log.info("======                    : " + restaurant.getAddress().getAreaCode() + " =========");
            log.info("======                    : " + restaurant.getAddress().getCity() + " =========");
            log.info("====== Item  : " + foodItem.getItemName() + " =========");
            log.info("====== Quantity  : " + i.getQuantity() + " =========");
            log.info(sendNotificationToRestaurantService(DeliveryNotification.builder().foodItem(foodItem)
                    .restaurant(restaurant).quantity(i.getQuantity()).build(),username));
        }
        return OrderResponse.builder().status("0").orderId(order.getOrderId()).message("Order confirmed with order id : " + order.getOrderId()).build();
    }

    @Transactional
    public OrderResponse cancelOrder(String orderId){
        Optional<Orders> orderDetails = orderRepository.findById(orderId);
        if(!orderDetails.isPresent()){
            return OrderResponse.builder().orderId(orderId).status("1").message("You don't have any order right now").build();
        }
        if(orderDetails.get().getOrderStatus().equals(OrderStatus.CANCELLED)) {
           return OrderResponse.builder().orderId(orderId).status("1").message("You order with this order id is already cancelled ! ").build();
        }
        List<FoodCart> foodCartList = orderDetails.get().getCartList();
        foodCartList.stream().forEach(
                i->foodCartRepository.updateCartStatus(i.getCartId())
        );
        foodCartList.stream().forEach(i->updateFoodItemQuantity(UpdateItemQuantity.builder().itemId(i.getItemId()).quantity(i.getQuantity()).update("ADD").build()));
        orderRepository.updateOrderStatus(orderId);
        processRefund(TransactionDetails.builder().transactionId(orderDetails.get().getTransactionId()).build());
        return OrderResponse.builder().status("0").orderId(orderId).message("Your order with order id : "+orderId+" is cancelled now.You will get your refund of Rs "+orderDetails.get().getOrderAmount()+" soon").build();
    }

    private Map makePayment(Double orderAmount) {
        PaymentDetails paymentDetails = new PaymentDetails("1231231231231231", "123", orderAmount);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PaymentDetails> entity = new HttpEntity<>(paymentDetails, headers);
        Map paymentResponse = restTemplate.exchange(
                PAYMENT_SERVICE_URL + "/makePayment",
                HttpMethod.POST,
                entity,
                Map.class).getBody();
        return paymentResponse;
    }

    private Map processRefund(TransactionDetails transactionDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<TransactionDetails> entity = new HttpEntity<>(transactionDetails, headers);
        Map paymentResponse = restTemplate.exchange(
                PAYMENT_SERVICE_URL + "/refund",
                HttpMethod.POST,
                entity,
                Map.class).getBody();
        return paymentResponse;
    }

    private String updateFoodItemQuantity(UpdateItemQuantity updateItemQuantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UpdateItemQuantity> entity = new HttpEntity<>(updateItemQuantity, headers);
        String response = restTemplate.exchange(
                RESTAURANTS_MENU_SERVICE_URL + "/food-menu/updateItemQuantity",
                HttpMethod.POST,
                entity,
                String.class).getBody();
        return response;
    }

    private Restaurant fetchRestaurantDetails(String restaurantId) {
        String endpointUrl = RESTAURANTS_MENU_SERVICE_URL + "/restaurant/getRestaurantById?restaurantId=" + restaurantId;
        return restTemplate.getForObject(endpointUrl, Restaurant.class);
    }

    private FoodItem fetchFoodItemDetails(String itemId) {
        String endpointUrl = RESTAURANTS_MENU_SERVICE_URL + "/food-menu/getFoodItemDetails?itemId=" + itemId;
        return restTemplate.getForObject(endpointUrl, FoodItem.class);
    }

    private Restaurant fetchRestaurantByItemId(String itemId) {
        String endpointUrl = RESTAURANTS_MENU_SERVICE_URL + "/food-menu/getRestaurantByItemId?itemId=" + itemId;
        return restTemplate.getForObject(endpointUrl, Restaurant.class);
    }

    private String sendNotificationToRestaurantService(DeliveryNotification deliveryNotification,String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        deliveryNotification.setUsername(username);
        HttpEntity<DeliveryNotification> entity = new HttpEntity<>(deliveryNotification, headers);
        restTemplate.exchange(RESTAURANTS_MENU_SERVICE_URL + "/restaurant/delivery-order-notification",
                HttpMethod.POST,
                entity,
                String.class).getBody();
        return "Restaurant has received your order ";

    }

}
