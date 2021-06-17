package com.example.javapaystack.controller;

import com.example.javapaystack.paystackconfiguration.InitializeTransactionRequest;
import com.example.javapaystack.response.HttpResponse;
import com.example.javapaystack.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/redirect")
    public void makePayment(HttpServletResponse httpServletResponse) throws Exception {

        InitializeTransactionRequest request = new InitializeTransactionRequest();

        //get payment url
        String paymentUrl = paymentService.getPaymentAuthorizationUrl(request);

        httpServletResponse.setHeader("Location", paymentUrl);
        httpServletResponse.setStatus(302);
    }

    @PostMapping(value = "/confirm/{orderId}")
    public ResponseEntity<HttpResponse> confirmPayment(@PathVariable int orderId) throws Exception {
        return paymentService.confirmPayment(orderId);
    }

}
