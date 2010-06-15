package com.jrako.command.result;

import com.jrako.command.RakoResult;

public class InvalidResult implements RakoResult {

    public static final InvalidResult INSTANCE = new InvalidResult();

    private InvalidResult() {
    }

}
