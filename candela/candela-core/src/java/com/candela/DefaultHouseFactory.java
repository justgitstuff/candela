package com.candela;

public final class DefaultHouseFactory extends GenericClassLoadingFactory<House> implements HouseFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.house.factory.class";
    private static final String PROPERTIES_FILE_NAME = "/candela.properties";

    public DefaultHouseFactory() {
        super(FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
    }

}
