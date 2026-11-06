package com.billing.clients.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** invoiceId vient de service-facturation : on garde seulement l'identifiant (pas de lien direct entre bases). */
@Entity
@Getter @Setter @NoArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long invoiceId;
    private Long clientId;
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    private PaymentStatus statut = PaymentStatus.EN_ATTENTE;

    private LocalDateTime datePaiement;
}
