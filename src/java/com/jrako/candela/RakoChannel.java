package com.jrako.candela;

import com.candela.model.Channel;

public class RakoChannel implements Channel {

    static final RakoChannel UNSET = new RakoChannel(-1);

    private final int channelId;

    private RakoChannel(int channelId) {
        this.channelId = channelId;
    }

    static RakoChannel valueOf(int channelId) {
        return new RakoChannel(channelId);
    }

    int getChannelId() {
        return channelId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + channelId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RakoChannel)) {
            return false;
        }
        RakoChannel other = (RakoChannel) obj;
        if (channelId != other.channelId) {
            return false;
        }
        return true;
    }

}
