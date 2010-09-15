package com.jrako.control.stateful.command;

public class RakoCommand {

    private final RakoCommandType type;

    private Integer argument;

    RakoCommand(RakoCommandType type, int argument) {
        this.type = type;
        this.argument = argument;
    }

    RakoCommand(RakoCommandType type) {
        this.type = type;
        if (type.getSignature() == RakoCommandArgument.REQUIRED) {
            throw new IllegalArgumentException("Command type '" + type.name() + "' requires an argument.");
        }
    }

    public static RakoCommand newInstance(RakoCommandType type, int argument) {
        return new RakoCommand(type, argument);
    }

    public static RakoCommand newInstance(RakoCommandType type) {
        return new RakoCommand(type);
    }

    public RakoCommandType getType() {
        return type;
    }

    public int getArgument() {
        return argument;
    }

    public boolean hasArgument() {
        return argument != null;
    }

    @Override
    public String toString() {
        if (type.getSignature() == RakoCommandArgument.NONE || !hasArgument()) {
            return type.getShortCode();
        } else {
            return type.getShortCode() + ": " + Integer.toString(argument);
        }
    }

}
