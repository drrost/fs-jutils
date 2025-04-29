package com.rdruzhchenko.fsjutils.exception;

/**
 * Exception thrown when string-related operations fail.
 * This includes null strings, empty strings, and format errors.
 */
public class FSStringException extends FSException {

    /**
     * Constructs a new FSStringException with the specified detail message.
     *
     * @param message the detail message
     */
    public FSStringException(String message) {
        super(message);
    }

    /**
     * Constructs a new FSStringException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FSStringException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FSStringException with the specified cause.
     *
     * @param cause the cause
     */
    public FSStringException(Throwable cause) {
        super(cause);
    }
}
