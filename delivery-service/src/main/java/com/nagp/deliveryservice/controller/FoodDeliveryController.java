package com.nagp.deliveryservice.controller;

import com.nagp.deliveryservice.exception.CustomException;
import com.nagp.deliveryservice.model.AddFoodItem;
import com.nagp.deliveryservice.model.ConfirmOrder;
import com.nagp.deliveryservice.response.CartViewResponse;
import com.nagp.deliveryservice.response.OrderResponse;
import com.nagp.deliveryservice.service.FoodDeliveryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/food-delivery")
public class FoodDeliveryController {

    @Autowired
    FoodDeliveryService foodDeliveryService;

    @Value("${server.port}")
    private int port;

    Logger log = LoggerFactory.getLogger(FoodDeliveryController.class);

    @PostMapping(path =  "/addItemToTheCart", produces = "application/json")
    public String addItem(@RequestBody @Valid AddFoodItem addFoodItem,  @RequestHeader("X-Authenticated-User") String username) throws CustomException,IllegalArgumentException{
        log.info("Working from port " + port + " of delivery-service");
        return foodDeliveryService.addItem(addFoodItem, username);
    }

    @PostMapping(path =  "/removeItemFromTheCart", produces = "application/json")
    public String removeItem(@RequestParam @NotBlank(message = "Item ID is required") String itemId,@RequestHeader("X-Authenticated-User") String username) throws IllegalArgumentException{
        log.info("Working from port " + port + " of delivery-service");
        return foodDeliveryService.removeItem(itemId, username);
    }

    @GetMapping(path =  "/viewItemsInTheCart", produces = "application/json")
    public CartViewResponse viewCart(@RequestHeader("X-Authenticated-User") String username) throws IllegalArgumentException{
        log.info("Working from port " + port + " of delivery-service");
        return foodDeliveryService.viewCart(username);
    }

    @PostMapping(path =  "/confirmOrder", produces = "application/json")
    @HystrixCommand(fallbackMethod = "getConfirmOrderFallbackResponse")
    public OrderResponse confirmOrder(@RequestBody @Valid ConfirmOrder confirmOrder, @RequestHeader("X-Authenticated-User") String username) throws IllegalArgumentException{
        log.info("Working from port " + port + " of delivery-service");
        return foodDeliveryService.confirmOrder(confirmOrder,username);
    }

    @PostMapping(path =  "/cancelOrder", produces = "application/json")
    @HystrixCommand(fallbackMethod = "getCancelOrderFallbackResponse")
    public OrderResponse cancelOrder(@RequestParam String orderId) throws IllegalArgumentException{
        log.info("Working from port " + port + " of delivery-service");
        return foodDeliveryService.cancelOrder(orderId);
    }

    public OrderResponse getConfirmOrderFallbackResponse(@RequestBody @Valid ConfirmOrder confirmOrder, @RequestHeader("X-Authenticated-User") String username) throws IllegalArgumentException{
        log.info("Inside fallback method of order food");
        return OrderResponse.builder().status("1").orderId("").message("Your request has not been processed.In case the amount has debited,refund will be initiated to your payment source within 2-3 working days.").build();
    }

    public OrderResponse getCancelOrderFallbackResponse(@RequestParam String orderId) throws IllegalArgumentException{
        log.info("Inside fallback method of cancel order");
        return OrderResponse.builder().status("1").orderId("").message("Your request has not been processed.Please try again after sometime").build();
    }

}
