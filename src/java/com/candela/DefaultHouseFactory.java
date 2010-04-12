package com.candela;


public final class DefaultHouseFactory implements HouseFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.house.factory.class";

    @Override
    public House newInstance() throws ConfigurationException {
        House house = null;
        String clazzName = System.getProperty(FACTORY_CLASS_NAME_KEY);
        try {
            Class<? extends HouseFactory> clazz = (Class<? extends HouseFactory>) Class.forName(clazzName);
            house = newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("TODO", e);
        }
        return house;
    }

    private House newInstance(Class<? extends HouseFactory> clazz) throws ConfigurationException {
        House house = null;
        try {
            HouseFactory delegate = clazz.newInstance();
            house = delegate.newInstance();
        } catch (InstantiationException e) {
            throw new ConfigurationException("TODO", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("TODO", e);
        }
        return house;
    }

}
