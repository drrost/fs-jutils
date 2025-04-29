package com.rdruzhchenko.fsjutils.exception;

/**
 * Exception thrown when file-related operations fail.
 * This includes file not found, permission issues, and I/O errors.
 */
public class FSFileException extends FSException {

    /**
     * Constructs a new FSFileException with the specified detail message.
     *
     * @param message the detail message
     */
    public FSFileException(String message) {
        super(message);
    }

    /**
     * Constructs a new FSFileException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FSFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FSFileException with the specified cause.
     *
     * @param cause the cause
     */
    public FSFileException(Throwable cause) {
        super(cause);
    }
}
