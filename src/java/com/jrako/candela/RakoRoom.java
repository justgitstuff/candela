package com.jrako.candela;

import java.util.List;

import com.candela.model.Channel;
import com.candela.model.House;
import com.candela.model.Room;
import com.candela.model.Scene;

public class RakoRoom implements Room {

    static final RakoRoom UNSET = new RakoRoom(-1);

    private final int roomId;

    private RakoRoom(int roomId) {
        this.roomId = roomId;
    }

    static RakoRoom valueOf(int roomId) {
        return new RakoRoom(roomId);
    }

    int getRoomId() {
        return roomId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + roomId;
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
        if (roomId != other.roomId) {
            return false;
        }
        return true;
    }

    @Override
    public List<Channel> getChannels() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public House getHouse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Scene> getScenes() {
        // TODO Auto-generated method stub
        return null;
    }

}
