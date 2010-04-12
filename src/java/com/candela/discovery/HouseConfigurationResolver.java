package com.candela.discovery;

import com.candela.House;

public interface HouseConfigurationResolver {

    House resolve() throws ConfigurationException;

}
