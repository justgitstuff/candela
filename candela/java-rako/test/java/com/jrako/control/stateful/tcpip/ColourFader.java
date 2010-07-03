package com.jrako.control.stateful.tcpip;

import java.awt.Color;

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
import com.jrako.candela.RakoLevel;

public class ColourFader {

    private static final float RATIO = 100.0f / 255.0f;

    @Test
    public void simpleRakoCommand() throws Exception {
        HouseFactory houseFactory = new DefaultHouseFactory();
        House house = houseFactory.newInstance();

        ControllerGroupFactory controlFactory = new DefaultControllerGroupFactory(house);
        ControllerGroup controllerGroup = controlFactory.newInstance();
        ChannelController channelController = controllerGroup.getChannelController();

        Room lounge = house.getRooms().get(2);
        System.out.println(lounge);

        Channel redChannel = lounge.getChannels().get(5);
        Channel greenChannel = lounge.getChannels().get(6);
        Channel blueChannel = lounge.getChannels().get(7);
        System.out.println(redChannel);
        System.out.println(greenChannel);
        System.out.println(blueChannel);

        float lastRed = 0.0f;
        float lastGreen = 0.0f;
        float lastBlue = 0.0f;
        for (int cycles = 0; cycles < 1; cycles++) {
            for (float hue = 0.0f; hue <= 1.0f; hue += 0.00625) {
                int rgb = Color.HSBtoRGB(hue, 1.0f, 0.8f);
                Color color = new Color(rgb);
                float red = color.getRed() * RATIO;
                float green = color.getGreen() * RATIO;
                float blue = color.getBlue() * RATIO;
                if (red != lastRed) {
                    System.out.println("Red   :" + red);
                    channelController.setChannelLevel(redChannel, RakoLevel.valueOf(red));
                    lastRed = red;
                }
                if (green != lastGreen) {
                    System.out.println("Green :" + green);
                    channelController.setChannelLevel(greenChannel, RakoLevel.valueOf(green));
                    lastGreen = green;
                }
                if (blue != lastBlue) {
                    System.out.println("Blue  :" + blue);
                    channelController.setChannelLevel(blueChannel, RakoLevel.valueOf(blue));
                    lastBlue = blue;
                }

                Thread.sleep(50);
            }
        }
    }
}
