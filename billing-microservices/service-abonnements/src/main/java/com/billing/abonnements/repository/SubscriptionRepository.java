package com.billing.abonnements.repository;

import com.billing.abonnements.model.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByClientId(Long clientId);
}
