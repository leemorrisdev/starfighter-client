package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.leemorris.starfighter.MultiformatDateDeserializer;

import java.util.Date;

/**
 * @author lmorris
 */
public class Fill {

    private int price;
    private int qty;
    private Date ts;

    @JsonCreator
    public Fill(@JsonProperty("price") int price,
                @JsonProperty("qty") int qty,
                @JsonProperty("ts") @JsonDeserialize(using = MultiformatDateDeserializer.class) Date ts) {
        this.price = price;
        this.qty = qty;
        this.ts = ts;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public Date getTs() {
        return ts;
    }
}
