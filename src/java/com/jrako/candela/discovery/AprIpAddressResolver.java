package com.jrako.candela.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;

import com.candela.ConfigurationException;

public class AprIpAddressResolver {

    private static final String BRIDGE_NAME_PROPERTY_KEY = "com.jrako.apr.netbios.name";
    private static final String DEFAULT_BRIDGE_NAME = "RAKOBRIDGE";

    private String bridgeNetbiosName = DEFAULT_BRIDGE_NAME;
    private final NetbiosNameResolver nameResolver;

    public AprIpAddressResolver() {
        nameResolver = new JcifsNetbiosNameResolver();
    }

    public void initialise() {
        updateBridgeName();
    }

    public String resolveIpAddress() throws ConfigurationException {
        String ipAddress = null;
        try {
            InetAddress address = nameResolver.resolveAddress(bridgeNetbiosName);
            ipAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            throw new ConfigurationException("Could not resolve IP address for netbios name: " + bridgeNetbiosName, e);
        }
        return ipAddress;
    }

    private void updateBridgeName() {
        String newName = System.getProperty(BRIDGE_NAME_PROPERTY_KEY);
        if (StringUtils.isNotEmpty(newName)) {
            bridgeNetbiosName = newName;
        }
    }

}
