package com.candela.discovery;

import com.candela.GenericClassLoadingFactory;
import com.candela.GenericFactory;

public final class DefaultHomeBrowserFactory implements HomeBrowserFactory {

    public static final String FACTORY_CLASS_NAME_KEY = "com.candela.home.browser.factory.class";
    private static final String PROPERTIES_FILE_NAME = "/candela.properties";
    private final HomeBrowserFactory classLoadedBrowserFactory;

    public DefaultHomeBrowserFactory() {
        GenericFactory<HomeBrowserFactory> classLoadedFactory = new GenericClassLoadingFactory<HomeBrowserFactory>(
                FACTORY_CLASS_NAME_KEY, PROPERTIES_FILE_NAME);
        classLoadedBrowserFactory = classLoadedFactory.newInstance();
    }

    @Override
    public HomeBrowser newInstance() {
        return classLoadedBrowserFactory.newInstance();
    }

}
