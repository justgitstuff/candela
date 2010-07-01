package com.jrako.controller.ethernet;

import org.junit.Test;

import com.candela.ControllerGroupFactory;
import com.candela.DefaultControllerGroupFactory;
import com.candela.DefaultHouseFactory;
import com.candela.House;
import com.candela.HouseFactory;
import com.candela.Room;
import com.candela.Scene;
import com.candela.control.ControllerGroup;
import com.candela.control.HouseController;
import com.candela.control.RoomController;

public class TelnetRakoControllerHelloWorld {

    @Test
    public void simpleRakoCommand() throws Exception {
        HouseFactory houseFactory = new DefaultHouseFactory();
        House house = houseFactory.newInstance();

        ControllerGroupFactory controlFactory = new DefaultControllerGroupFactory(house);
        ControllerGroup controllerGroup = controlFactory.newInstance();
        HouseController houseController = controllerGroup.getHouseController();
        // houseController.off(house);
        // Thread.sleep(2000);

        Room lounge = house.getRooms().get(2);
        System.out.println(lounge);

        Scene warm = lounge.getScenes().get(1);
        System.out.println(warm);
        RoomController roomController = controllerGroup.getRoomController();
        roomController.setScene(warm);

        Thread.sleep(2000);

        Scene cool = lounge.getScenes().get(0);
        System.out.println(cool);
        roomController.setScene(cool);
    }

}
