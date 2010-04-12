package com.candela;

import java.util.List;

public interface Room {

    public String getName();

    public House getHouse();

    public List<? extends Scene> getScenes();

    public List<? extends Channel> getChannels();

}
