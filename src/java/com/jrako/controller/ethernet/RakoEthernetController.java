package com.jrako.controller.ethernet;

import static com.jrako.command.RakoCommandType.BAUD;
import static com.jrako.command.RakoCommandType.ECHO;
import static com.jrako.command.RakoCommandType.NOECHO;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.jrako.command.RakoCommand;
import com.jrako.command.RakoCommandType;
import com.jrako.controller.RakoControllerException;
import com.jrako.controller.RakoController;

abstract public class RakoEthernetController implements RakoController {

    private static final Set<RakoCommandType> UNSUPPORTED;
    static {
        UNSUPPORTED = new ImmutableSet.Builder<RakoCommandType>().add(BAUD, ECHO, NOECHO).build();
    }

    final String hostName;
    final int port;

    RakoEthernetController(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    abstract public void connect() throws RakoControllerException;

    abstract public void close() throws RakoControllerException;

    void validateCommandForController(RakoCommand command) throws RakoControllerException {
        if (UNSUPPORTED.contains(command.getType())) {
            throw new RakoControllerException("Command type '" + command.getType() + "' not supported by "
                    + getClass().getSimpleName() + ".");
        }
    }

}
