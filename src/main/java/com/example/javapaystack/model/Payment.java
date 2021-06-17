package com.example.javapaystack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_ref")
    private String paymentReference;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "confirm_payment")
    private boolean confirmPayment;
}
