package com.jrako.candela.discovery;

import java.util.List;

import com.candela.House;
import com.google.common.collect.Lists;
import com.jrako.candela.RakoChannel;
import com.jrako.candela.RakoHouse;
import com.jrako.candela.RakoRoom;
import com.jrako.candela.RakoScene;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

class AprXmlConfigParser {

    private final int houseId;

    private final String xmlData;

    AprXmlConfigParser(int houseId, String xmlData) {
        this.houseId = houseId;
        this.xmlData = xmlData;
    }

    House parseXml() {
        final List<RakoRoom> rooms = Lists.newArrayList();
        XMLTag doc = XMLDoc.from(xmlData, true);
        doc.forEach(new CallBack() {
            public void execute(XMLTag roomTag) {
                RakoRoom room = processRoom(roomTag);
                rooms.add(room);
            }
        }, "Room");
        RakoHouse house = new RakoHouse(houseId, rooms);
        return house;
    }

    private RakoRoom processRoom(XMLTag roomTag) {
        final List<RakoScene> scenes = Lists.newArrayList();
        final List<RakoChannel> channels = Lists.newArrayList();
        String title = roomTag.gotoChild("title").getText();
        String type = roomTag.gotoChild("type").getText();
        int id = Integer.parseInt(roomTag.getAttribute("id"));
        roomTag.forEach(new CallBack() {
            public void execute(XMLTag sceneTag) {
                RakoScene scene = processScene(sceneTag);
                scenes.add(scene);
            }
        }, "Scene");
        roomTag.forEach(new CallBack() {
            public void execute(XMLTag channelTag) {
                RakoChannel channel = processChannel(channelTag);
                channels.add(channel);
            }
        }, "Channel");
        RakoRoom room = new RakoRoom(id, title, type, scenes, channels);
        return room;
    }

    private RakoScene processScene(XMLTag sceneTag) {
        int id = Integer.parseInt(sceneTag.getAttribute("id"));
        String name = sceneTag.gotoChild("Name").getText();
        RakoScene scene = new RakoScene(id, name);
        return scene;
    }

    private RakoChannel processChannel(XMLTag channelTag) {
        int id = Integer.parseInt(channelTag.getAttribute("id"));
        String name = channelTag.gotoChild("Name").getText();
        RakoChannel channel = new RakoChannel(id, name);
        return channel;
    }

}
