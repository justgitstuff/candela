package com.jrako.controller.ethernet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.net.telnet.TelnetClient;

import com.jrako.command.RakoCommand;
import com.jrako.command.RakoCommandArgument;
import com.jrako.command.RakoCommandType;
import com.jrako.command.RakoResult;
import com.jrako.command.RakoResultResolver;
import com.jrako.controller.RakoControllerException;

public class TelnetRakoController extends RakoEthernetController {

    private final TelnetClient client;
    private InputStream inputStream;
    private OutputStream outputStream;
    private LineIterator lineIterator;

    public TelnetRakoController(String hostName, int port) {
        super(hostName, port);
        client = new TelnetClient();
        client.registerSpyStream(System.err);
    }

    @Override
    public void connect() throws RakoControllerException {
        try {
            client.connect(hostName, port);
            outputStream = client.getOutputStream();
            inputStream = client.getInputStream();
            lineIterator = IOUtils.lineIterator(inputStream, "US-ASCII");
        } catch (SocketException sX) {
            throw new RakoControllerException("Error connecting to Rako Ethernet bridge " + hostName + ":" + port, sX);
        } catch (IOException ioX) {
            throw new RakoControllerException("Error connecting to Rako Ethernet bridge " + hostName + ":" + port, ioX);
        }
    }

    @Override
    public RakoResult execute(RakoCommand command) throws RakoControllerException {
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
            throw new RakoControllerException(t);
        }
    }

    @Override
    public void close() throws RakoControllerException {
        try {
            client.disconnect();
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (Exception ignoreX) {
        }
    }

    private RakoResult internalExecute(RakoCommandType command, int value) throws RakoControllerException {
        write(command.getShortCode() + " " + Integer.toString(value) + "\n");
        return readResult(command);
    }

    private RakoResult internalExecute(RakoCommandType command) throws RakoControllerException {
        write(command.getShortCode() + "\n");
        return readResult(command);
    }

    private RakoResult readResult(RakoCommandType command) throws RakoControllerException {
        RakoResultResolver resolver = command.getRakoResultResolver();
        RakoResult result = resolver.resolve(lineIterator);
        return result;
    }

    private void write(String commandString) throws RakoControllerException {
        try {
            IOUtils.write(commandString, outputStream, "US-ASCII");
            outputStream.flush();
        } catch (IOException ioX) {
            throw new RakoControllerException(ioX);
        }
    }

    private boolean commandDoesNotRequireArgument(RakoCommand command) {
        return command.getType().getSignature() == RakoCommandArgument.NONE || !command.hasArgument();
    }

}
