package com.jrako.control.stateful.tcpip;

import org.junit.Test;

import com.candela.ControllerFactory;
import com.candela.DefaultControllerFactory;
import com.candela.DefaultHouseFactory;
import com.candela.House;
import com.candela.HouseFactory;
import com.candela.Room;
import com.candela.Scene;
import com.candela.control.RoomController;

public class TelnetRakoControllerHelloWorld {

    @Test
    public void simpleRakoCommand() throws Exception {
        HouseFactory houseFactory = new DefaultHouseFactory();
        House house = houseFactory.newInstance();

        ControllerFactory controlFactory = new DefaultControllerFactory(house);

        Room lounge = house.getRooms().get(2);
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
