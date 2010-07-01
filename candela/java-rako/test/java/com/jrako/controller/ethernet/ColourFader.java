package com.jrako.controller.ethernet;

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

        Channel red = lounge.getChannels().get(5);
        Channel green = lounge.getChannels().get(6);
        Channel blue = lounge.getChannels().get(7);
        System.out.println(red);
        System.out.println(green);
        System.out.println(blue);

        for (int cycles = 0; cycles < 1; cycles++) {
            for (float hue = 0.0f; hue <= 1.0f; hue += 0.0125) {
                int rgb = Color.HSBtoRGB(hue, 1.0f, 0.8f);
                Color color = new Color(rgb);
                System.out.println("Red   :" + color.getRed() * RATIO);
                System.out.println("Green :" + color.getGreen() * RATIO);
                System.out.println("Blue  :" + color.getBlue() * RATIO);
                channelController.setChannelLevel(red, RakoLevel.valueOf(color.getRed() * RATIO));
                channelController.setChannelLevel(green, RakoLevel.valueOf(color.getGreen() * RATIO));
                channelController.setChannelLevel(blue, RakoLevel.valueOf(color.getBlue() * RATIO));

                Thread.sleep(1000);
            }
        }
    }
}
