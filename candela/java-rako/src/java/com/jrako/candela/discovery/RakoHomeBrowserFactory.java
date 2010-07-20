package com.jrako.candela.discovery;

import com.candela.ConfigurationException;
import com.candela.GenericFactory;
import com.candela.discovery.HomeBrowser;
import com.candela.discovery.HomeBrowserFactory;
import com.jrako.candela.RakoHouse;

public class RakoHomeBrowserFactory implements HomeBrowserFactory, GenericFactory<HomeBrowser> {

    @Override
    public HomeBrowser newInstance() throws ConfigurationException {
        AprHomeConfigurationResolver resolver = new AprHomeConfigurationResolver();
        RakoHouse house = (RakoHouse) resolver.resolve();
        HomeBrowser browser = new RakoHomeBrowser(house);
        return browser;
    }

}
