package com.billing.clients.service;

import com.billing.clients.dto.CreatePaymentRequest;
import com.billing.clients.exception.BusinessException;
import com.billing.clients.exception.ResourceNotFoundException;
import com.billing.clients.model.Payment;
import com.billing.clients.model.PaymentStatus;
import com.billing.clients.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository repository;
    private final ClientService clientService;

    public PaymentService(PaymentRepository repository, ClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    public List<Payment> findAll() { return repository.findAll(); }

    public Payment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable : " + id));
    }

    public Payment create(CreatePaymentRequest r) {
        clientService.findById(r.clientId()); // le client doit exister
        Payment p = new Payment();
        p.setClientId(r.clientId());
        p.setInvoiceId(r.invoiceId());
        p.setMontant(r.montant());
        p.setStatut(PaymentStatus.EN_ATTENTE);
        return repository.save(p);
    }

    /** Un seul encaissement par paiement, et jamais sur un paiement annule. */
    public Payment encaisser(Long id) {
        Payment p = findById(id);
        if (p.getStatut() == PaymentStatus.ANNULE) throw new BusinessException("Paiement annule : encaissement impossible");
        if (p.getStatut() == PaymentStatus.ENCAISSE) throw new BusinessException("Paiement deja encaisse");
        p.setStatut(PaymentStatus.ENCAISSE);
        p.setDatePaiement(LocalDateTime.now());
        return repository.save(p);
    }

    public Payment annuler(Long id) {
        Payment p = findById(id);
        if (p.getStatut() == PaymentStatus.ENCAISSE) throw new BusinessException("Paiement deja encaisse : annulation impossible");
        p.setStatut(PaymentStatus.ANNULE);
        return repository.save(p);
    }
}
