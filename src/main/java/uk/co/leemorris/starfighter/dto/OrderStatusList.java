package uk.co.leemorris.starfighter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.leemorris.starfighter.model.BaseResponse;
import uk.co.leemorris.starfighter.model.OrderStatus;

import java.util.List;

/**
 * @author lmorris
 */
public class OrderStatusList extends BaseResponse {

    private String venue;
    private List<OrderStatus> orders;

    @JsonCreator
    public OrderStatusList(@JsonProperty("ok") boolean ok,
                           @JsonProperty(value="error", required=false) String error,
                           @JsonProperty("venue") String venue,
                           @JsonProperty("orders") List<OrderStatus> orders) {
        super(ok, error);
        this.venue = venue;
        this.orders = orders;
    }

    public String getVenue() {
        return venue;
    }

    public List<OrderStatus> getOrders() {
        return orders;
    }
}
