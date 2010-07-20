package com.jrako.candela.discovery;

import java.util.Map;

import com.candela.Channel;
import com.candela.House;
import com.candela.Room;
import com.candela.Scene;
import com.candela.discovery.HomeBrowser;
import com.google.common.collect.Maps;
import com.jrako.candela.RakoChannel;
import com.jrako.candela.RakoHouse;
import com.jrako.candela.RakoRoom;
import com.jrako.candela.RakoScene;
import com.jrako.control.RakoException;

class RakoHomeBrowser implements HomeBrowser {

    private final Map<String, RakoRoom> rooms = Maps.newHashMap();
    private final Map<RakoRoom, Map<String, RakoScene>> roomScenes = Maps.newHashMap();
    private final Map<RakoRoom, Map<String, RakoChannel>> roomChannels = Maps.newHashMap();

    private final RakoHouse house;
    private RakoRoom room;
    private RakoScene scene;
    private RakoChannel channel;

    RakoHomeBrowser(RakoHouse house) {
        this.house = house;
    }

    @Override
    public HomeBrowser gotoHouse(String houseId) {
        room = null;
        return this;
    }

    @Override
    public HomeBrowser gotoRoom(String roomId) {
        RakoRoom localRoom = rooms.get(roomId);
        if (localRoom == null) {
            throw new RakoException("Room not found: " + roomId + " - location: " + getLocation());
        }
        room = localRoom;
        channel = null;
        scene = null;
        return this;
    }

    @Override
    public HomeBrowser gotoChannel(String channelId) {
        Map<String, RakoChannel> channels = roomChannels.get(room);
        RakoChannel localChannel = channels.get(channelId);
        if (localChannel == null) {
            throw new RakoException("Channel not found: " + channelId + " - location: " + getLocation());
        }
        channel = localChannel;
        scene = null;
        return this;
    }

    @Override
    public HomeBrowser gotoScene(String sceneId) {
        Map<String, RakoScene> scenes = roomScenes.get(room);
        RakoScene localScene = scenes.get(sceneId);
        if (localScene == null) {
            throw new RakoException("Scene not found: " + sceneId + " - location: " + getLocation());
        }
        scene = localScene;
        channel = null;
        return this;
    }

    @Override
    public Channel getChannel() {
        if (channel == null) {
            throw new RakoException("Not at channel: " + getLocation());
        }
        return channel;
    }

    @Override
    public House getHouse() {
        if (house == null) {
            throw new RakoException("Not at house: " + getLocation());
        }
        return house;
    }

    @Override
    public Room getRoom() {
        if (room == null) {
            throw new RakoException("Not at room: " + getLocation());
        }
        return room;
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            throw new RakoException("Not at scene: " + getLocation());
        }
        return scene;
    }

    @Override
    public String getLocation() {
        return getLocation(house, room, scene, channel);
    }

    private String getLocation(House localHouse, Room localRoom, Scene localScene, Channel localChannel) {
        if (localHouse == null) {
            return "/";
        } else if (localRoom == null) {
            return "/house" + localHouse.getName();
        } else if (localChannel != null) {
            return "/house" + localHouse.getName() + "/room" + localRoom.getName() + "/channel/"
                    + localChannel.getName();
        } else if (localScene != null) {
            return "/house" + localHouse.getName() + "/room" + localRoom.getName() + "/scene/" + localScene.getName();
        } else {
            return "/house" + localHouse.getName() + "/room" + localRoom.getName();
        }
    }

}
