package com.rdruzhchenko.fsjutils.exception;

/**
 * Exception thrown when JSON-related operations fail.
 * This includes parsing errors, serialization errors, and deserialization errors.
 */
public class FSJsonException extends FSException {

    /**
     * Constructs a new FSJsonException with the specified detail message.
     *
     * @param message the detail message
     */
    public FSJsonException(String message) {
        super(message);
    }

    /**
     * Constructs a new FSJsonException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FSJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FSJsonException with the specified cause.
     *
     * @param cause the cause
     */
    public FSJsonException(Throwable cause) {
        super(cause);
    }
}
