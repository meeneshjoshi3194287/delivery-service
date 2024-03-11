package com.nagp.paymentservice.controller;

import com.nagp.paymentservice.model.PaymentDetails;
import com.nagp.paymentservice.model.TransactionDetails;
import com.nagp.paymentservice.response.PaymentResponse;
import com.nagp.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PaymentServiceController {
    Logger logger = LoggerFactory.getLogger(PaymentServiceController.class);
    @Value("${server.port}")
    private int port;
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/makePayment")
    public PaymentResponse makePayment(@RequestBody @Valid PaymentDetails paymentDetails) {
        logger.info("Working from port " + port + " of payment service");
        return paymentService.makePayment(paymentDetails);
    }

    @PostMapping("/refund")
    public PaymentResponse makeRefund(@RequestBody @Valid TransactionDetails transactionDetails) {
        logger.info("Working from port " + port + " of payment service");
        return paymentService.makeRefund(transactionDetails.getTransactionId());
    }
}
