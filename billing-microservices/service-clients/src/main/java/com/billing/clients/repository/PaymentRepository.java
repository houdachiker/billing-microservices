package com.billing.clients.repository;

import com.billing.clients.model.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByClientId(Long clientId);
    List<Payment> findByInvoiceId(Long invoiceId);
}
