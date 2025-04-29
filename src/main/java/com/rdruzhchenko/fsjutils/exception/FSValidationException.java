package com.rdruzhchenko.fsjutils.exception;

/**
 * Exception thrown when validation of input data fails.
 * This includes format validation, range validation, and other validation rules.
 */
public class FSValidationException extends FSException {

    /**
     * Constructs a new FSValidationException with the specified detail message.
     *
     * @param message the detail message
     */
    public FSValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new FSValidationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FSValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FSValidationException with the specified cause.
     *
     * @param cause the cause
     */
    public FSValidationException(Throwable cause) {
        super(cause);
    }
}
