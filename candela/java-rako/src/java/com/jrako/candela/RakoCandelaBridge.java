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
import com.jrako.control.RakoException;
import com.jrako.control.stateful.PesimisticRakoClientAdapter;
import com.jrako.control.stateful.StatefulRakoClient;
import com.jrako.control.stateful.command.RakoCommand;
import com.jrako.control.stateful.result.InvalidResult;
import com.jrako.control.stateful.result.RakoCommandResult;
import com.jrako.control.stateful.result.StatusResult;

public class RakoCandelaBridge implements HouseController, RoomController, ChannelController {

    // TODO: This should cache
    private final Map<Integer, RakoHouse> houses = Maps.newHashMap();
    private final Map<Integer, RakoRoom> rooms = Maps.newHashMap();
    private final Map<Integer, RakoChannel> channels = Maps.newHashMap();

    private RakoHouse controllerHouse = RakoHouse.UNSET;
    private RakoRoom controllerRoom = RakoRoom.UNSET;
    private RakoChannel controllerChannel = RakoChannel.UNSET;

    private final StatefulRakoClient controller;

    public RakoCandelaBridge(StatefulRakoClient controller) {
        this.controller = new PesimisticRakoClientAdapter(controller);
    }

    @Override
    public void initialise(House... confHouses) {
        populateMaps(confHouses);
        RakoCommand command = newInstance(STATUS);
        RakoCommandResult result = controller.execute(command);
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

    private void populateMaps(House... confHouses) {
        for (House house : confHouses) {
            RakoHouse rakoHouse = (RakoHouse) house;
            houses.put(rakoHouse.getId(), rakoHouse);
            for (Room room : house.getRooms()) {
                RakoRoom rakoRoom = (RakoRoom) room;
                rooms.put(rakoRoom.getId(), rakoRoom);
                for (Channel channel : room.getChannels()) {
                    RakoChannel rakoChannel = (RakoChannel) channel;
                    channels.put(rakoChannel.getId(), rakoChannel);
                }
            }
        }
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
            RakoCommandResult result = controller.execute(command);
            if (result.equals(InvalidResult.INSTANCE)) {
                throw new RakoCommandException("Command execution failed: " + result);
            }
            switch (command.getType()) {
            case HOUSE:
                controllerHouse = houses.get(command.getArgument());
                break;
            case ROOM:
                controllerRoom = rooms.get(command.getArgument());
                break;
            case CHANNEL:
                controllerChannel = channels.get(command.getArgument());
                break;
            }
        }
    }

}
