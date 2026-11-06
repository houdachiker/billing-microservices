package com.billing.clients.controller;

import com.billing.clients.dto.CreatePaymentRequest;
import com.billing.clients.model.Payment;
import com.billing.clients.service.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) { this.service = service; }

    @GetMapping
    public List<Payment> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Payment findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@Valid @RequestBody CreatePaymentRequest r) { return service.create(r); }

    @PatchMapping("/{id}/encaisser")
    public Payment encaisser(@PathVariable Long id) { return service.encaisser(id); }

    @PatchMapping("/{id}/annuler")
    public Payment annuler(@PathVariable Long id) { return service.annuler(id); }
}
