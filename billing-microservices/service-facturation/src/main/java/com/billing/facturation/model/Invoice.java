package com.billing.facturation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * clientId et subscriptionId viennent des autres services : on garde seulement les identifiants.
 * Contrainte d'unicite (subscriptionId, periode) : une seule facture par abonnement et par periode.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"subscriptionId", "periode"}))
@Getter @Setter @NoArgsConstructor
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private Long subscriptionId;
    private String periode;          // ex : "2026-11"

    @Enumerated(EnumType.STRING)
    private InvoiceStatus statut = InvoiceStatus.BROUILLON;

    private LocalDate dateEmission;
    private LocalDate dateEcheance;

    private BigDecimal montantHT = BigDecimal.ZERO;
    private BigDecimal tva = BigDecimal.ZERO;
    private BigDecimal montantTTC = BigDecimal.ZERO;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceLine> lignes = new ArrayList<>();
}
