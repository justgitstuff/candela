package com.candela.discovery;

import com.candela.Channel;
import com.candela.House;
import com.candela.Room;
import com.candela.Scene;

public interface HomeBrowser {

    HomeBrowser gotoHouse(String houseId);

    HomeBrowser gotoRoom(String roomId);

    HomeBrowser gotoScene(String sceneId);

    HomeBrowser gotoChannel(String channelId);

    House getHouse();

    Room getRoom();

    Scene getScene();

    Channel getChannel();

    String getLocation();

}
