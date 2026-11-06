package com.billing.facturation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record LineRequest(
        @NotBlank String description,
        @Min(1) int quantite,
        @NotNull @DecimalMin("0.0") BigDecimal prixUnitaire) { }
