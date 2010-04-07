package com.jrako.candela;

import com.candela.model.Scene;

public class RakoScene implements Scene {

    static final RakoScene UNSET = new RakoScene(-1);

    private final int sceneId;

    private RakoScene(int sceneId) {
        this.sceneId = sceneId;
    }

    static RakoScene valueOf(int sceneId) {
        return new RakoScene(sceneId);
    }

    int getRoomId() {
        return sceneId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + sceneId;
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
        if (!(obj instanceof RakoScene)) {
            return false;
        }
        RakoScene other = (RakoScene) obj;
        if (sceneId != other.sceneId) {
            return false;
        }
        return true;
    }

}
