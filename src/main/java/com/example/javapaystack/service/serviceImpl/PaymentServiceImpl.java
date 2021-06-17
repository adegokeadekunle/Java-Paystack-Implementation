package com.example.javapaystack.service.serviceImpl;

import com.example.javapaystack.model.Payment;
import com.example.javapaystack.paystackconfiguration.InitializeTransaction;
import com.example.javapaystack.paystackconfiguration.InitializeTransactionRequest;
import com.example.javapaystack.paystackconfiguration.InitializeTransactionResponse;
import com.example.javapaystack.paystackconfiguration.PayStackVerifyTransactionResponse;
import com.example.javapaystack.repository.PaymentRepository;
import com.example.javapaystack.response.HttpResponse;
import com.example.javapaystack.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public String getPaymentAuthorizationUrl(InitializeTransactionRequest request) throws Exception {

        //request to be sent
        request.setEmail("adenusidamilola5@gmail.com");
        //conversion from kobo to naira
        request.setAmount(3500*100);
        request.setCallbackUrl("http://localhost:8080/api/payment/confirm");

        InitializeTransactionResponse res = InitializeTransaction.initTransaction(request);

        String authorizationUrl = res.getData().getAuthorization_url();

        if(!res.getStatus()){
            return null;
        }

        try {
            //save it in the database to check later if user has made payment;
            Payment payment = new Payment();

            int size = paymentRepository.findAll().size();

            payment.setOrderId(size+1);
            payment.setConfirmPayment(false);
            payment.setPaymentReference(res.getData().getReference());

            paymentRepository.save(payment);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return authorizationUrl;
    }

    public ResponseEntity<HttpResponse> confirmPayment(int orderId) throws Exception {

        HttpResponse res = new HttpResponse();

        Optional<Payment> paymentOptional = paymentRepository.findPaymentByOrderId(orderId);

        if(paymentOptional.isEmpty()) {
            res.setStatus(404);
            res.setMessage("!!!Particular order Not Found");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        //payment reference
        String paymentReference = paymentOptional.get().getPaymentReference();

        PayStackVerifyTransactionResponse payStackVerifyTransactionResponse = new PayStackVerifyTransactionResponse();
        payStackVerifyTransactionResponse = payStackVerifyTransactionResponse.verifyTransaction(paymentReference);

        if(payStackVerifyTransactionResponse==null){
            paymentOptional.get().setConfirmPayment(true);
            paymentRepository.save(paymentOptional.get());

            res.setStatus(201);
            res.setMessage("Payment already made on this order");

        }else{

            if (!payStackVerifyTransactionResponse.getData().getStatus().equals("success")){
                res.setStatus(200);
                res.setMessage("Payment has not been made yet on this order");

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
