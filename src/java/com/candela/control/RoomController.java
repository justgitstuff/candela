package com.candela.control;

import com.candela.model.Room;
import com.candela.model.Scene;

public interface RoomController {

    public void on(Room room);

    public void off(Room room);

    public void scene(Scene scene);

}
