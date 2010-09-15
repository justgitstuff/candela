package com.candela;

import org.junit.Before;
import org.junit.Test;

public class GenericClassLoadingFactoryTest {

    private GenericClassLoadingFactory factory;

    @Before
    public void initialise() {

    }

    @Test
    public void newInstance() {
        Object instance = factory.newInstance();
    }

    @Test(expected = ConfigurationException.class)
    public void newInstanceBadKey() {
        Object instance = factory.newInstance();
    }

    @Test(expected = ConfigurationException.class)
    public void newInstanceBadPropertyFile() {
        Object instance = factory.newInstance();
    }

    @Test(expected = ClassNotFoundException.class)
    public void newInstanceBadClass() {
        Object instance = factory.newInstance();
    }

}
