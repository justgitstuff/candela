package com.jrako.control.stateful.result;

import org.apache.commons.io.LineIterator;

import com.jrako.control.RakoException;

public class StatusResultResolver implements CommandResultResolver {

    @Override
    public RakoCommandResult resolve(LineIterator lines) {
        RakoCommandResult result = null;
        lines.nextLine(); // echo
        String input = lines.nextLine();
        try {
            result = new StatusResult(input);
            lines.nextLine(); // OK
            lines.nextLine(); // bracket
        } catch (RakoException e) {
            lines.nextLine(); // bracket
            if ("Invalid!".equals(input)) {
                result = InvalidResult.INSTANCE;
            } else if ("OK".equals(input)) {
                result = OkResult.INSTANCE;
            } else {
                throw new RakoException("Unrecognized result: " + input);
            }
        }
        return result;
    }

}
