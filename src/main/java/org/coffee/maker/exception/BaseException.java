package org.coffee.maker.exception;

public class BaseException extends RuntimeException {
    private String message;
    public BaseException(String message) {
        super(message);
    }
}
