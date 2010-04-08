package com.jrako.candela.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface NetbiosNameResolver {

    InetAddress resolveName(String name) throws UnknownHostException;

}
