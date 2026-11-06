package com.billing.abonnements.controller;

import com.billing.abonnements.model.Plan;
import com.billing.abonnements.service.PlanService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService service;

    public PlanController(PlanService service) { this.service = service; }

    @GetMapping
    public List<Plan> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Plan findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plan create(@Valid @RequestBody Plan plan) { return service.create(plan); }

    @PutMapping("/{id}")
    public Plan update(@PathVariable Long id, @Valid @RequestBody Plan plan) { return service.update(id, plan); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
