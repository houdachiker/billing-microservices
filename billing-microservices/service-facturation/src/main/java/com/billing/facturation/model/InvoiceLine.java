package com.billing.facturation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class InvoiceLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;   // ex : "Plan Pro - novembre"
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal montant;   // quantite x prixUnitaire

    @ManyToOne(optional = false)
    @JsonIgnore
    private Invoice invoice;
}
