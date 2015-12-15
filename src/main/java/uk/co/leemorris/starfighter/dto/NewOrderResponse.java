package uk.co.leemorris.starfighter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.leemorris.starfighter.MultiformatDateDeserializer;
import uk.co.leemorris.starfighter.model.BaseResponse;
import uk.co.leemorris.starfighter.model.Direction;
import uk.co.leemorris.starfighter.model.Fill;
import uk.co.leemorris.starfighter.model.OrderType;

import java.util.Date;
import java.util.List;

/**
 * @author lmorris
 */
public class NewOrderResponse extends BaseResponse {

    private String symbol;
    private String venue;
    private Direction direction;
    private int originalQty;
    private int qty;
    private int price;
    private OrderType orderType;
    private long id;
    private String account;
    private Date ts;
    private int totalFilled;
    private boolean open;
    private List<Fill> fills;

    @JsonCreator
    public NewOrderResponse(@JsonProperty("ok") boolean ok,
                            @JsonProperty(value="error", required=false) String error,
                            @JsonProperty("symbol") String symbol,
                            @JsonProperty("venue") String venue,
                            @JsonProperty("direction") Direction direction,
                            @JsonProperty("originalQty") int originalQty,
                            @JsonProperty("qty") int qty,
                            @JsonProperty("price") int price,
                            @JsonProperty("orderType") OrderType orderType,
                            @JsonProperty("id") long id,
                            @JsonProperty("account") String account,
                            @JsonProperty("ts") @JsonDeserialize(using = MultiformatDateDeserializer.class) Date ts,
                            @JsonProperty("totalFilled") int totalFilled,
                            @JsonProperty("open") boolean open,
                            @JsonProperty("fills") List<Fill> fills) {
        super(ok, error);

        this.symbol = symbol;
        this.venue = venue;
        this.direction = direction;
        this.originalQty = originalQty;
        this.qty = qty;
        this.price = price;
        this.orderType = orderType;
        this.id = id;
        this.account = account;
        this.ts = ts;
        this.totalFilled = totalFilled;
        this.open = open;
        this.fills = fills;
    }


    public String getSymbol() {
        return symbol;
    }

    public String getVenue() {
        return venue;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getOriginalQty() {
        return originalQty;
    }

    public int getQty() {
        return qty;
    }

    public int getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public Date getTs() {
        return ts;
    }

    public int getTotalFilled() {
        return totalFilled;
    }

    public boolean isOpen() {
        return open;
    }

    public List<Fill> getFills() {
        return fills;
    }
}
