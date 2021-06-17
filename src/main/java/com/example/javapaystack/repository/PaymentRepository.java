package com.example.javapaystack.repository;

import com.example.javapaystack.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findPaymentByOrderId(int orderId);
    List<Payment> findAll();
}
