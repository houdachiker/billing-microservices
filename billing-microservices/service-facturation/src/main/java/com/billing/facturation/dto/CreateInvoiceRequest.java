package com.billing.facturation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record CreateInvoiceRequest(
        @NotNull Long clientId,
        Long subscriptionId,
        String periode,
        LocalDate dateEcheance,
        @NotEmpty @Valid List<LineRequest> lignes) { }
