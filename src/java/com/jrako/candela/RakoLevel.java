package com.jrako.candela;

import java.util.Map;

import com.candela.Level;
import com.google.common.collect.Maps;

public class RakoLevel implements Level {

    private static final int MAX_LEVEL = 100;
    private static final int MIN_LEVEL = 0;
    private static Map<Integer, RakoLevel> cache = Maps.newHashMap();

    private final int levelValue;

    private RakoLevel(int levelValue) {
        this.levelValue = levelValue;
    }

    public static Level valueOf(int levelValue) {
        if (levelValue < MIN_LEVEL || levelValue > MAX_LEVEL) {
            throw new IllegalArgumentException("Invalid level value [0..100]");
        }
        Integer intLevelValue = Integer.valueOf(levelValue);
        RakoLevel level = cache.get(levelValue);
        if (level == null) {
            level = new RakoLevel(intLevelValue);
            cache.put(intLevelValue, level);
        }
        return level;
    }

    public int getLevelValue() {
        return levelValue;
    }

    @Override
    public String toString() {
        return Integer.toString(getLevelValue());
    }

}
