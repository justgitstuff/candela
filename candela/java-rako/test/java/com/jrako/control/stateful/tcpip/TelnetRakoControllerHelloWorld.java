package com.jrako.control.stateful.tcpip;

import org.junit.Test;

import com.candela.ControllerFactory;
import com.candela.DefaultControllerFactory;
import com.candela.Room;
import com.candela.Scene;
import com.candela.control.RoomController;
import com.candela.discovery.DefaultHomeBrowserFactory;
import com.candela.discovery.HomeBrowser;
import com.candela.discovery.HomeBrowserFactory;

public class TelnetRakoControllerHelloWorld {

    @Test
    public void simpleRakoCommand() throws Exception {
        HomeBrowserFactory houseFactory = new DefaultHomeBrowserFactory();
        HomeBrowser browser = houseFactory.newInstance();

        ControllerFactory controlFactory = DefaultControllerFactory.newInstance(browser);

        Room lounge = browser.gotoRoom("lounge").getRoom();
        System.out.println(lounge);

        Scene warm = lounge.getScenes().get(1);
        System.out.println(warm);
        RoomController roomController = controlFactory.newRoomController();
        roomController.setScene(warm);

        Thread.sleep(2000);

        Scene cool = lounge.getScenes().get(0);
        System.out.println(cool);
        roomController.setScene(cool);
    }

}
