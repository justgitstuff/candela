package com.jrako.candela;

import java.util.ArrayList;
import java.util.List;

import com.candela.House;
import com.candela.Room;
import com.google.common.collect.Lists;

public class RakoHouse implements House {

    static final RakoHouse UNSET = new RakoHouse();
    static final RakoHouse MASTER = new RakoHouse(0, new ArrayList<RakoRoom>());

    private final int id;
    private final List<RakoRoom> rooms;

    private RakoHouse() {
        this(-1, new ArrayList<RakoRoom>());
    }

    public RakoHouse(int id, List<RakoRoom> rooms) {
        this.id = id;
        RakoRoom allRooms = new RakoRoom(0, "All rooms", "master", new ArrayList<RakoScene>(),
                new ArrayList<RakoChannel>());
        allRooms.setHouse(this);
        this.rooms = Lists.newArrayList(allRooms);
        for (RakoRoom room : rooms) {
            room.setHouse(this);
            this.rooms.add(room);
        }
    }

    @Override
    public List<? extends Room> getRooms() {
        return rooms;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return "House";
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
        if (!(obj instanceof RakoHouse)) {
            return false;
        }
        RakoHouse other = (RakoHouse) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName();
    }

}
