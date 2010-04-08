package com.jrako.candela.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jcifs.netbios.NbtAddress;

public class JcifsNetbiosNameResolver implements NetbiosNameResolver {

    @Override
    public InetAddress resolveName(String name) throws UnknownHostException {
        InetAddress addr = NbtAddress.getByName(name).getInetAddress();
        return addr;
    }

}
