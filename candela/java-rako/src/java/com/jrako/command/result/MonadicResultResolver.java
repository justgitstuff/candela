package com.jrako.command.result;

import org.apache.commons.io.LineIterator;

import com.jrako.command.RakoResult;
import com.jrako.command.RakoResultResolver;
import com.jrako.controller.RakoControllerException;

public class MonadicResultResolver implements RakoResultResolver {

    @Override
    public RakoResult resolve(LineIterator lines) {
        RakoResult result = InvalidResult.INSTANCE;
        lines.nextLine(); // echo
        String input = lines.nextLine();
        lines.nextLine(); // bracket
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
