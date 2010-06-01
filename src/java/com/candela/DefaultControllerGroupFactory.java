package com.candela;

import com.candela.control.ControllerGroup;

public class DefaultControllerGroupFactory extends GenericClassLoadingFactory<ControllerGroup> implements
        ControllerGroupFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.controller.factory.class";
    private static final String PROPERTIES_FILE_NAME = "candela.properties";

    public DefaultControllerGroupFactory() {
        super(FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
    }

}
