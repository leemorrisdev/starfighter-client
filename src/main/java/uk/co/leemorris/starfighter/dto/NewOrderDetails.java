package uk.co.leemorris.starfighter.dto;

import uk.co.leemorris.starfighter.model.Direction;
import uk.co.leemorris.starfighter.model.OrderType;

/**
 * Holds information for creating new orders.
 * @author lmorris
 */
public class NewOrderDetails {

    private String account;
    private String venue;
    private String stock;
    private int price;
    private int qty;
    private Direction direction;
    private OrderType orderType;

    public NewOrderDetails(String account, String venue, String stock,
                           int price, int qty, Direction direction, OrderType orderType) {
        this.account = account;
        this.venue = venue;
        this.stock = stock;
        this.price = price;
        this.qty = qty;
        this.direction = direction;
        this.orderType = orderType;
    }

    public NewOrderDetails() {

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
