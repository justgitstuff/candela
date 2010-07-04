package com.candela.control;

import com.candela.Channel;
import com.candela.Level;

public interface ChannelController {

    void setChannelLevel(Channel channel, Level level);

    Level newLevel(float value);

}
