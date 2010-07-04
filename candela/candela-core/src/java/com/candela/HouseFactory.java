package com.candela;

public interface HouseFactory {

    House newInstance() throws ConfigurationException;

}
