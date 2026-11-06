package com.billing.clients.exception;

/** Regle metier violee (ex: transition d'etat interdite, doublon). */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
}
