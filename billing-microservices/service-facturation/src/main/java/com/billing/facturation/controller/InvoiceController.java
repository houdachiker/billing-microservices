package com.billing.facturation.controller;

import com.billing.facturation.dto.CreateInvoiceRequest;
import com.billing.facturation.model.Invoice;
import com.billing.facturation.model.InvoiceStatus;
import com.billing.facturation.service.InvoiceService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) { this.service = service; }

    @GetMapping
    public List<Invoice> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Invoice findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice create(@Valid @RequestBody CreateInvoiceRequest r) { return service.create(r); }

    /** PATCH /invoices/1/status?statut=EMISE */
    @PatchMapping("/{id}/status")
    public Invoice changeStatus(@PathVariable Long id, @RequestParam InvoiceStatus statut) {
        return service.changeStatus(id, statut);
    }
}
