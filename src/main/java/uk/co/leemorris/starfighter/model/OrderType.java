package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lmorris
 */
public enum OrderType {

    LIMIT,
    MARKET,
    FOK,
    IOC;

    private static Map<String, OrderType> nameMap = new HashMap<>();

    static {
        nameMap.put("limit", LIMIT);
        nameMap.put("market", MARKET);
        nameMap.put("fill-or-kill", FOK);
        nameMap.put("immediate-or-cancel", IOC);
    }

    @JsonCreator
    public static OrderType forValue(String value) {
        return nameMap.get(value);
    }

    @JsonValue
    public String toValue() {

        for(Map.Entry<String, OrderType> directionEntry : nameMap.entrySet()) {
            if(directionEntry.getValue() == this) {
                return directionEntry.getKey();
            }
        }

        return null;
    }

}
