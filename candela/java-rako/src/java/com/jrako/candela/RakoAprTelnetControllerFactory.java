package com.jrako.candela;

import com.candela.ControllerFactory;
import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.candela.discovery.HomeBrowser;
import com.jrako.candela.discovery.AprIpAddressResolver;
import com.jrako.control.stateful.PesimisticRakoClientAdapter;
import com.jrako.control.stateful.tcpip.TelnetRakoClient;

public class RakoAprTelnetControllerFactory implements ControllerFactory {

    private static final int TELNET_PORT = 9761;

    private RakoCandelaBridge bridge;

    @Override
    public void initialise(HomeBrowser browser) {
        AprIpAddressResolver ipAddressResolver = new AprIpAddressResolver();
        ipAddressResolver.initialise();
        String address = ipAddressResolver.resolveIpAddress();
        TelnetRakoClient telnet = new TelnetRakoClient(address, TELNET_PORT);
        PesimisticRakoClientAdapter adapter = new PesimisticRakoClientAdapter(telnet);
        bridge = new RakoCandelaBridge(adapter, browser);
        bridge.initialise();
    }

    @Override
    public ChannelController newChannelController() {
        return bridge;
    }

    @Override
    public HouseController newHouseController() {
        return bridge;
    }

    @Override
    public RoomController newRoomController() {
        return bridge;
    }

}
