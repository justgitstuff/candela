package com.candela;

import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.candela.discovery.HomeBrowser;

public class DefaultControllerFactory implements ControllerFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.controller.factory.class";
    private static final String PROPERTIES_FILE_NAME = "/candela.properties";
    private final ControllerFactory classLoadedControllerFactory;

    public static ControllerFactory newInstance(HomeBrowser browser) {
        ControllerFactory factory = new DefaultControllerFactory();
        factory.initialise(browser);
        return factory;
    }

    private DefaultControllerFactory() {
        GenericFactory<ControllerFactory> classLoadedFactory = new GenericClassLoadingFactory<ControllerFactory>(
                FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
        classLoadedControllerFactory = classLoadedFactory.newInstance();
    }

    @Override
    public synchronized void initialise(HomeBrowser browser) {
        classLoadedControllerFactory.initialise(browser);
    }

    public HouseController newHouseController() {
        return classLoadedControllerFactory.newHouseController();
    }

    public RoomController newRoomController() {
        return classLoadedControllerFactory.newRoomController();
    }

    public ChannelController newChannelController() {
        return classLoadedControllerFactory.newChannelController();
    }

}
