package com.jrako.candela;

import com.candela.Channel;
import com.candela.Room;

public class RakoChannel implements Channel {

    static final RakoChannel UNSET = new RakoChannel();
    static final RakoChannel MASTER = new RakoChannel(0, "All channels");

    private final int id;
    private final String name;
    private RakoRoom room = RakoRoom.UNSET;

    private RakoChannel() {
        this(-1, "");
    }

    public RakoChannel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public String getName() {
        return name;
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
        if (!(obj instanceof RakoChannel)) {
            return false;
        }
        RakoChannel other = (RakoChannel) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    void setRoom(RakoRoom room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return getName();
    }

}
