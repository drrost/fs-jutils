package com.rdruzhchenko.fsjutils.exception;

/**
 * Exception thrown when date-related operations fail.
 * This includes parsing errors, format errors, and invalid date operations.
 */
public class FSDateException extends FSException {

    /**
     * Constructs a new FSDateException with the specified detail message.
     *
     * @param message the detail message
     */
    public FSDateException(String message) {
        super(message);
    }

    /**
     * Constructs a new FSDateException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FSDateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FSDateException with the specified cause.
     *
     * @param cause the cause
     */
    public FSDateException(Throwable cause) {
        super(cause);
    }
}
