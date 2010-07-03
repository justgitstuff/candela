package com.jrako.control.stateful.command;

import static com.jrako.control.stateful.command.RakoCommandArgument.OPTIONAL;
import static com.jrako.control.stateful.command.RakoCommandArgument.REQUIRED;
import static com.jrako.control.stateful.command.RakoCommandOrdinate.CHANNEL_ID;
import static com.jrako.control.stateful.command.RakoCommandOrdinate.HOUSE_ID;
import static com.jrako.control.stateful.command.RakoCommandOrdinate.NONE;
import static com.jrako.control.stateful.command.RakoCommandOrdinate.ROOM_ID;

import com.jrako.control.stateful.result.DefaultResultResolver;
import com.jrako.control.stateful.result.CommandResultResolver;
import com.jrako.control.stateful.result.StatusResultResolver;

public enum RakoCommandType {

    /** */
    ADDRESS("AD", REQUIRED),
    /** */
    BAUD("BA", REQUIRED),
    /** */
    CHANNEL("CH", OPTIONAL, ROOM_ID),
    /** */
    COMMAND("CO", OPTIONAL),
    /** */
    DATA("DA", REQUIRED),
    /** */
    ECHO("EC"),
    /** */
    HOUSE("HO", REQUIRED),
    /** */
    LEVEL("L", REQUIRED, CHANNEL_ID),
    /** */
    NOECHO("NO"),
    /** */
    OFF("OF", ROOM_ID),
    /** */
    RESET("RE"),
    /** */
    ROOM("RO", OPTIONAL, HOUSE_ID),
    /** */
    SCENE("SC", REQUIRED, ROOM_ID),
    /** */
    STATUS("STA", new StatusResultResolver()),
    /** */
    STORE("STO"),
    /** */
    VER("VE");

    private final RakoCommandArgument signature;

    private final String shortCode;

    private final RakoCommand defaultCommand;

    private final RakoCommandOrdinate ordinate;

    private final CommandResultResolver resultResolver;

    private RakoCommandType(String shortCode) {
        this(shortCode, RakoCommandArgument.NONE);
    }

    private RakoCommandType(String shortCode, CommandResultResolver resultResolver) {
        this(shortCode, RakoCommandArgument.NONE, NONE, resultResolver);
    }

    private RakoCommandType(String shortCode, RakoCommandOrdinate ordinate) {
        this(shortCode, RakoCommandArgument.NONE, ordinate, new DefaultResultResolver());
    }

    private RakoCommandType(String shortCode, RakoCommandArgument signature) {
        this(shortCode, signature, NONE, new DefaultResultResolver());
    }

    private RakoCommandType(String shortCode, RakoCommandArgument signature, RakoCommandOrdinate ordinate) {
        this(shortCode, signature, ordinate, new DefaultResultResolver());
    }

    private RakoCommandType(String shortCode, RakoCommandArgument signature, RakoCommandOrdinate ordinate,
            CommandResultResolver resultResolver) {
        this.signature = signature;
        this.shortCode = shortCode;
        this.ordinate = ordinate;
        this.resultResolver = resultResolver;
        if (signature == RakoCommandArgument.NONE) {
            defaultCommand = new RakoCommand(this);
        } else {
            defaultCommand = null;
        }
    }

    public RakoCommandArgument getSignature() {
        return signature;
    }

    public String getShortCode() {
        return shortCode;
    }

    public RakoCommand getDefaultCommand() {
        return defaultCommand;
    }

    public RakoCommandOrdinate getOrdinate() {
        return ordinate;
    }

    public CommandResultResolver getRakoResultResolver() {
        return resultResolver;
    }

}
