package com.candela;

import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;

public interface ControllerFactory {

    void initialise();

    HouseController newHouseController();

    RoomController newRoomController();

    ChannelController newChannelController();

}
