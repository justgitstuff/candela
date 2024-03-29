package com.candela;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class GenericClassLoadingFactory<T> implements GenericFactory<T> {

    private final String factoryClassNameKey;
    private final String propertiesFileName;

    public GenericClassLoadingFactory(String factoryClassNameKey, String propertiesFileName) {
        this.factoryClassNameKey = factoryClassNameKey;
        this.propertiesFileName = propertiesFileName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T newInstance() {
        T made = null;
        String clazzName = resolveClassName();
        try {
            Class<? extends T> factoryClazz = (Class<? extends T>) Class.forName(clazzName);
            made = newInstance(factoryClazz);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Failed to load class identified by '" + clazzName + "'", e);
        }
        return made;
    }

    private T newInstance(Class<? extends T> factoryClazz) throws ConfigurationException {
        T made = null;
        try {
            made = factoryClazz.newInstance();
        } catch (InstantiationException e) {
            throw new ConfigurationException("Could not instantiate factory of type '" + factoryClazz + "'", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Could not instantiate factory of type '" + factoryClazz + "'", e);
        }
        return made;
    }

    private String resolveClassName() {
        String clazzName = System.getProperty(factoryClassNameKey);
        if (StringUtils.isEmpty(clazzName)) {
            InputStream stream = null;
            try {
                stream = getClass().getResourceAsStream(propertiesFileName);
                Properties props = new Properties();
                props.load(stream);
                clazzName = props.getProperty(factoryClassNameKey);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(stream);
            }
        }
        return clazzName;
    }

}
