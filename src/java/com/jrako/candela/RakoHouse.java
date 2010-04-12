package com.jrako.candela;

import java.util.ArrayList;
import java.util.List;

import com.candela.House;
import com.candela.Room;

public class RakoHouse implements House {

    static final RakoHouse UNSET = new RakoHouse();

    private final int id;
    private final List<? extends Room> rooms;

    private RakoHouse() {
        this(-1, new ArrayList<RakoRoom>());
    }

    public RakoHouse(int id, List<RakoRoom> rooms) {
        this.id = id;
        this.rooms = rooms;
        for (RakoRoom room : rooms) {
            room.setHouse(this);
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

}
