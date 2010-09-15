package com.jrako.candela;

import org.junit.Before;
import org.junit.Test;

import com.candela.discovery.HomeBrowser;

public class RakoAprTelnetControllerFactoryTest {

    private RakoAprTelnetControllerFactory factory;

    @Before
    public void initialise() {

    }

    @Test(expected = IllegalStateException.class)
    public void newHouseControllerWhenNotInitialised() {
        factory.newHouseController();
    }

    @Test(expected = IllegalStateException.class)
    public void newRoomControllerWhenNotInitialised() {
        factory.newRoomController();
    }

    @Test(expected = IllegalStateException.class)
    public void newChannelControllerWhenNotInitialised() {
        factory.newChannelController();
    }

    @Test(expected = IllegalStateException.class)
    public void newHouseController() {
        factory.newHouseController();
    }

    @Test(expected = IllegalStateException.class)
    public void newRoomController() {
        factory.newRoomController();
    }

    @Test(expected = IllegalStateException.class)
    public void newChannelController() {
        factory.newChannelController();
    }

    @Test(expected = NullPointerException.class)
    public void initialiseFactoryNull() {
        factory.initialise(null);
    }

    @Test
    public void initialiseFactory() {
        HomeBrowser browser = null;
        factory.initialise(browser);
    }

}
