package com.candela;

import com.candela.control.ChannelController;
import com.candela.control.Controller;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.candela.discovery.HomeBrowser;

public class DefaultControllerFactory extends GenericClassLoadingFactory<Controller> implements ControllerFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.controller.factory.class";
    private static final String PROPERTIES_FILE_NAME = "/candela.properties";
    private volatile Controller controller;

    public static ControllerFactory newInstance(HomeBrowser browser) {
        ControllerFactory factory = new DefaultControllerFactory();
        factory.initialise(browser);
        return factory;
    }

    private DefaultControllerFactory() {
        super(FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
    }

    @Override
    public synchronized void initialise(HomeBrowser browser) {
        if (controller != null) {
            controller = super.newInstance();
        }
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
