package com.candela.example;

import java.awt.Color;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.candela.Channel;
import com.candela.ControllerFactory;
import com.candela.DefaultControllerFactory;
import com.candela.control.ChannelController;
import com.candela.discovery.DefaultHomeBrowserFactory;
import com.candela.discovery.HomeBrowser;
import com.candela.discovery.HomeBrowserFactory;

public class ColourCycle {

    private static final float RATIO = 100.0f / 255.0f;

    private final int interval;
    private final int cycleCount;
    private final float increment;

    private final String houseId;
    private final String roomId;
    private final String redChannelId;
    private final String greenChannelId;
    private final String blueChannelId;

    private final float brightness;

    private ColourCycle() throws Exception {
        InputStream stream = getClass().getResourceAsStream("colour-cycle.properties");
        Properties properties = new Properties();
        properties.load(stream);

        interval = Integer.parseInt(properties.getProperty("interval.ms"));
        cycleCount = Integer.parseInt(properties.getProperty("cycles"));
        increment = Float.parseFloat(properties.getProperty("increment"));

        houseId = properties.getProperty("house.id");
        roomId = properties.getProperty("room.id");
        redChannelId = properties.getProperty("red.channel.id");
        greenChannelId = properties.getProperty("green.channel.id");
        blueChannelId = properties.getProperty("blue.channel.id");

        brightness = Float.parseFloat(properties.getProperty("brightness"));
    }

    public static void main(String[] args) throws Exception {
        ColourCycle cycle = new ColourCycle();
        cycle.start();
    }

    @Test
    private void start() throws Exception {
        HomeBrowserFactory factory = new DefaultHomeBrowserFactory();
        HomeBrowser browser = factory.newInstance();

        ControllerFactory controlFactory = DefaultControllerFactory.newInstance(browser);
        ChannelController channelController = controlFactory.newChannelController();

        browser.gotoHouse(houseId).gotoRoom(roomId);

        System.out.println(browser.getLocation());

        Channel redChannel = browser.gotoChannel(redChannelId).getChannel();
        Channel greenChannel = browser.gotoChannel(greenChannelId).getChannel();
        Channel blueChannel = browser.gotoChannel(blueChannelId).getChannel();
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
