package uk.co.leemorris.starfighter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.leemorris.starfighter.MultiformatDateDeserializer;

import java.util.Date;

/**
 * @author lmorris
 */
public class StockQuote extends BaseResponse {

    private String symbol;
    private String venue;
    private int bid;
    private int ask;
    private int bidSize;
    private int askSize;
    private int bidDepth;
    private int askDepth;
    private int last;
    private int lastSize;
    private Date lastTrade;
    private Date quoteTime;

    @JsonCreator
    public StockQuote(@JsonProperty(value="ok", required=false) Boolean ok,
                      @JsonProperty(value="error", required=false) String error,
                      @JsonProperty("symbol") String symbol,
                      @JsonProperty("venue") String venue,
                      @JsonProperty("bid") int bid,
                      @JsonProperty("ask") int ask,
                      @JsonProperty("bidSize") int bidSize,
                      @JsonProperty("askSize") int askSize,
                      @JsonProperty("bidDepth") int bidDepth,
                      @JsonProperty("askDepth") int askDepth,
                      @JsonProperty("last") int last,
                      @JsonProperty("lastSize") int lastSize,
                      @JsonProperty("lastTrade") @JsonDeserialize(using = MultiformatDateDeserializer.class) Date lastTrade,
                      @JsonProperty("quoteTime") @JsonDeserialize(using = MultiformatDateDeserializer.class) Date quoteTime) {
        super(ok == null ? true : ok, error);
        this.symbol = symbol;
        this.venue = venue;
        this.bid = bid;
        this.ask = ask;
        this.bidSize = bidSize;
        this.askSize = askSize;
        this.bidDepth = bidDepth;
        this.askDepth = askDepth;
        this.last = last;
        this.lastSize = lastSize;
        this.lastTrade = lastTrade;
        this.quoteTime = quoteTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getVenue() {
        return venue;
    }

    public int getBid() {
        return bid;
    }

    public int getAsk() {
        return ask;
    }

    public int getBidSize() {
        return bidSize;
    }

    public int getAskSize() {
        return askSize;
    }

    public int getBidDepth() {
        return bidDepth;
    }

    public int getAskDepth() {
        return askDepth;
    }

    public int getLast() {
        return last;
    }

    public int getLastSize() {
        return lastSize;
    }

    public Date getLastTrade() {
        return lastTrade;
    }

    public Date getQuoteTime() {
        return quoteTime;
    }

    @Override
    public String toString() {
        return "StockQuote{" +
                "symbol='" + symbol + '\'' +
                ", venue='" + venue + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", bidSize=" + bidSize +
                ", askSize=" + askSize +
                ", bidDepth=" + bidDepth +
                ", askDepth=" + askDepth +
                ", last=" + last +
                ", lastSize=" + lastSize +
                ", lastTrade=" + lastTrade +
                ", quoteTime=" + quoteTime +
                '}';
    }
}
