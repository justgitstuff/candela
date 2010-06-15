package com.jrako.controller.ethernet;

import org.junit.Test;

import com.candela.ControllerGroupFactory;
import com.candela.DefaultControllerGroupFactory;
import com.candela.DefaultHouseFactory;
import com.candela.House;
import com.candela.HouseFactory;
import com.candela.control.ControllerGroup;
import com.candela.control.HouseController;

public class TelnetRakoControllerHelloWorld {

    @Test
    public void simpleRakoCommand() throws Exception {
        HouseFactory houseFactory = new DefaultHouseFactory();
        House house = houseFactory.newInstance();

        ControllerGroupFactory controlFactory = new DefaultControllerGroupFactory();
        ControllerGroup controllerGroup = controlFactory.newInstance();
        HouseController houseController = controllerGroup.getHouseController();
        houseController.off(house);
    }

}
