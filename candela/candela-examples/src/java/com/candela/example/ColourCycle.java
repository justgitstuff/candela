package com.candela.example;

import java.awt.Color;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.candela.Channel;
import com.candela.ControllerGroupFactory;
import com.candela.DefaultControllerGroupFactory;
import com.candela.DefaultHouseFactory;
import com.candela.House;
import com.candela.HouseFactory;
import com.candela.Room;
import com.candela.control.ChannelController;
import com.candela.control.ControllerGroup;

public class ColourCycle {

    private static final float RATIO = 100.0f / 255.0f;

    private final int interval;
    private final int cycleCount;
    private final float increment;

    private final int roomId;
    private final int redChannelId;
    private final int greenChannelId;
    private final int blueChannelId;

    private final float brightness;

    private ColourCycle() throws Exception {
        InputStream stream = getClass().getResourceAsStream("colour-cycle.properties");
        Properties properties = new Properties();
        properties.load(stream);

        interval = Integer.parseInt(properties.getProperty("interval.ms"));
        cycleCount = Integer.parseInt(properties.getProperty("cycles"));
        increment = Float.parseFloat(properties.getProperty("increment"));

        roomId = Integer.parseInt(properties.getProperty("room.id"));
        redChannelId = Integer.parseInt(properties.getProperty("red.channel.id"));
        greenChannelId = Integer.parseInt(properties.getProperty("green.channel.id"));
        blueChannelId = Integer.parseInt(properties.getProperty("blue.channel.id"));

        brightness = Float.parseFloat(properties.getProperty("brightness"));
    }

    public static void main(String[] args) throws Exception {
        ColourCycle cycle = new ColourCycle();
        cycle.start();
    }

    @Test
    private void start() throws Exception {
        HouseFactory houseFactory = new DefaultHouseFactory();
        House house = houseFactory.newInstance();

        ControllerGroupFactory controlFactory = new DefaultControllerGroupFactory(house);
        ControllerGroup controllerGroup = controlFactory.newInstance();
        ChannelController channelController = controllerGroup.getChannelController();

        Room room = house.getRooms().get(roomId);
        System.out.println(room);

        Channel redChannel = room.getChannels().get(redChannelId);
        Channel greenChannel = room.getChannels().get(greenChannelId);
        Channel blueChannel = room.getChannels().get(blueChannelId);
        System.out.println(redChannel);
        System.out.println(greenChannel);
        System.out.println(blueChannel);

        float lastRed = 0.0f;
        float lastGreen = 0.0f;
        float lastBlue = 0.0f;
        for (int cycles = 0; cycles < cycleCount; cycles++) {
            for (float hue = 0.0f; hue <= 1.0f; hue += increment) {
                int rgb = Color.HSBtoRGB(hue, 1.0f, brightness);
                Color color = new Color(rgb);
                float red = color.getRed() * RATIO;
                float green = color.getGreen() * RATIO;
                float blue = color.getBlue() * RATIO;
                if (red != lastRed) {
                    System.out.println("Red   :" + red);
                    channelController.setChannelLevel(redChannel, channelController.newLevel(red));
                    lastRed = red;
                }
                if (green != lastGreen) {
                    System.out.println("Green :" + green);
                    channelController.setChannelLevel(greenChannel, channelController.newLevel(green));
                    lastGreen = green;
                }
                if (blue != lastBlue) {
                    System.out.println("Blue  :" + blue);
                    channelController.setChannelLevel(blueChannel, channelController.newLevel(blue));
                    lastBlue = blue;
                }
                Thread.sleep(interval);
            }
        }
    }

}
