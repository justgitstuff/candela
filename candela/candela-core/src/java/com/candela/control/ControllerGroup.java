package com.candela.control;

public interface ControllerGroup {

    HouseController getHouseController();

    RoomController getRoomController();

    ChannelController getChannelController();

}
