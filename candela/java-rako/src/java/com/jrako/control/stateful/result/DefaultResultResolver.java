package com.jrako.control.stateful.result;

import org.apache.commons.io.LineIterator;

import com.jrako.control.RakoException;

public class DefaultResultResolver implements CommandResultResolver {

    @Override
    public RakoCommandResult resolve(LineIterator lines) {
        RakoCommandResult result = InvalidResult.INSTANCE;
        lines.nextLine(); // echo
        String input = lines.nextLine();
        lines.nextLine(); // bracket
        if ("Invalid!".equals(input)) {
            result = InvalidResult.INSTANCE;
        } else if ("OK".equals(input)) {
            result = OkResult.INSTANCE;
        } else {
            throw new RakoException("Unrecognized result: " + input);
        }
        return result;
    }

}
