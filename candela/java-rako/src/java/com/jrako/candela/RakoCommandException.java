package com.jrako.candela;

public class RakoCommandException extends RuntimeException {

    private static final long serialVersionUID = -613884695635514600L;

    RakoCommandException(String message) {
        super(message);
    }

    RakoCommandException(String message, Throwable cause) {
        super(message, cause);
    }

}
