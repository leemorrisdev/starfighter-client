package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lmorris
 */
public class Symbol {

    private String name;
    private String symbol;

    @JsonCreator
    public Symbol(@JsonProperty("name") String name, @JsonProperty("symbol") String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
