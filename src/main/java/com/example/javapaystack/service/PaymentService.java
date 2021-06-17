package com.example.javapaystack.service;

import com.example.javapaystack.paystackconfiguration.InitializeTransactionRequest;
import com.example.javapaystack.response.HttpResponse;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    String getPaymentAuthorizationUrl(InitializeTransactionRequest request) throws Exception;
    ResponseEntity<HttpResponse> confirmPayment(int orderId) throws Exception;
}
