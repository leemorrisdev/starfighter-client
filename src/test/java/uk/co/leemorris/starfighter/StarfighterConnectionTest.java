package uk.co.leemorris.starfighter;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.leemorris.starfighter.StarfighterConnection;
import uk.co.leemorris.starfighter.dto.*;
import uk.co.leemorris.starfighter.model.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author lmorris
 */
public class StarfighterConnectionTest {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final String TEST_AUTH_KEY = "abcd1234";

    @Test
    public void heartbeatReturnsTrueResponse() throws Exception {
        stubFor(get(urlEqualTo("/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("heartbeat-success.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        HeartbeatResult result = connection.heartbeat().toBlocking().single();
        assertTrue(result.isOk());
        assertEquals(result.getError(), "");

        verify(getRequestedFor(urlEqualTo("/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void heartbeatReturnsFalseResponse() throws Exception {

        stubFor(get(urlEqualTo("/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("heartbeat-fail.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        HeartbeatResult result = connection.heartbeat().toBlocking().single();
        assertFalse(result.isOk());
        assertEquals(result.getError(), "Server problem");

        verify(getRequestedFor(urlEqualTo("/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void venueHeartbeatReturnsTrueResponse() throws Exception {

        String venueName = "TESTEX";

        stubFor(get(urlEqualTo("/venues/" + venueName + "/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("venue-heartbeat-success.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        VenueResult result = connection.venueHeartbeat(venueName).toBlocking().single();
        assertTrue(result.isOk());
        assertEquals(venueName, result.getVenue());

        verify(getRequestedFor(urlEqualTo("/venues/" + venueName + "/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void venueHeartbeatReturnsFalseResponse() throws Exception {

        String venueName = "TESTEX";

        stubFor(get(urlEqualTo("/venues/" + venueName + "/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("venue-heartbeat-fail.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        VenueResult result = connection.venueHeartbeat(venueName).toBlocking().single();
        assertFalse(result.isOk());
        assertEquals(venueName, result.getVenue());

        verify(getRequestedFor(urlEqualTo("/venues/" + venueName + "/heartbeat"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void stocksForVenueReturnsStocks() throws Exception {

        String venue = "TESTEX";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("venue-stocks.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        VenueStocks result = connection.getStocksForVenue("TESTEX").toBlocking().single();
        assertTrue(result.isOk());
        assertEquals(2, result.getSymbols().size());
        assertEquals("FOOBAR", result.getSymbols().get(0).getSymbol());
        assertEquals("Foreign Owned Occluded Bridge Architecture Resources", result.getSymbols().get(0).getName());
        assertEquals("DFE", result.getSymbols().get(1).getSymbol());
        assertEquals("DFE Inc", result.getSymbols().get(1).getName());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void stocksForVenuesReturnsFalse() throws Exception {

        String venue = "TESTEX";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("venue-no-stocks.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        VenueStocks result = connection.getStocksForVenue("TESTEX").toBlocking().single();
        assertFalse(result.isOk());
        assertEquals(0, result.getSymbols().size());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void orderBookForStockReturnsBidOrders() throws Exception {

        String venue = "TESTEX";
        String stock = "FOOBAR";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks/" + stock))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("orderbook-bids.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        Orderbook orderbook = connection.getOrderbookForStock(stock, venue).toBlocking().single();

        assertTrue(orderbook.isOk());
        assertEquals(orderbook.getVenue(), venue);
        assertEquals(orderbook.getSymbol(), stock);
        assertEquals(9, orderbook.getBids().size());
        assertEquals(0, orderbook.getAsks().size());

        assertEquals(234, orderbook.getBids().get(0).getPrice());
        assertEquals(100, orderbook.getBids().get(0).getQty());
        assertTrue(orderbook.getBids().get(0).isBuy());
        assertEquals(100, orderbook.getBids().get(5).getPrice());
        assertEquals(200, orderbook.getBids().get(5).getQty());
        assertTrue(orderbook.getBids().get(1).isBuy());

        Date date = format.parse("2015-12-14T19:05:18.739");

        assertEquals(date, orderbook.getTs());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void orderBookForStockReturnsAskOrders() throws Exception {

        String venue = "TESTEX";
        String stock = "FOOBAR";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks/" + stock))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("orderbook-asks.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        Orderbook orderbook = connection.getOrderbookForStock(stock, venue).toBlocking().single();

        assertTrue(orderbook.isOk());
        assertEquals(0, orderbook.getBids().size());
        assertEquals(2, orderbook.getAsks().size());

        assertEquals(234, orderbook.getAsks().get(0).getPrice());
        assertEquals(100, orderbook.getAsks().get(0).getQty());
        assertFalse(orderbook.getAsks().get(0).isBuy());
        assertEquals(100, orderbook.getAsks().get(1).getPrice());
        assertEquals(200, orderbook.getAsks().get(1).getQty());
        assertFalse(orderbook.getAsks().get(1).isBuy());

        Date date = format.parse("2015-12-14T19:05:18.739");

        assertEquals(date, orderbook.getTs());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void emptyOrderBookReturnsEmptySides() throws Exception {

        String venue = "TESTEX";
        String stock = "FOOBAR";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks/" + stock))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("orderbook-empty.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        Orderbook orderbook = connection.getOrderbookForStock(stock, venue).toBlocking().single();

        assertTrue(orderbook.isOk());
        assertEquals(0, orderbook.getAsks().size());
        assertEquals(0, orderbook.getBids().size());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void newOrderReturnsSuccess() throws Exception {

        String account = "EXB123456";
        String venue = "TESTEX";
        String stock = "FOOBAR";
        int price = 43;
        int qty = 422;
        Direction direction = Direction.BUY;
        OrderType orderType = OrderType.LIMIT;

        stubFor(post(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/orders"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("new-order.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        NewOrderResponse response = connection.newOrder(
                new NewOrderDetails(account, venue, stock, price, qty, direction, orderType)).toBlocking().single();

        assertTrue(response.isOk());
        assertEquals(account, response.getAccount());
        assertEquals(venue, response.getVenue());
        assertEquals(stock, response.getSymbol());
        assertEquals(price, response.getPrice());
        assertEquals(qty, response.getQty());
        assertEquals(qty, response.getOriginalQty());
        assertEquals(Direction.BUY, response.getDirection());
        assertEquals(OrderType.LIMIT, response.getOrderType());
        assertEquals(161, response.getId());
        assertEquals(format.parse("2015-12-14T20:37:48.338"), response.getTs());
        assertEquals(0, response.getTotalFilled());
        assertTrue(response.isOpen());
        assertEquals(0, response.getFills().size());

        verify(postRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/orders"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void quoteForStockReturnsPrice() throws Exception {

        String venue = "TESTEX";
        String stock = "FOOBAR";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/quote"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("quote.json"))));


        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        StockQuote quote = connection.getQuoteForStock(stock, venue).toBlocking().single();

        assertTrue(quote.isOk());
        assertEquals(stock, quote.getSymbol());
        assertEquals(venue, quote.getVenue());
        assertEquals(2, quote.getBid());
        assertEquals(3, quote.getAsk());
        assertEquals(200, quote.getBidSize());
        assertEquals(3, quote.getAskSize());
        assertEquals(299, quote.getBidDepth());
        assertEquals(207, quote.getAskDepth());
        assertEquals(2, quote.getLast());
        assertEquals(2, quote.getLastSize());

        Date lastTradeDate = format.parse("2015-12-14T21:17:57.073");
        Date quoteTime = format.parse("2015-12-14T21:18:08.307");

        assertEquals(quote.getLastTrade(), lastTradeDate);
        assertEquals(quote.getQuoteTime(), quoteTime);

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/quote"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void statusForExistingOrderReturnsStatus() throws Exception {

        String venue = "TESTEX";
        String stock = "FOOBAR";

        stubFor(get(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/orders/167"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("order-status.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        OrderStatus status = connection.getOrderStatus(venue, stock, 167).toBlocking().single();

        assertTrue(status.isOk());
        assertEquals(stock, status.getSymbol());
        assertEquals(venue, status.getVenue());
        assertEquals(Direction.BUY, status.getDirection());
        assertEquals(422, status.getOriginalQty());
        assertEquals(0, status.getQty());
        assertEquals(43, status.getPrice());
        assertEquals(OrderType.LIMIT, status.getOrderType());
        assertEquals(167, status.getId());
        assertEquals("EXB123456", status.getAccount());
        assertEquals(1, status.getFills().size());
        assertEquals(422, status.getTotalFilled());
        assertEquals(422, status.getFills().get(0).getQty());
        assertEquals(43, status.getFills().get(0).getPrice());
        assertFalse(status.isOpen());

        Date date = format.parse("2015-12-14T21:03:49.073");

        assertEquals(date, status.getTs());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/orders/167"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void cancelOrderCancelsOrder() throws Exception {

        String venue = "TESTEX";
        String stock = "FOOBAR";

        stubFor(delete(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/orders/167"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("cancel-ack.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        OrderCancelResponse response = connection.cancelOrder(venue, stock, 167).toBlocking().single();

        assertTrue(response.isOk());
        assertFalse(response.isOpen());
        assertEquals("FOO123", response.getAccount());
        assertEquals(venue, response.getVenue());
        assertEquals(stock, response.getSymbol());
        assertEquals(Direction.BUY, response.getDirection());
        assertEquals(1, response.getFills().size());
        assertEquals(OrderType.IOC, response.getOrderType());
        assertEquals(167, response.getId());
        assertEquals(0, response.getQty());
        assertEquals(85, response.getOriginalQty());
        assertEquals(85, response.getTotalFilled());
        assertEquals(993, response.getPrice());

        Date date = format.parse("2015-08-10T08:10:32.987");
        assertEquals(response.getTs(), date);

        verify(deleteRequestedFor(urlEqualTo("/venues/" + venue + "/stocks/" + stock + "/orders/167"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void getOrderStatusesGetsStatuses() throws Exception {

        String venue = "ROBUST";
        String account = "EX12345";

        stubFor(get(urlEqualTo("/venues/" + venue + "/accounts/" + account + "/orders"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("all-orders.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        OrderStatusList statuses = connection.getAllOrderStatuses(venue, account).toBlocking().single();

        assertTrue(statuses.isOk());
        assertEquals(venue, statuses.getVenue());
        assertEquals(1, statuses.getOrders().size());

        Date date = format.parse("2015-08-10T08:10:32.987");

        OrderStatus status = statuses.getOrders().get(0);

        assertEquals("ROBO", status.getSymbol());
        assertEquals(venue, status.getVenue());
        assertEquals(Direction.BUY, status.getDirection());
        assertEquals(85, status.getOriginalQty());
        assertEquals(40, status.getQty());
        assertEquals(993, status.getPrice());
        assertEquals(OrderType.IOC, status.getOrderType());
        assertEquals(1, status.getId());
        assertEquals("FOO123", status.getAccount());
        assertEquals(1, status.getFills().size());
        assertEquals(85, status.getTotalFilled());
        assertEquals(45, status.getFills().get(0).getQty());
        assertEquals(366, status.getFills().get(0).getPrice());
        assertEquals(date, status.getFills().get(0).getTs());
        assertTrue(status.isOpen());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/accounts/" + account + "/orders"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }

    @Test
    public void getOrderStatusesForStockGetsStatuses() throws Exception {

        String venue = "ROBUST";
        String account = "EX12345";
        String stock = "ROBO";

        stubFor(get(urlEqualTo("/venues/" + venue + "/accounts/" + account + "/stocks/" + stock + "/orders"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtil.getJSON("all-orders.json"))));

        StarfighterConnection connection = new StarfighterConnection(TEST_AUTH_KEY);
        connection.setBaseUrl("http://localhost:8089");

        OrderStatusList statuses = connection.getAllOrderStatuses(venue, account, stock).toBlocking().single();

        assertTrue(statuses.isOk());
        assertEquals(venue, statuses.getVenue());
        assertEquals(1, statuses.getOrders().size());

        OrderStatus status = statuses.getOrders().get(0);

        assertEquals(stock, status.getSymbol());
        assertEquals(venue, status.getVenue());
        assertEquals(Direction.BUY, status.getDirection());
        assertEquals(85, status.getOriginalQty());
        assertEquals(40, status.getQty());
        assertEquals(993, status.getPrice());
        assertEquals(OrderType.IOC, status.getOrderType());
        assertEquals(1, status.getId());
        assertEquals("FOO123", status.getAccount());
        assertEquals(1, status.getFills().size());
        assertEquals(85, status.getTotalFilled());
        assertEquals(45, status.getFills().get(0).getQty());
        assertEquals(366, status.getFills().get(0).getPrice());
        assertTrue(status.isOpen());

        verify(getRequestedFor(urlEqualTo("/venues/" + venue + "/accounts/" + account + "/stocks/" + stock + "/orders"))
                .withHeader(StarfighterConnection.AUTH_HEADER, equalTo(TEST_AUTH_KEY)));
    }
}
