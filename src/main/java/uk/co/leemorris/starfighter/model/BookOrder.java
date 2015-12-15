package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lmorris
 */
public class BookOrder {

    private int price;
    private int qty;
    private boolean isBuy;

    @JsonCreator
    public BookOrder(@JsonProperty("price") int price,
                     @JsonProperty("qty") int qty,
                     @JsonProperty("isBuy") boolean isBuy) {
        this.price = price;
        this.qty = qty;
        this.isBuy = isBuy;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public boolean isBuy() {
        return isBuy;
    }
}
