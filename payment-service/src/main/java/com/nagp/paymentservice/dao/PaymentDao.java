package com.nagp.paymentservice.dao;

import com.nagp.paymentservice.model.PaymentDetails;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class PaymentDao {

    public static HashMap<String, PaymentDetails> paymentDetailsHashMap = new HashMap<>();

    public String addToPaymentDetailsHashMap(PaymentDetails paymentDetails) {
        String transactionId = UUID.randomUUID().toString();
        paymentDetailsHashMap.put(transactionId, paymentDetails);
        return transactionId;
    }


    public Double refundAmount(String transactionId) {
        return paymentDetailsHashMap.get(transactionId).getAmount();
    }
}
