package com.billing.abonnements.controller;

import com.billing.abonnements.dto.CreateSubscriptionRequest;
import com.billing.abonnements.model.Subscription;
import com.billing.abonnements.service.SubscriptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) { this.service = service; }

    @GetMapping
    public List<Subscription> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Subscription findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription create(@Valid @RequestBody CreateSubscriptionRequest r) { return service.create(r); }

    @PatchMapping("/{id}/resilier")
    public Subscription resilier(@PathVariable Long id) { return service.resilier(id); }
}
