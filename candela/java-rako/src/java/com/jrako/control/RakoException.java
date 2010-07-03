package com.jrako.control;

public class RakoException extends RuntimeException {

    private static final long serialVersionUID = 8499560468496887761L;

    public RakoException() {
    }

    public RakoException(String message) {
        super(message);
    }

    public RakoException(Throwable cause) {
        super(cause);
    }

    public RakoException(String message, Throwable cause) {
        super(message, cause);
    }

}
