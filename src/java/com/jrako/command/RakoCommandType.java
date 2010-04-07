package com.jrako.command;

public enum RakoCommandType {

    /** */
    ADDRESS("AD", RakoCommandArgument.REQUIRED),
    /** */
    BAUD("BA", RakoCommandArgument.REQUIRED),
    /** */
    CHANNEL("CH", RakoCommandArgument.OPTIONAL),
    /** */
    COMMAND("CO", RakoCommandArgument.OPTIONAL),
    /** */
    DATA("DA", RakoCommandArgument.REQUIRED),
    /** */
    ECHO("EC"),
    /** */
    HOUSE("HO", RakoCommandArgument.REQUIRED),
    /** */
    LEVEL("L", RakoCommandArgument.REQUIRED),
    /** */
    NOECHO("NO"),
    /** */
    OFF("OF"),
    /** */
    RESET("RE"),
    /** */
    ROOM("RO", RakoCommandArgument.OPTIONAL),
    /** */
    SCENE("SC", RakoCommandArgument.REQUIRED),
    /** */
    STATUS("STA"),
    /** */
    STORE("STO"),
    /** */
    VER("VE");

    private final RakoCommandArgument signature;

    private final String shortCode;

    private final RakoCommand defaultCommand;

    private RakoCommandType(String shortCode) {
        this(shortCode, RakoCommandArgument.NONE);
    }

    private RakoCommandType(String shortCode, RakoCommandArgument signature) {
        this.signature = signature;
        this.shortCode = shortCode;
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

}
