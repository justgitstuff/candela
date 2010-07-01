package com.jrako.candela;

import java.util.ArrayList;
import java.util.List;

import com.candela.Channel;
import com.candela.House;
import com.candela.Room;
import com.candela.Scene;
import com.google.common.collect.Lists;

public class RakoRoom implements Room {

    static final RakoRoom UNSET = new RakoRoom();
    static final RakoRoom MASTER = new RakoRoom(0, "All rooms", "master", new ArrayList<RakoScene>(),
            new ArrayList<RakoChannel>());

    private final int id;
    private final String name;
    private final String type;
    private final List<RakoScene> scenes;
    private final List<RakoChannel> channels;
    private RakoHouse house;

    private RakoRoom() {
        this(-1, "", "", new ArrayList<RakoScene>(), new ArrayList<RakoChannel>());
    }

    public RakoRoom(int id, String name, String type, List<RakoScene> scenes, List<RakoChannel> channels) {
        this.id = id;
        this.name = name;
        this.type = type;

        RakoChannel allChannels = new RakoChannel(0, "All channels");
        allChannels.setRoom(this);
        this.channels = Lists.newArrayList(allChannels);
        for (RakoChannel channel : channels) {
            channel.setRoom(this);
            this.channels.add(channel);
        }

        this.scenes = Lists.newArrayList(scenes);
        for (RakoScene scene : scenes) {
            scene.setRoom(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public House getHouse() {
        return house;
    }

    public List<? extends Scene> getScenes() {
        return scenes;
    }

    public List<? extends Channel> getChannels() {
        return channels;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RakoRoom)) {
            return false;
        }
        RakoRoom other = (RakoRoom) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    void setHouse(RakoHouse house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return getName();
    }

}
