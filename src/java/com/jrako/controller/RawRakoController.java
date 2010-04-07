package com.jrako.controller;

import com.jrako.command.RakoCommand;
import com.jrako.command.RakoResult;

public interface RawRakoController {

    RakoResult execute(RakoCommand command) throws RakoControllerException;

}
