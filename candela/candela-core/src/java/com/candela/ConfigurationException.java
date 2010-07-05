package com.candela;

public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -7572915354664945812L;

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
