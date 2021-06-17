package com.example.javapaystack.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class PayStackBearer {

     public static Account account;

     @Data
     @AllArgsConstructor
     @NoArgsConstructor
     @ToString
     public static class Account {
        private String name;
        private String accountNumber;
        private String cvv;
        private String issuer;
        private String cardNumber;
        private String expiryMonth;
        private String expiryYear;
        private String country;
        private String zipcode;
    }

}
