package com.jrako.candela;

import static com.jrako.command.RakoCommand.newInstance;
import static com.jrako.command.RakoCommandType.CHANNEL;
import static com.jrako.command.RakoCommandType.HOUSE;
import static com.jrako.command.RakoCommandType.LEVEL;
import static com.jrako.command.RakoCommandType.OFF;
import static com.jrako.command.RakoCommandType.ROOM;
import static com.jrako.command.RakoCommandType.SCENE;
import static com.jrako.command.RakoCommandType.STATUS;

import java.util.List;
import java.util.Map;

import com.candela.Channel;
import com.candela.House;
import com.candela.Level;
import com.candela.Room;
import com.candela.Scene;
import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jrako.command.RakoCommand;
import com.jrako.command.RakoResult;
import com.jrako.command.result.InvalidResult;
import com.jrako.command.result.StatusResult;
import com.jrako.controller.RakoController;
import com.jrako.controller.RakoControllerException;

public class RakoCandelaBridge implements HouseController, RoomController, ChannelController {

    private final Map<Integer, RakoHouse> houses = Maps.newHashMap();
    private final Map<Integer, RakoRoom> rooms = Maps.newHashMap();
    private final Map<Integer, RakoChannel> channels = Maps.newHashMap();

    private RakoHouse controllerHouse = RakoHouse.UNSET;
    private RakoRoom controllerRoom = RakoRoom.UNSET;
    private RakoChannel controllerChannel = RakoChannel.UNSET;

    private RakoController controller;

    public void initialise() throws RakoControllerException {
        RakoCommand command = newInstance(STATUS);
        RakoResult result = controller.execute(command);
        if (!result.equals(InvalidResult.INSTANCE)) {
            StatusResult status = (StatusResult) result;
            controllerHouse = houses.get(status.getHouse());
            controllerRoom = rooms.get(status.getRoom());
            controllerChannel = channels.get(status.getChannel());
        }
    }

    @Override
    public void off(House house) {
        RakoHouse rakoHouse = (RakoHouse) house;
        List<RakoCommand> commands = Lists.newArrayList();
        if (!controllerHouse.equals(rakoHouse)) {
            commands.add(newInstance(HOUSE, rakoHouse.getId()));
        }
        if (!controllerRoom.equals(RakoRoom.MASTER)) {
            commands.add(newInstance(ROOM, RakoRoom.MASTER.getId()));
        }
        commands.add(newInstance(OFF));
        try {
            execute(commands);
        } catch (RakoControllerException e) {
            throw new RakoCommandException("Error executing house off command", e);
        }
    }

    @Override
    public void off(Room room) {
        RakoRoom rakoRoom = (RakoRoom) room;
        List<RakoCommand> commands = Lists.newArrayList();
        commands.addAll(prepareContext(rakoRoom));
        commands.add(newInstance(OFF));
        try {
            execute(commands);
        } catch (RakoControllerException e) {
            throw new RakoCommandException("Error executing room off command", e);

        }
    }

    @Override
    public void setScene(Scene scene) {
        RakoScene rakoScene = (RakoScene) scene;
        RakoRoom rakoRoom = (RakoRoom) rakoScene.getRoom();
        List<RakoCommand> commands = Lists.newArrayList();
        commands.addAll(prepareContext(rakoRoom));
        commands.add(newInstance(SCENE, rakoScene.getId()));
        try {
            execute(commands);
        } catch (RakoControllerException e) {
            throw new RakoCommandException("Error executing set scene command", e);

        }
    }

    @Override
    public void setChannelLevel(Channel channel, Level level) {
        RakoLevel rakoLevel = (RakoLevel) level;
        List<RakoCommand> commands = Lists.newArrayList();
        commands.addAll(prepareContext(channel));
        commands.add(newInstance(LEVEL, rakoLevel.getLevelValue()));
        try {
            execute(commands);
        } catch (RakoControllerException e) {
            throw new RakoCommandException("Error executing channel level command", e);
        }
    }

    private List<RakoCommand> prepareContext(Room room) {
        RakoRoom rakoRoom = (RakoRoom) room;
        RakoHouse rakoHouse = (RakoHouse) room.getHouse();
        List<RakoCommand> commands = Lists.newArrayList();
        if (!controllerHouse.equals(rakoHouse)) {
            commands.add(newInstance(HOUSE, rakoHouse.getId()));
        }
        if (!controllerRoom.equals(rakoRoom)) {
            commands.add(newInstance(ROOM, rakoRoom.getId()));
        }
        return commands;
    }

    private List<RakoCommand> prepareContext(Channel channel) {
        prepareContext(channel.getRoom());
        RakoChannel rakoChannel = (RakoChannel) channel;
        List<RakoCommand> commands = Lists.newArrayList();
        if (!controllerChannel.equals(rakoChannel)) {
            commands.add(newInstance(CHANNEL, rakoChannel.getId()));
        }
        return commands;
    }

    private void execute(List<RakoCommand> commands) throws RakoControllerException {
        for (RakoCommand command : commands) {
            RakoResult result = controller.execute(command);
            if (result.equals(InvalidResult.INSTANCE)) {
                throw new RakoCommandException("Command execution failed: " + result);
            }
        }
    }

}
