package com.jrako.candela;

import com.candela.model.House;

public class RakoHouse implements House {

    static final RakoHouse UNSET = new RakoHouse(-1);

    private final int houseId;

    private RakoHouse(int houseId) {
        this.houseId = houseId;
    }

    static RakoHouse valueOf(int houseId) {
        return new RakoHouse(houseId);
    }

    int getHouseId() {
        return houseId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + houseId;
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
        if (houseId != other.houseId) {
            return false;
        }
        return true;
    }

}
