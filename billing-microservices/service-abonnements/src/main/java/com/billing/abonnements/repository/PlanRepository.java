package com.billing.abonnements.repository;

import com.billing.abonnements.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> { }
