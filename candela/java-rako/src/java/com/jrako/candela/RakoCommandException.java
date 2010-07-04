package com.jrako.candela;

import com.jrako.control.RakoException;

public class RakoCommandException extends RakoException {

    private static final long serialVersionUID = -613884695635514600L;

    RakoCommandException(String message) {
        super(message);
    }

    RakoCommandException(String message, Throwable cause) {
        super(message, cause);
    }

}
