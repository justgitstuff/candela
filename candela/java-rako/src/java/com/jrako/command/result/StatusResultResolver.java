package com.jrako.command.result;

import org.apache.commons.io.LineIterator;

import com.jrako.command.RakoResult;
import com.jrako.command.RakoResultResolver;
import com.jrako.controller.RakoControllerException;

public class StatusResultResolver implements RakoResultResolver {

    @Override
    public RakoResult resolve(LineIterator lines) {
        RakoResult result = null;
        String echo = lines.nextLine();
        String input = lines.nextLine();
        try {
            result = new StatusResult(input);
            input = lines.nextLine();
            System.err.println("Should be OK: " + input);
            input = lines.nextLine();
            System.err.println("Should be a bracket: " + input);
        } catch (RakoControllerException e) {
            String bracket = lines.nextLine();
            System.err.println("Should be a bracket: " + bracket);
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
