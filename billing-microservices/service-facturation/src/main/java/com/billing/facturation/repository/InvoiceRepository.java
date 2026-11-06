package com.billing.facturation.repository;

import com.billing.facturation.model.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsBySubscriptionIdAndPeriode(Long subscriptionId, String periode);
    List<Invoice> findByClientId(Long clientId);
}
