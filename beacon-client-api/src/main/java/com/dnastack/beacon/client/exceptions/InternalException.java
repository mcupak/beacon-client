package com.dnastack.beacon.client.exceptions;

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class InternalException extends Exception {
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
