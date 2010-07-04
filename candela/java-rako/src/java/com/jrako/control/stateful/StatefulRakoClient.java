package com.jrako.control.stateful;

import com.jrako.control.RakoException;
import com.jrako.control.stateful.command.RakoCommand;
import com.jrako.control.stateful.result.RakoCommandResult;

/**
 * Serial / TCP/IP
 */
public interface StatefulRakoClient {

    void connect() throws RakoException;

    RakoCommandResult execute(RakoCommand command) throws RakoException;

    void close() throws RakoException;

}
