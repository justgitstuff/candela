package com.jrako.control.stateful.tcpip;

import static com.jrako.control.stateful.command.RakoCommandType.BAUD;
import static com.jrako.control.stateful.command.RakoCommandType.ECHO;
import static com.jrako.control.stateful.command.RakoCommandType.NOECHO;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.jrako.control.RakoException;
import com.jrako.control.stateful.StatefulRakoClient;
import com.jrako.control.stateful.command.RakoCommand;
import com.jrako.control.stateful.command.RakoCommandType;

abstract public class AbstractTcpIpRakoClient implements StatefulRakoClient {

    private static final Set<RakoCommandType> UNSUPPORTED;
    static {
        UNSUPPORTED = new ImmutableSet.Builder<RakoCommandType>().add(BAUD, ECHO, NOECHO).build();
    }

    final String hostName;
    final int port;

    AbstractTcpIpRakoClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    void validateCommandForController(RakoCommand command) throws RakoException {
        if (UNSUPPORTED.contains(command.getType())) {
            throw new RakoException("Command type '" + command.getType() + "' not supported by "
                    + getClass().getSimpleName() + ".");
        }
    }

}
