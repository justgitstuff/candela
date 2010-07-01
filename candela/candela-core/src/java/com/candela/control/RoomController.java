package com.candela.control;

import com.candela.Room;
import com.candela.Scene;

public interface RoomController {

    void off(Room room);

    void on(Room room);

    void setScene(Scene scene);

}
