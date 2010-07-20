package com.candela;

import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.candela.discovery.HomeBrowser;

public interface ControllerFactory {

    void initialise(HomeBrowser browser);

    HouseController newHouseController();

    RoomController newRoomController();

    ChannelController newChannelController();

}
