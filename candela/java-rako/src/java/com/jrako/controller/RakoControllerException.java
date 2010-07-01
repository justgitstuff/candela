package com.jrako.controller;

public class RakoControllerException extends RuntimeException {

    private static final long serialVersionUID = 8499560468496887761L;

    public RakoControllerException() {
    }

    public RakoControllerException(String message) {
        super(message);
    }

    public RakoControllerException(Throwable cause) {
        super(cause);
    }

    public RakoControllerException(String message, Throwable cause) {
        super(message, cause);
    }

}
