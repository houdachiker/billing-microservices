package com.billing.facturation.model;

/**
 * BROUILLON -> EMISE -> PAYEE
 *              EMISE -> EN_RETARD -> PAYEE
 * BROUILLON / EMISE / EN_RETARD -> ANNULEE (jamais si PAYEE)
 */
public enum InvoiceStatus {
    BROUILLON, EMISE, PAYEE, EN_RETARD, ANNULEE;

    public boolean canTransitionTo(InvoiceStatus target) {
        return switch (this) {
            case BROUILLON -> target == EMISE || target == ANNULEE;
            case EMISE -> target == PAYEE || target == EN_RETARD || target == ANNULEE;
            case EN_RETARD -> target == PAYEE || target == ANNULEE;
            case PAYEE, ANNULEE -> false; // etats finaux
        };
    }
}
