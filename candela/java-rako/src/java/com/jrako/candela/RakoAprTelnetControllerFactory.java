package com.jrako.candela;

import com.candela.ConfigurationException;
import com.candela.GenericFactory;
import com.candela.control.ControllerGroup;
import com.jrako.candela.discovery.AprIpAddressResolver;
import com.jrako.controller.PesimisticRakoControllerAdapter;
import com.jrako.controller.ethernet.TelnetRakoController;

public class RakoAprTelnetControllerFactory implements GenericFactory<ControllerGroup> {

    private static final int TELNET_PORT = 9761;

    public ControllerGroup newInstance() throws ConfigurationException {
        AprIpAddressResolver ipAddressResolver = new AprIpAddressResolver();
        ipAddressResolver.initialise();
        String address = ipAddressResolver.resolveIpAddress();
        TelnetRakoController telnet = new TelnetRakoController(address, TELNET_PORT);
        PesimisticRakoControllerAdapter adapter = new PesimisticRakoControllerAdapter(telnet);
        RakoCandelaBridge bridge = new RakoCandelaBridge(adapter);
        return bridge;
    }

}
