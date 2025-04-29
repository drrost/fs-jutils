package com.rdruzhchenko.fsjutils.exception;

/**
 * Base exception class for all FastStream Java Utilities exceptions.
 * This class extends RuntimeException to maintain backward compatibility
 * with the existing codebase.
 */
public class FSException extends RuntimeException {

    /**
     * Constructs a new FSException with the specified detail message.
     *
     * @param message the detail message
     */
    public FSException(String message) {
        super(message);
    }

    /**
     * Constructs a new FSException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FSException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FSException with the specified cause.
     *
     * @param cause the cause
     */
    public FSException(Throwable cause) {
        super(cause);
    }
}
