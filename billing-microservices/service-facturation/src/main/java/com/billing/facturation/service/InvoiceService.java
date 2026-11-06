package com.billing.facturation.service;

import com.billing.facturation.dto.CreateInvoiceRequest;
import com.billing.facturation.dto.LineRequest;
import com.billing.facturation.exception.BusinessException;
import com.billing.facturation.exception.ResourceNotFoundException;
import com.billing.facturation.model.Invoice;
import com.billing.facturation.model.InvoiceLine;
import com.billing.facturation.model.InvoiceStatus;
import com.billing.facturation.repository.InvoiceRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService {
    private final InvoiceRepository repository;
    private final BigDecimal tvaRate;

    public InvoiceService(InvoiceRepository repository,
                          @Value("${facturation.tva-rate:0.20}") BigDecimal tvaRate) {
        this.repository = repository;
        this.tvaRate = tvaRate;
    }

    public List<Invoice> findAll() { return repository.findAll(); }

    public Invoice findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture introuvable : " + id));
    }

    @Transactional
    public Invoice create(CreateInvoiceRequest r) {
        if (r.subscriptionId() != null
                && repository.existsBySubscriptionIdAndPeriode(r.subscriptionId(), r.periode())) {
            throw new BusinessException("Une facture existe deja pour cet abonnement et cette periode");
        }
        Invoice inv = new Invoice();
        inv.setClientId(r.clientId());
        inv.setSubscriptionId(r.subscriptionId());
        inv.setPeriode(r.periode());
        inv.setDateEcheance(r.dateEcheance());
        inv.setStatut(InvoiceStatus.BROUILLON);

        BigDecimal ht = BigDecimal.ZERO;
        for (LineRequest lr : r.lignes()) {
            InvoiceLine l = new InvoiceLine();
            l.setDescription(lr.description());
            l.setQuantite(lr.quantite());
            l.setPrixUnitaire(lr.prixUnitaire());
            l.setMontant(lr.prixUnitaire().multiply(BigDecimal.valueOf(lr.quantite())));
            l.setInvoice(inv);
            inv.getLignes().add(l);
            ht = ht.add(l.getMontant());
        }
        BigDecimal tva = ht.multiply(tvaRate).setScale(2, RoundingMode.HALF_UP);
        inv.setMontantHT(ht.setScale(2, RoundingMode.HALF_UP));
        inv.setTva(tva);
        inv.setMontantTTC(inv.getMontantHT().add(tva));
        return repository.save(inv);
    }

    /** Refuse les transitions interdites (ex : annuler une facture PAYEE). */
    @Transactional
    public Invoice changeStatus(Long id, InvoiceStatus target) {
        Invoice inv = findById(id);
        if (!inv.getStatut().canTransitionTo(target)) {
            throw new BusinessException("Transition interdite : " + inv.getStatut() + " -> " + target);
        }
        inv.setStatut(target);
        if (target == InvoiceStatus.EMISE) inv.setDateEmission(LocalDate.now());
        return repository.save(inv);
    }
}
