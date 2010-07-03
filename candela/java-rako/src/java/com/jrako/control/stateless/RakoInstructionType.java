package com.jrako.control.stateless;

public enum RakoInstructionType {

    /* */
    OFF(0),
    /* */
    FADE_UP(1),
    /* */
    FADE_DOWN(2),
    /* */
    SCENE_1(3),
    /* */
    SCENE_2(4),
    /* */
    SCENE_3(5),
    /* */
    SCENE_4(6),
    /* */
    PROGRAM_MODE(7),
    /* */
    IDENTIFY(8),
    /* */
    LOW_BATTERY(10),
    /* */
    EEPROM_WRITE(11),
    /* */
    LEVEL_SET(12),
    /* */
    STORE(13),
    /* */
    STOP(15),
    /* */
    PROGRAM_LIGHT_INCREASE(1, true),
    /* */
    PROGRAM_LIGHT_DECREASE(2, true),
    /* */
    PROGRAM_STORE_AND_IDENTIFY(3, true),
    /* */
    PROGRAM_CHANNEL_INCREASE(4, true),
    /* */
    PROGRAM_CHANNEL_DECREASE(5, true),
    /* */
    PROGRAM_IDENDITY(8, true),
    /* */
    PROGRAM_LOW_BATTERY(10, true),
    /* */
    PROGRAM_EEPROM_WRITE(11, true),
    /* */
    PROGRAM_LEVEL_SET(12, true),
    /* */
    PROGRAM_STORE(13, true),
    /* */
    PROGRAM_EXIT(14, true),
    /* */
    PROGRAM_STOP(15, true);

    private final int instructionNumber;

    private final boolean isProgramMode;

    private RakoInstructionType(int instructionNumber, boolean isProgramMode) {
        this.instructionNumber = instructionNumber;
        this.isProgramMode = isProgramMode;
    }

    private RakoInstructionType(int instructionNumber) {
        this(instructionNumber, false);
    }

    public int getInstructionNumber() {
        return instructionNumber;
    }

    public boolean isProgramMode() {
        return isProgramMode;
    }

}
