package com.jrako;

import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;

public class HelloWorldCommand {

    private static final String RAKO_HOST = "192.168.1.78";
    private static final int RAKO_PORT = 9761;

    @Test
    public void simpleRakoCommand() throws Exception {
        TelnetClient client = new TelnetClient();
        client.connect(RAKO_HOST, RAKO_PORT);
        OutputStream outputStream = client.getOutputStream();
        outputStream.write("SC: 1\n".getBytes("US-ASCII"));
        outputStream.flush();
        Thread.sleep(4000);
        outputStream.write("SC: 1\n".getBytes("US-ASCII"));
        outputStream.flush();
        IOUtils.closeQuietly(outputStream);
    }
}
