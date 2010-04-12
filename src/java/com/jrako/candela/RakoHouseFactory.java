package com.jrako.candela;

import com.candela.ConfigurationException;
import com.candela.House;
import com.candela.HouseFactory;
import com.candela.discovery.HouseConfigurationResolver;
import com.jrako.candela.discovery.AprHouseConfigurationResolver;

public class RakoHouseFactory implements HouseFactory {

    @Override
    public House newInstance() throws ConfigurationException {
        HouseConfigurationResolver resolver = new AprHouseConfigurationResolver();
        House house = resolver.resolve();
        return house;
    }

}
