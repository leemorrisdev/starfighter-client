package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lmorris
 */
public enum Direction {

    BUY,
    SELL;

    private static Map<String, Direction> nameMap = new HashMap<>();

    static {
        nameMap.put("buy", BUY);
        nameMap.put("sell", SELL);
    }

    @JsonCreator
    public static Direction forValue(String value) {
        return nameMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for(Map.Entry<String, Direction> directionEntry : nameMap.entrySet()) {
            if(directionEntry.getValue() == this) {
                return directionEntry.getKey();
            }
        }
        return null;
    }

}
