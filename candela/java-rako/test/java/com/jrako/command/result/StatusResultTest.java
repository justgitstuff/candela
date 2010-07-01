package com.jrako.command.result;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatusResultTest {

    @Test
    public void standardInput() {
        String input = "HO:001 RO:3421 CH:170 GR:107";
        StatusResult result = new StatusResult(input);
        assertEquals(1, result.getHouse());
        assertEquals(3421, result.getRoom());
        assertEquals(170, result.getChannel());
        assertEquals(107, result.getGroup());
    }

}
