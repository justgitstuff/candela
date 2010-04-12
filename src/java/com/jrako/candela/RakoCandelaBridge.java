package com.jrako.candela;

import java.util.List;

import com.candela.Channel;
import com.candela.House;
import com.candela.Level;
import com.candela.Room;
import com.candela.Scene;
import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.google.common.collect.Lists;
import com.jrako.command.RakoCommand;
import com.jrako.command.RakoCommandType;
import com.jrako.controller.RawRakoController;

public class RakoCandelaBridge implements HouseController, RoomController, ChannelController {

    private final RakoHouse controllerHouse = RakoHouse.UNSET;

    private final RakoRoom controllerRoom = RakoRoom.UNSET;

    private final RakoChannel controllerChannel = RakoChannel.UNSET;

    private RawRakoController controller;

    // @Override
    // public void setScene(House house, Room room, Scene scene) {
    // RakoResult result = execute(RakoCommandType.STATUS.getDefaultCommand());
    // if (!result.equals(InvalidResult.INSTANCE)) {
    // StatusResult status = (StatusResult) result;
    // controllerHouse = RakoHouse.valueOf(status.getHouse());
    // controllerRoom = RakoRoom.valueOf(status.getRoom());
    // controllerChannel = RakoChannel.valueOf(status.getChannel());
    //
    // List<RakoCommand> commands = Lists.newArrayList();
    // if (!controllerHouse.equals(house)) {
    // commands.add(RakoCommand.newInstance(RakoCommandType.HOUSE, house));
    // }
    // if (!controllerHouse.equals(room)) {
    // commands.add(RakoCommand.newInstance(RakoCommandType.ROOM, room));
    // }
    // commands.add(RakoCommand.newInstance(RakoCommandType.SCENE, scene));
    // }
    // }

    @Override
    public void off(House house) {
        RakoHouse rakoHouse = (RakoHouse) house;
        List<RakoCommand> commands = Lists.newArrayList();
        commands.add(RakoCommand.newInstance(RakoCommandType.HOUSE, rakoHouse.getId()));
        commands.add(RakoCommand.newInstance(RakoCommandType.OFF));
    }

    @Override
    public void off(Room room) {
        RakoRoom rakoRoom = (RakoRoom) room;
    }

    @Override
    public void setScene(Scene scene) {
        RakoScene rakoScene = (RakoScene) scene;
    }

    @Override
    public void setChannelLevel(Channel channel, Level level) {
        RakoChannel rakoChannel = (RakoChannel) channel;
        RakoLevel rakoLevel = (RakoLevel) level;
    }

}
