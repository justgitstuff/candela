package com.candela.model;

import java.util.List;

public interface Room {

    House getHouse();

    List<Channel> getChannels();

    List<Scene> getScenes();

}
