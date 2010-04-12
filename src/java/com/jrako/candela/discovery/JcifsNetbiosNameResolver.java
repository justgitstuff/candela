package com.jrako.candela.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jcifs.netbios.NbtAddress;

public class JcifsNetbiosNameResolver implements NetbiosNameResolver {

    @Override
    public InetAddress resolveAddress(String netbiosName) throws UnknownHostException {
        InetAddress address = NbtAddress.getByName(netbiosName).getInetAddress();
        return address;
    }

}
