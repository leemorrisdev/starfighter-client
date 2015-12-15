package uk.co.leemorris.starfighter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import rx.Observable;
import uk.co.leemorris.starfighter.dto.HeartbeatResult;
import uk.co.leemorris.starfighter.dto.NewOrderDetails;
import uk.co.leemorris.starfighter.dto.NewOrderResponse;
import uk.co.leemorris.starfighter.dto.OrderStatusList;
import uk.co.leemorris.starfighter.dto.VenueResult;
import uk.co.leemorris.starfighter.dto.VenueStocks;
import uk.co.leemorris.starfighter.model.OrderCancelResponse;
import uk.co.leemorris.starfighter.model.OrderStatus;
import uk.co.leemorris.starfighter.model.Orderbook;
import uk.co.leemorris.starfighter.model.StockQuote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lmorris
 */
public class StarfighterConnection {

    protected static final String AUTH_HEADER = "X-Starfighter-Authorization";
    private String baseUrl = "https://api.stockfighter.io/ob/api";
    private String baseWebsocketUrl = "wss://www.stockfighter.io/ob/api";

    private final String apiToken;

    public StarfighterConnection(String apiToken) {
        this.apiToken = apiToken;
        Unirest.setObjectMapper(new JacksonObjectMapper());
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setBaseWebsocketUrl(String baseWebsocketUrl) {
        this.baseWebsocketUrl = baseWebsocketUrl;
    }

    /**
     * Check if API is available.
     * @return heartbeat result giving API status
     */
    public Observable<HeartbeatResult> heartbeat() {

        return Observable.from(buildGet("/heartbeat").asObjectAsync(HeartbeatResult.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Check if a venue is available.
     * @param venueName
     * @return venue result giving venue status
     */
    public Observable<VenueResult> venueHeartbeat(String venueName) {

        return Observable.from(buildGet("/venues/" + venueName + "/heartbeat").asObjectAsync(VenueResult.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Get a list of stocks for the given venue.
     * @param venueName
     * @return
     */
    public Observable<VenueStocks> getStocksForVenue(String venueName) {

        return Observable.from(buildGet("/venues/" + venueName + "/stocks").asObjectAsync(VenueStocks.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Get the orderbook for the given stock on the given venue.
     * @param stockName
     * @param venueName
     * @return
     */
    public Observable<Orderbook> getOrderbookForStock(String stockName, String venueName) {

        return Observable.from(buildGet("/venues/" + venueName + "/stocks/" + stockName).asObjectAsync(Orderbook.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Create a new order.
     * @param details
     * @return
     */
    public Observable<NewOrderResponse> newOrder(NewOrderDetails details) {

        return Observable.from(buildPost("/venues/" + details.getVenue() + "/stocks/" + details.getStock()
                + "/orders").body(details).asObjectAsync(NewOrderResponse.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Get a quote for the given stock.
     * @param stock
     * @param venue
     * @return
     */
    public Observable<StockQuote> getQuoteForStock(String stock, String venue) {

        return Observable.from(buildGet("/venues/" + venue + "/stocks/" + stock + "/quote").asObjectAsync(StockQuote.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Get the status of the given order.
     * @param venueName
     * @param stock
     * @param orderId
     * @return
     */
    public Observable<OrderStatus> getOrderStatus(String venueName, String stock, int orderId) {

        return Observable.from(buildGet("/venues/" + venueName + "/stocks/" + stock +
                "/orders/" + orderId).asObjectAsync(OrderStatus.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Request a cancellation of the given order.
     * @param venueName
     * @param stock
     * @param orderId
     * @return
     */
    public Observable<OrderCancelResponse> cancelOrder(String venueName, String stock, int orderId) {

        return Observable.from(buildDelete("/venues/" + venueName + "/stocks/" + stock + "/orders/" +
                orderId).asObjectAsync(OrderCancelResponse.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Get all available order statuses.
     * @param venueName
     * @param account
     * @return
     */
    public Observable<OrderStatusList> getAllOrderStatuses(String venueName, String account) {

        return Observable.from(buildGet("/venues/" + venueName + "/accounts/" + account + "/orders")
                .asObjectAsync(OrderStatusList.class))
                .map(HttpResponse::getBody);
    }

    /**
     * Get all available order statuses for the given stock.
     * @param venueName
     * @param account
     * @param stock
     * @return
     */
    public Observable<OrderStatusList> getAllOrderStatuses(String venueName, String account, String stock) {

        return Observable.from(buildGet("/venues/" + venueName + "/accounts/" + account + "/stocks/" + stock + "/orders")
                .asObjectAsync(OrderStatusList.class))
                .map(HttpResponse::getBody);
    }

    private List<QuoteSubscription> subscriptions = new ArrayList<>();

    /**
     * Subscribe to ticker tape for a specific symbol.
     * @param venue
     * @param account
     * @param symbol
     * @return
     */
    public Observable<StockQuote> subscribeToQuotes(String venue, String account, String symbol) {

        QuoteSubscription subscription = new QuoteSubscription();
        subscriptions.add(subscription);

        return subscription.logon(baseWebsocketUrl, account, venue, symbol);
    }

    /**
     * Subscribe to all symbols on the given venue.
     * @param venue
     * @param account
     * @return
     */
    public Observable<StockQuote> subscribeToQuotes(String venue, String account) {
        QuoteSubscription subscription = new QuoteSubscription();
        subscriptions.add(subscription);

        return subscription.logon(baseWebsocketUrl, account, venue);
    }

    private GetRequest buildGet(String endpoint) {
        return Unirest.get(baseUrl + endpoint)
                .header(AUTH_HEADER, apiToken);
    }

    private HttpRequestWithBody buildPost(String endpoint) {
        return Unirest.post(baseUrl + endpoint)
                .header(AUTH_HEADER, apiToken);
    }

    private HttpRequestWithBody buildDelete(String endpoint) {
        return Unirest.delete(baseUrl + endpoint)
                .header(AUTH_HEADER, apiToken);
    }
}
