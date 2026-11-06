package com.billing.abonnements.service;

import com.billing.abonnements.dto.CreateSubscriptionRequest;
import com.billing.abonnements.exception.BusinessException;
import com.billing.abonnements.exception.ResourceNotFoundException;
import com.billing.abonnements.model.Plan;
import com.billing.abonnements.model.Subscription;
import com.billing.abonnements.model.SubscriptionStatus;
import com.billing.abonnements.repository.SubscriptionRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final PlanService planService;

    public SubscriptionService(SubscriptionRepository repository, PlanService planService) {
        this.repository = repository;
        this.planService = planService;
    }

    public List<Subscription> findAll() { return repository.findAll(); }

    public Subscription findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abonnement introuvable : " + id));
    }

    public Subscription create(CreateSubscriptionRequest r) {
        Plan plan = planService.findById(r.planId());
        if (!plan.isActif()) throw new BusinessException("Ce plan n'est plus propose a la vente");
        LocalDate debut = r.dateDebut() != null ? r.dateDebut() : LocalDate.now();
        Subscription s = new Subscription();
        s.setClientId(r.clientId());
        s.setPlan(plan);
        s.setDateDebut(debut);
        s.setProchaineEcheance(debut.plusMonths(1));
        s.setStatut(SubscriptionStatus.ACTIF);
        return repository.save(s);
    }

    public Subscription resilier(Long id) {
        Subscription s = findById(id);
        s.setStatut(SubscriptionStatus.RESILIE);
        s.setDateFin(LocalDate.now());
        return repository.save(s);
    }
}
