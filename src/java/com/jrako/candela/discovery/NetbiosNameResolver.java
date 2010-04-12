package com.jrako.candela.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface NetbiosNameResolver {

    InetAddress resolveAddress(String netbiosName) throws UnknownHostException;

}
