package com.jrako.candela.discovery;

import org.apache.commons.lang.StringUtils;

import com.candela.discovery.HouseConfigurationResolver;
import com.candela.model.House;

public class AprHouseConfigurationResolver implements HouseConfigurationResolver {

    private static final String BRIDGE_NAME_PROPERTY_KEY = "rako.apr.netbios.name";
    private static final String DEFAULT_BRIDGE_NAME = "RAKOBRIDGE";

    private String bridgeNetbiosName = DEFAULT_BRIDGE_NAME;

    private final NetbiosNameResolver nameResolver;

    public AprHouseConfigurationResolver() {
        nameResolver = new JcifsNetbiosNameResolver();
    }

    @Override
    public House resolve() {
        updateBridgeName();
        return null;
    }

    private void updateBridgeName() {
        String newName = System.getProperty(BRIDGE_NAME_PROPERTY_KEY);
        if (StringUtils.isNotEmpty(newName)) {
            bridgeNetbiosName = newName;
        }
    }

}
