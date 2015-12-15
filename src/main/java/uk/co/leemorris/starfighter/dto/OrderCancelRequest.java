package uk.co.leemorris.starfighter.dto;

/**
 * @author lmorris
 */
public class OrderCancelRequest {

    private String venue;
    private String stock;
    private int orderId;

    private OrderCancelRequest() {

    }

    public OrderCancelRequest(String venue, String stock, int orderId) {
        this.venue = venue;
        this.stock = stock;
        this.orderId = orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
