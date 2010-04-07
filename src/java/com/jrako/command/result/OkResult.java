package com.jrako.command.result;

import com.jrako.command.RakoResult;

public class OkResult implements RakoResult {

    public static final OkResult INSTANCE = new OkResult();

    private OkResult() {
    }

}
