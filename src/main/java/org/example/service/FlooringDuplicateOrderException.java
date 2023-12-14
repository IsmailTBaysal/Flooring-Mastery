package org.example.service;

public class FlooringDuplicateOrderException extends Throwable {
    public FlooringDuplicateOrderException(String message) {
        super(message);
    }
    public FlooringDuplicateOrderException(String message,
                                           Throwable cause) {
        super(message, cause);
    }
}
