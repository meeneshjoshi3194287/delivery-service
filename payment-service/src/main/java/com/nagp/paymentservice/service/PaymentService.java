package com.nagp.paymentservice.service;

import com.nagp.paymentservice.dao.PaymentDao;
import com.nagp.paymentservice.model.PaymentDetails;
import com.nagp.paymentservice.response.PaymentResponse;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    Logger log = LoggerFactory.getLogger(PaymentService.class);


    @Autowired
    PaymentDao paymentDao;

    public PaymentResponse makePayment(PaymentDetails paymentDetails) {
        if(paymentDetails.getAmount() >= 0){
            String transactionId=paymentDao.addToPaymentDetailsHashMap(paymentDetails);
            log.info("==== Payment processed with transactionId : "+transactionId+" ====");
            return PaymentResponse.builder().message("Payment processed successfully").transactionId(transactionId).build();
        }
        return PaymentResponse.builder().message("Payment failed").build();
    }

    public PaymentResponse makeRefund(String transactionId) {
        Double refundAmount=paymentDao.refundAmount(transactionId);
        log.info("==== The refund has been initiated for the amount of "+ refundAmount+" to your payment source ====");
        return PaymentResponse.builder().message("Refund processed successfully").transactionId(transactionId).build();
    }
}
