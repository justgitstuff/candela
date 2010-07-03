package com.jrako.control.stateful.result;


public class InvalidResult implements RakoCommandResult {

    public static final InvalidResult INSTANCE = new InvalidResult();

    private InvalidResult() {
    }

}
