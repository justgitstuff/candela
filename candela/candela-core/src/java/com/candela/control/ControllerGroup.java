package com.candela.control;

public interface ControllerGroup extends Controller {

    HouseController getHouseController();

    RoomController getRoomController();

    ChannelController getChannelController();

}
