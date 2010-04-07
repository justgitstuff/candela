package com.jrako.command.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jrako.command.RakoResult;
import com.jrako.controller.RakoControllerException;

public class StatusResult implements RakoResult {

    private static final String STATUS_PATTERN_STR = "^HO:([0-9]{3}) RO:([0-9]{4}) CH:([0-9]{3}) GR:([0-9]{3})$";
    private static final Pattern STATUS_PATTERN = Pattern.compile(STATUS_PATTERN_STR);

    private final int house;

    private final int room;

    private final int channel;

    private final int gr;

    StatusResult(String input) throws RakoControllerException {
        try {
            Matcher matcher = STATUS_PATTERN.matcher(input);
            house = Integer.parseInt(matcher.group(1));
            room = Integer.parseInt(matcher.group(2));
            channel = Integer.parseInt(matcher.group(3));
            gr = Integer.parseInt(matcher.group(4));
        } catch (NumberFormatException nfX) {
            throw new RakoControllerException("Could not understand: " + input, nfX);
        }
    }

    public int getHouse() {
        return house;
    }

    public int getRoom() {
        return room;
    }

    public int getChannel() {
        return channel;
    }

    public int getGr() {
        return gr;
    }

}
