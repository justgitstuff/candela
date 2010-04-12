package com.candela.control;

import com.candela.Room;
import com.candela.Scene;

public interface RoomController {

    public void on(Room room);

    public void off(Room room);

    public void scene(Scene scene);

}
