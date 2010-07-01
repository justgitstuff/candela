package com.jrako.command.result;

import org.apache.commons.io.LineIterator;

import com.jrako.command.RakoResult;
import com.jrako.command.RakoResultResolver;
import com.jrako.controller.RakoControllerException;

public class MonadicResultResolver implements RakoResultResolver {

    @Override
    public RakoResult resolve(LineIterator lines) {
        RakoResult result = InvalidResult.INSTANCE;
        String echo = lines.nextLine();
        String input = lines.nextLine();
        String bracket = lines.nextLine();
        System.err.println("Should be a bracket: " + bracket);
        if ("Invalid!".equals(input)) {
            result = InvalidResult.INSTANCE;
        } else if ("OK".equals(input)) {
            result = OkResult.INSTANCE;
        } else {
            throw new RakoControllerException("Unrecognized result: " + input);
        }
        return result;
    }

}
