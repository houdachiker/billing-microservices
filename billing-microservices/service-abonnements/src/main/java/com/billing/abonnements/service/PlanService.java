package com.billing.abonnements.service;

import com.billing.abonnements.exception.ResourceNotFoundException;
import com.billing.abonnements.model.Plan;
import com.billing.abonnements.repository.PlanRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlanService {
    private final PlanRepository repository;

    public PlanService(PlanRepository repository) { this.repository = repository; }

    public List<Plan> findAll() { return repository.findAll(); }

    public Plan findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan introuvable : " + id));
    }

    public Plan create(Plan plan) {
        plan.setId(null);
        return repository.save(plan);
    }

    public Plan update(Long id, Plan data) {
        Plan p = findById(id);
        p.setNom(data.getNom());
        p.setPrixMensuel(data.getPrixMensuel());
        p.setQuotaUtilisateurs(data.getQuotaUtilisateurs());
        p.setActif(data.isActif());
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
