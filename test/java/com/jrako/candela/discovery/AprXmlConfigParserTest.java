package com.jrako.candela.discovery;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.candela.House;

public class AprXmlConfigParserTest {

    @Test
    public void loadConfig() throws Exception {
        String xmlData = FileUtils.readFileToString(new File(
                "test/data/rako/discovery/AprXmlConfigParserTest/lighting-config.xml"));
        AprXmlConfigParser parser = new AprXmlConfigParser(1, xmlData);
        House house = parser.parseXml();
        System.out.println(house);
    }

}
