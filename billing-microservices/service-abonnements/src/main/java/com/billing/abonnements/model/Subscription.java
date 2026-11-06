package com.billing.abonnements.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** clientId vient de service-clients : seulement l'identifiant, pas de lien direct entre bases. */
@Entity
@Getter @Setter @NoArgsConstructor
public class Subscription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    @ManyToOne(optional = false)
    private Plan plan;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate prochaineEcheance;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus statut = SubscriptionStatus.ACTIF;
}
