package com.jrako.candela;

import java.util.Map;

import com.candela.Level;
import com.google.common.collect.Maps;

public class RakoLevel implements Level {

    private static final float RATIO = 255.0f / 100.0f;
    private static final float MAX_LEVEL = 100.0f;
    private static final float MIN_LEVEL = 0.0f;
    private static Map<Integer, RakoLevel> cache = Maps.newHashMap();

    private final float levelValue;
    private final Integer rakoValue;

    private RakoLevel(float levelValue, Integer rakoValue) {
        this.levelValue = levelValue;
        this.rakoValue = rakoValue;
    }

    public static Level valueOf(float levelValue) {
        if (levelValue < MIN_LEVEL || levelValue > MAX_LEVEL) {
            throw new IllegalArgumentException("Invalid level value [0..100]");
        }
        Integer intLevelValue = Integer.valueOf((int) (levelValue * RATIO));
        RakoLevel level = cache.get(intLevelValue);
        if (level == null) {
            level = new RakoLevel(levelValue, intLevelValue);
            cache.put(intLevelValue, level);
        }
        return level;
    }

    public int getRakoLevelValue() {
        return rakoValue;
    }

    @Override
    public float getValue() {
        return levelValue;
    }

    @Override
    public String toString() {
        return Integer.toString(getRakoLevelValue());
    }

}
