package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lmorris
 */
public class QuoteSubscriptionWrapper extends BaseResponse {

    private StockQuote quote;

    @JsonCreator
    public QuoteSubscriptionWrapper(@JsonProperty(value="ok") boolean ok,
                                    @JsonProperty(value="error", required=false) String error,
                                    @JsonProperty("quote") StockQuote quote) {
        super(ok, error);
        this.quote = quote;
    }

    public StockQuote getQuote() {
        return quote;
    }
}
