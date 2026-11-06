package com.billing.clients.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotNull Long clientId,
        @NotNull Long invoiceId,
        @NotNull @DecimalMin("0.01") BigDecimal montant) { }
