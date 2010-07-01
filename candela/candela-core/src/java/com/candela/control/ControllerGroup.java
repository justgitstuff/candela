package com.candela.control;

import com.candela.House;

public interface ControllerGroup {

    HouseController getHouseController();

    RoomController getRoomController();

    ChannelController getChannelController();

    void initialise(House... houses);

}
