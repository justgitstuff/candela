package com.jrako.candela;

import static com.jrako.control.stateful.command.RakoCommand.newInstance;
import static com.jrako.control.stateful.command.RakoCommandType.CHANNEL;
import static com.jrako.control.stateful.command.RakoCommandType.HOUSE;
import static com.jrako.control.stateful.command.RakoCommandType.LEVEL;
import static com.jrako.control.stateful.command.RakoCommandType.OFF;
import static com.jrako.control.stateful.command.RakoCommandType.ROOM;
import static com.jrako.control.stateful.command.RakoCommandType.SCENE;
import static com.jrako.control.stateful.command.RakoCommandType.STATUS;

import java.util.List;

import com.candela.Channel;
import com.candela.House;
import com.candela.Level;
import com.candela.Room;
import com.candela.Scene;
import com.candela.control.ChannelController;
import com.candela.control.HouseController;
import com.candela.control.RoomController;
import com.candela.discovery.HomeBrowser;
import com.google.common.collect.Lists;
import com.jrako.control.RakoException;
import com.jrako.control.stateful.PesimisticRakoClientAdapter;
import com.jrako.control.stateful.StatefulRakoClient;
import com.jrako.control.stateful.command.RakoCommand;
import com.jrako.control.stateful.result.InvalidResult;
import com.jrako.control.stateful.result.RakoCommandResult;
import com.jrako.control.stateful.result.StatusResult;

public class RakoCandelaBridge implements HouseController, RoomController, ChannelController {

    private RakoHouse controllerHouse = RakoHouse.UNSET;
    private RakoRoom controllerRoom = RakoRoom.UNSET;
    private RakoChannel controllerChannel = RakoChannel.UNSET;

    private final StatefulRakoClient controller;
    private final HomeBrowser browser;

    RakoCandelaBridge(StatefulRakoClient controller, HomeBrowser browser) {
        this.controller = new PesimisticRakoClientAdapter(controller);
        this.browser = browser;
    }

    @Override
    public void initialise() {
        RakoCommand command = newInstance(STATUS);
        RakoCommandResult result = controller.execute(command);
        if (!result.equals(InvalidResult.INSTANCE)) {
            StatusResult status = (StatusResult) result;
            controllerHouse = getHouseById(status.getHouse());
            controllerRoom = getRoomById(status.getRoom());
            controllerChannel = getChannelById(status.getChannel());
        }
    }

    @Override
    public void off(House house) {
        RakoHouse rakoHouse = (RakoHouse) house;
        List<RakoCommand> commands = prepareContext(rakoHouse);
        commands.add(newInstance(OFF));
        try {
            execute(commands);
        } catch (RakoException e) {
            throw new RakoCommandException("Error executing house off command", e);
        }
    }

    @Override
    public void on(House house) {
        for (Room room : house.getRooms()) {
            on(room);
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
        } catch (RakoException e) {
            throw new RakoCommandException("Error executing room off command", e);
        }
    }

    @Override
    public void on(Room room) {
        Scene scene = room.getScenes().get(0);
        setScene(scene);
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
        } catch (RakoException e) {
            throw new RakoCommandException("Error executing set scene command", e);
        }
    }

    @Override
    public void setChannelLevel(Channel channel, Level level) {
        RakoLevel rakoLevel = (RakoLevel) level;
        List<RakoCommand> commands = Lists.newArrayList();
        commands.addAll(prepareContext(channel));
        commands.add(newInstance(LEVEL, rakoLevel.getRakoLevelValue()));
        try {
            execute(commands);
        } catch (RakoException e) {
            throw new RakoCommandException("Error executing channel level command", e);
        }
    }

    @Override
    public Level newLevel(float value) {
        return RakoLevel.valueOf(value);
    }

    private List<RakoCommand> prepareContext(RakoHouse house, RakoRoom room, RakoChannel channel) {
        List<RakoCommand> commands = Lists.newArrayList();
        if (controllerHouse == null || !controllerHouse.equals(house)) {
            commands.add(newInstance(HOUSE, house.getId()));
        }
        if (controllerRoom == null || !controllerRoom.equals(room)) {
            commands.add(newInstance(ROOM, room.getId()));
        }
        if (controllerChannel == null || !controllerChannel.equals(channel)) {
            commands.add(newInstance(CHANNEL, channel.getId()));
        }
        return commands;
    }

    private List<RakoCommand> prepareContext(RakoHouse rakoHouse) {
        List<RakoCommand> commands = prepareContext(rakoHouse, RakoRoom.MASTER, RakoChannel.MASTER);
        return commands;
    }

    private List<RakoCommand> prepareContext(Room room) {
        RakoRoom rakoRoom = (RakoRoom) room;
        RakoHouse rakoHouse = (RakoHouse) room.getHouse();
        List<RakoCommand> commands = prepareContext(rakoHouse, rakoRoom, RakoChannel.MASTER);
        return commands;
    }

    private List<RakoCommand> prepareContext(Channel channel) {
        RakoChannel rakoChannel = (RakoChannel) channel;
        RakoRoom rakoRoom = (RakoRoom) channel.getRoom();
        RakoHouse rakoHouse = (RakoHouse) rakoRoom.getHouse();
        List<RakoCommand> commands = prepareContext(rakoHouse, rakoRoom, rakoChannel);
        return commands;
    }

    private void execute(List<RakoCommand> commands) throws RakoException {
        for (RakoCommand command : commands) {
            System.out.println(command);
            RakoCommandResult result = controller.execute(command);
            if (result.equals(InvalidResult.INSTANCE)) {
                throw new RakoCommandException("Command execution failed: " + result);
            }
            switch (command.getType()) {
            case HOUSE:
                controllerHouse = getHouseById(command.getArgument());
                break;
            case ROOM:
                controllerRoom = getRoomById(command.getArgument());
                break;
            case CHANNEL:
                controllerChannel = getChannelById(command.getArgument());
                break;
            }
        }
    }

    private RakoHouse getHouseById(int houseId) {
        browser.gotoHouse(Integer.toString(houseId));
        return (RakoHouse) browser.getHouse();
    }

    private RakoRoom getRoomById(int roomId) {
        browser.gotoRoom(Integer.toString(roomId));
        return (RakoRoom) browser.getRoom();
    }

    private RakoChannel getChannelById(int channelId) {
        browser.gotoChannel(Integer.toString(channelId));
        return (RakoChannel) browser.getChannel();
    }

}
