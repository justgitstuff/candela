package com.candela;

import com.candela.control.ChannelController;
import com.candela.control.Controller;
import com.candela.control.HouseController;
import com.candela.control.RoomController;

public class DefaultControllerFactory extends GenericClassLoadingFactory<Controller> implements ControllerFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.controller.factory.class";
    private static final String PROPERTIES_FILE_NAME = "/candela.properties";
    private final House[] houses;
    private Controller controller;

    public DefaultControllerFactory(House... houses) {
        super(FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
        this.houses = houses;
    }

    @Override
    public void initialise() {
        Controller controller = super.newInstance();
        controller.initialise(houses);
    }

    public HouseController newHouseController() {
        return (HouseController) controller;
    }

    public RoomController newRoomController() {
        return (RoomController) controller;
    }

    public ChannelController newChannelController() {
        return (ChannelController) controller;
    }

}
