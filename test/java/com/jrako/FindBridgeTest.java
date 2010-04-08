package com.jrako;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jcifs.netbios.NbtAddress;

import org.junit.Test;

public class FindBridgeTest {

    @Test
    public void findBridge() throws UnknownHostException {
        InetAddress addr = NbtAddress.getByName("RAKOBRIDGE").getInetAddress();
        System.out.println(addr.getHostAddress());
    }

}
