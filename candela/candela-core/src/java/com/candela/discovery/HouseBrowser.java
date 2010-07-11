package com.candela.discovery;

import com.candela.Channel;
import com.candela.House;
import com.candela.Room;
import com.candela.Scene;

public interface HouseBrowser {

    House findHouse(String id);

    Room findRoom(String id);

    Scene findScene(String id);

    Channel findChannel(String id);

}
