package com.candela;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public final class DefaultHouseFactory implements HouseFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.house.factory.class";
    private static final String PROPERTIES_FILE_NAME = "candela.propeties";

    @Override
    public House newInstance() throws ConfigurationException {
        House house = null;
        String clazzName = resolveClassName();
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

    private String resolveClassName() {
        String clazzName = System.getProperty(FACTORY_CLASS_NAME_KEY);
        if (StringUtils.isEmpty(clazzName)) {
            InputStream stream = null;
            try {
                stream = getClass().getResourceAsStream(PROPERTIES_FILE_NAME);
                Properties props = new Properties();
                props.load(stream);
                clazzName = props.getProperty(FACTORY_CLASS_NAME_KEY);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(stream);
            }
        }
        return clazzName;
    }

}
