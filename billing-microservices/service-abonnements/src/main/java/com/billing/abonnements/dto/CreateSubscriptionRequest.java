package com.billing.abonnements.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateSubscriptionRequest(
        @NotNull Long clientId,
        @NotNull Long planId,
        LocalDate dateDebut) { }
