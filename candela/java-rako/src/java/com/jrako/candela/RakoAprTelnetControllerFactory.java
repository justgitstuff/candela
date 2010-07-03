package com.jrako.candela;

import com.candela.ConfigurationException;
import com.candela.GenericFactory;
import com.candela.control.ControllerGroup;
import com.jrako.candela.discovery.AprIpAddressResolver;
import com.jrako.control.stateful.PesimisticRakoClientAdapter;
import com.jrako.control.stateful.tcpip.TelnetRakoClient;

public class RakoAprTelnetControllerFactory implements GenericFactory<ControllerGroup> {

    private static final int TELNET_PORT = 9761;

    public ControllerGroup newInstance() throws ConfigurationException {
        AprIpAddressResolver ipAddressResolver = new AprIpAddressResolver();
        ipAddressResolver.initialise();
        String address = ipAddressResolver.resolveIpAddress();
        TelnetRakoClient telnet = new TelnetRakoClient(address, TELNET_PORT);
        PesimisticRakoClientAdapter adapter = new PesimisticRakoClientAdapter(telnet);
        RakoCandelaBridge bridge = new RakoCandelaBridge(adapter);
        return bridge;
    }

}
