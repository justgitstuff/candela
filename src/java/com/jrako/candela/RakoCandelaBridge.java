package com.jrako.candela;

import java.util.List;

import com.candela.SceneController;
import com.candela.model.House;
import com.candela.model.Room;
import com.candela.model.Scene;
import com.google.common.collect.Lists;
import com.jrako.command.RakoCommand;
import com.jrako.command.RakoCommandType;
import com.jrako.command.RakoResult;
import com.jrako.command.result.InvalidResult;
import com.jrako.command.result.StatusResult;
import com.jrako.controller.ethernet.RakoEthernetController;

public class RakoCandelaBridge implements SceneController {

    private final RakoHouse controllerHouse = RakoHouse.UNSET;

    private final RakoRoom controllerRoom = RakoRoom.UNSET;

    private final RakoChannel controllerChannel = RakoChannel.UNSET;

    private RakoEthernetController controller;

    @Override
    public void setScene(House house, Room room, Scene scene) {
        RakoResult result = execute(RakoCommandType.STATUS.getDefaultCommand());
        if (!result.equals(InvalidResult.INSTANCE)) {
            StatusResult status = (StatusResult) result;
            controllerHouse = RakoHouse.valueOf(status.getHouse());
            controllerRoom = RakoRoom.valueOf(status.getRoom());
            controllerChannel = RakoChannel.valueOf(status.getChannel());

            List<RakoCommand> commands = Lists.newArrayList();
            if (!controllerHouse.equals(house)) {
                commands.add(RakoCommand.newInstance(RakoCommandType.HOUSE, house));
            }
            if (!controllerHouse.equals(room)) {
                commands.add(RakoCommand.newInstance(RakoCommandType.ROOM, room));
            }
            commands.add(RakoCommand.newInstance(RakoCommandType.SCENE, scene));
        }
    }
}
