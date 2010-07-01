package com.jrako.command.result;

import org.apache.commons.io.LineIterator;

import com.jrako.command.RakoResult;
import com.jrako.command.RakoResultResolver;
import com.jrako.controller.RakoControllerException;

public class StatusResultResolver implements RakoResultResolver {

    @Override
    public RakoResult resolve(LineIterator lines) {
        RakoResult result = null;
        lines.nextLine(); // echo
        String input = lines.nextLine();
        try {
            result = new StatusResult(input);
            lines.nextLine(); // OK
            lines.nextLine(); // bracket
        } catch (RakoControllerException e) {
            lines.nextLine(); // bracket
            if ("Invalid!".equals(input)) {
                result = InvalidResult.INSTANCE;
            } else if ("OK".equals(input)) {
                result = OkResult.INSTANCE;
            } else {
                throw new RakoControllerException("Unrecognized result: " + input);
            }
        }
        return result;
    }

}
