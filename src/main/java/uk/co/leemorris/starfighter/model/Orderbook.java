package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.leemorris.starfighter.MultiformatDateDeserializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lmorris
 */
public class Orderbook extends BaseResponse {

    private String venue;
    private String symbol;
    private Date ts;

    private List<BookOrder> bids;
    private List<BookOrder> asks;

    @JsonCreator
    public Orderbook(@JsonProperty("ok") boolean ok,
                     @JsonProperty(value="error", required=false) String error,
                     @JsonProperty("venue") String venue,
                     @JsonProperty("symbol") String symbol,
                     @JsonProperty("ts") @JsonDeserialize(using = MultiformatDateDeserializer.class) Date ts,
                     @JsonProperty("bids") List<BookOrder> bids,
                     @JsonProperty("asks") List<BookOrder> asks) {
        super(ok, error);
        this.venue = venue;
        this.symbol = symbol;
        this.ts = ts;

        /*
         * If there are no orders the API responds with null.  Rather than pass this through we
         * instantiate an empty list to avoid having to cater for NullPointerExceptions.
         */
        if(bids == null) {
            this.bids = new ArrayList<>();
        } else {
            this.bids = bids;
        }

        if(asks == null) {
            this.asks = new ArrayList<>();
        } else {
            this.asks = asks;
        }


    }

    public String getVenue() {
        return venue;
    }

    public String getSymbol() {
        return symbol;
    }

    public Date getTs() {
        return ts;
    }

    public List<BookOrder> getBids() {
        return bids;
    }

    public List<BookOrder> getAsks() {
        return asks;
    }
}
