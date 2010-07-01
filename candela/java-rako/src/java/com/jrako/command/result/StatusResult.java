package com.jrako.command.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.jrako.command.RakoResult;
import com.jrako.controller.RakoControllerException;

public class StatusResult implements RakoResult {

    private static final Logger LOG = Logger.getLogger(StatusResult.class);
    private static final String STATUS_PATTERN_STR = "^HO:([0-9]{3}) RO:([0-9]{4}) CH:([0-9]{3}) GR:([0-9]{3})$";
    private static final Pattern STATUS_PATTERN = Pattern.compile(STATUS_PATTERN_STR);

    private final int house;

    private final int room;

    private final int channel;

    private final int group;

    StatusResult(String input) throws RakoControllerException {
        LOG.debug("StatusResult input: '" + input + "'");
        Matcher matcher = STATUS_PATTERN.matcher(input);
        if (matcher.matches()) {
            try {
                house = Integer.parseInt(matcher.group(1));
                room = Integer.parseInt(matcher.group(2));
                channel = Integer.parseInt(matcher.group(3));
                group = Integer.parseInt(matcher.group(4));
            } catch (NumberFormatException nfX) {
                throw new RakoControllerException("Could not understand: " + input, nfX);
            }
        } else {
            throw new RakoControllerException("Could not understand: " + input);
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

    public int getGroup() {
        return group;
    }

}
