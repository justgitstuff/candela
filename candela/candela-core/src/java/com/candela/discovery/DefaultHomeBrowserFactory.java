package com.candela.discovery;

import com.candela.GenericClassLoadingFactory;

public final class DefaultHomeBrowserFactory extends GenericClassLoadingFactory<HomeBrowser> implements
        HomeBrowserFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.home.browser.factory.class";
    private static final String PROPERTIES_FILE_NAME = "/candela.properties";

    public DefaultHomeBrowserFactory() {
        super(FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
    }

}
