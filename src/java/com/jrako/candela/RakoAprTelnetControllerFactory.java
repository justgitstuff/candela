package com.jrako.candela;

import com.candela.ConfigurationException;
import com.candela.control.ControllerGroup;
import com.jrako.candela.discovery.AprIpAddressResolver;
import com.jrako.controller.PesimisticRakoControllerAdapter;
import com.jrako.controller.ethernet.TelnetRakoController;

public class RakoAprTelnetControllerFactory {

    private static final int HTTP_PORT = 80;

    public ControllerGroup newInstance() throws ConfigurationException {
        AprIpAddressResolver ipAddressResolver = new AprIpAddressResolver();
        ipAddressResolver.initialise();
        String address = ipAddressResolver.resolveIpAddress();
        TelnetRakoController telnet = new TelnetRakoController(address, HTTP_PORT);
        PesimisticRakoControllerAdapter adapter = new PesimisticRakoControllerAdapter(telnet);
        RakoCandelaBridge bridge = new RakoCandelaBridge(adapter);
        return bridge;
    }

}
