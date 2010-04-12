package com.candela;

import java.util.List;

public interface House {

    public String getName();

    public List<? extends Room> getRooms();

}
