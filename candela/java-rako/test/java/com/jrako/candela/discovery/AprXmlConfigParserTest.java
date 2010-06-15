package com.jrako.candela.discovery;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.jrako.candela.RakoChannel;
import com.jrako.candela.RakoHouse;
import com.jrako.candela.RakoRoom;
import com.jrako.candela.RakoScene;

public class AprXmlConfigParserTest {

    @Test
    public void loadConfig() throws Exception {
        String xmlData = FileUtils.readFileToString(new File(
                "test/data/rako/discovery/AprXmlConfigParserTest/lighting-config.xml"));
        AprXmlConfigParser parser = new AprXmlConfigParser(1, xmlData);
        RakoHouse house = (RakoHouse) parser.parseXml();
        assertEquals("House", house.getName());
        assertEquals(1, house.getId());

        List<RakoRoom> rooms = (List<RakoRoom>) house.getRooms();
        assertEquals(2, rooms.size());
        assertEquals("Hall", rooms.get(0).getName());
        assertEquals("Lounge", rooms.get(1).getName());
        assertEquals(4, rooms.get(0).getId());
        assertEquals(5, rooms.get(1).getId());
        assertEquals("Lights", rooms.get(0).getType());
        assertEquals("Lights", rooms.get(1).getType());

        List<RakoChannel> channels = (List<RakoChannel>) rooms.get(0).getChannels();
        assertEquals(3, channels.size());
        assertEquals("Downlights", channels.get(0).getName());
        assertEquals("Wall lights", channels.get(1).getName());
        assertEquals("Cupboard light", channels.get(2).getName());
        assertEquals(1, channels.get(0).getId());
        assertEquals(2, channels.get(1).getId());
        assertEquals(3, channels.get(2).getId());

        List<RakoScene> scenes = (List<RakoScene>) rooms.get(0).getScenes();
        assertEquals(4, scenes.size());
        assertEquals("Day", scenes.get(0).getName());
        assertEquals("Evening", scenes.get(1).getName());
        assertEquals("Night", scenes.get(2).getName());
        assertEquals("Cleaning", scenes.get(3).getName());
        assertEquals(1, scenes.get(0).getId());
        assertEquals(2, scenes.get(1).getId());
        assertEquals(3, scenes.get(2).getId());
        assertEquals(4, scenes.get(3).getId());

        channels = (List<RakoChannel>) rooms.get(1).getChannels();
        assertEquals(4, channels.size());
        assertEquals("Coffee table", channels.get(0).getName());
        assertEquals("Washer R", channels.get(1).getName());
        assertEquals("Washer G", channels.get(2).getName());
        assertEquals("Washer B", channels.get(3).getName());
        assertEquals(1, channels.get(0).getId());
        assertEquals(2, channels.get(1).getId());
        assertEquals(3, channels.get(2).getId());
        assertEquals(4, channels.get(3).getId());

        scenes = (List<RakoScene>) rooms.get(1).getScenes();
        assertEquals(4, scenes.size());
        assertEquals("Day", scenes.get(0).getName());
        assertEquals("Evening", scenes.get(1).getName());
        assertEquals("Movie", scenes.get(2).getName());
        assertEquals("Cleaning", scenes.get(3).getName());
        assertEquals(1, scenes.get(0).getId());
        assertEquals(2, scenes.get(1).getId());
        assertEquals(3, scenes.get(2).getId());
        assertEquals(4, scenes.get(3).getId());
    }

}
