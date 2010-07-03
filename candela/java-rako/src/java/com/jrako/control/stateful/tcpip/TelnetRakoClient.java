package com.jrako.control.stateful.tcpip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.net.telnet.TelnetClient;

import com.jrako.control.RakoException;
import com.jrako.control.stateful.command.RakoCommand;
import com.jrako.control.stateful.command.RakoCommandArgument;
import com.jrako.control.stateful.command.RakoCommandType;
import com.jrako.control.stateful.result.RakoCommandResult;
import com.jrako.control.stateful.result.CommandResultResolver;

public class TelnetRakoClient extends AbstractTcpIpRakoClient {

    private final TelnetClient client;
    private InputStream inputStream;
    private OutputStream outputStream;
    private LineIterator lineIterator;

    public TelnetRakoClient(String hostName, int port) {
        super(hostName, port);
        client = new TelnetClient();
    }

    @Override
    public void connect() throws RakoException {
        try {
            client.connect(hostName, port);
            outputStream = client.getOutputStream();
            inputStream = client.getInputStream();
            lineIterator = IOUtils.lineIterator(inputStream, "US-ASCII");
        } catch (SocketException sX) {
            throw new RakoException("Error connecting to Rako Ethernet bridge " + hostName + ":" + port, sX);
        } catch (IOException ioX) {
            throw new RakoException("Error connecting to Rako Ethernet bridge " + hostName + ":" + port, ioX);
        }
    }

    @Override
    public RakoCommandResult execute(RakoCommand command) throws RakoException {
        try {
            if (!client.isConnected()) {
                connect();
            }
            if (commandDoesNotRequireArgument(command)) {
                return internalExecute(command.getType());
            } else {
                return internalExecute(command.getType(), command.getArgument());
            }
        } catch (Throwable t) {
            close();
            throw new RakoException(t);
        }
    }

    @Override
    public void close() throws RakoException {
        try {
            client.disconnect();
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (Exception ignoreX) {
        }
    }

    private RakoCommandResult internalExecute(RakoCommandType command, int value) throws RakoException {
        write(command.getShortCode() + " " + Integer.toString(value) + "\n");
        return readResult(command);
    }

    private RakoCommandResult internalExecute(RakoCommandType command) throws RakoException {
        write(command.getShortCode() + "\n");
        return readResult(command);
    }

    private RakoCommandResult readResult(RakoCommandType command) throws RakoException {
        CommandResultResolver resolver = command.getRakoResultResolver();
        RakoCommandResult result = resolver.resolve(lineIterator);
        return result;
    }

    private void write(String commandString) throws RakoException {
        try {
            IOUtils.write(commandString, outputStream, "US-ASCII");
            outputStream.flush();
        } catch (IOException ioX) {
            throw new RakoException(ioX);
        }
    }

    private boolean commandDoesNotRequireArgument(RakoCommand command) {
        return command.getType().getSignature() == RakoCommandArgument.NONE || !command.hasArgument();
    }

}
