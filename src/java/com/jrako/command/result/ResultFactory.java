package com.jrako.command.result;

import com.jrako.command.RakoCommandType;
import com.jrako.command.RakoResult;
import com.jrako.controller.RakoControllerException;

public class ResultFactory {

    public RakoResult newResult(RakoCommandType command, String input) throws RakoControllerException {
        RakoResult result;
        switch (command) {
        case STATUS:
            result = new StatusResult(input);
            break;
        default:
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
