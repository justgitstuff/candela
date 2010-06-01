package com.jrako.command;

import static com.jrako.command.RakoCommandArgument.OPTIONAL;
import static com.jrako.command.RakoCommandArgument.REQUIRED;
import static com.jrako.command.RakoCommandOrdinate.CHANNEL_ID;
import static com.jrako.command.RakoCommandOrdinate.HOUSE_ID;
import static com.jrako.command.RakoCommandOrdinate.NONE;
import static com.jrako.command.RakoCommandOrdinate.ROOM_ID;

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
    STATUS("STA"),
    /** */
    STORE("STO"),
    /** */
    VER("VE");

    private final RakoCommandArgument signature;

    private final String shortCode;

    private final RakoCommand defaultCommand;

    private final RakoCommandOrdinate ordinate;

    private RakoCommandType(String shortCode) {
        this(shortCode, RakoCommandArgument.NONE);
    }

    private RakoCommandType(String shortCode, RakoCommandOrdinate ordinate) {
        this(shortCode, RakoCommandArgument.NONE, ordinate);
    }

    private RakoCommandType(String shortCode, RakoCommandArgument signature) {
        this(shortCode, signature, NONE);
    }

    private RakoCommandType(String shortCode, RakoCommandArgument signature, RakoCommandOrdinate ordinate) {
        this.signature = signature;
        this.shortCode = shortCode;
        this.ordinate = ordinate;
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

}
