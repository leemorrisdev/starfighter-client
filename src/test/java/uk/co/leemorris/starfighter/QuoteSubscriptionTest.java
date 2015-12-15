package uk.co.leemorris.starfighter;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Subscriber;
import uk.co.leemorris.starfighter.QuoteSubscription;
import uk.co.leemorris.starfighter.model.StockQuote;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author lmorris
 */
public class QuoteSubscriptionTest {

    public QuoteSubscriptionTest() throws Exception {
        TestWebsocketServer testWebsocketServer; testWebsocketServer = new TestWebsocketServer(8091, "tickertape.txt");
        testWebsocketServer.start();
    }

    @Test
    public void subscribeRetrievesQuotes() throws Exception {

        QuoteSubscription subscription = new QuoteSubscription();

        CountDownLatch latch = new CountDownLatch(30);
        List<StockQuote> quotes = Lists.newArrayList();

        subscription.logon("ws://localhost:8091/api", "KAT97952062", "YEPS")
                .subscribe(new Subscriber<StockQuote>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        fail(throwable.getMessage());
                    }

                    @Override
                    public void onNext(StockQuote stockQuote) {
                        quotes.add(stockQuote);
                        latch.countDown();
                    }
                });

        latch.await(5, TimeUnit.SECONDS);

        assertEquals(30, quotes.size());
        assertEquals("YEPS", quotes.get(0).getSymbol());
        assertEquals(5006, quotes.get(0).getBid());
        assertEquals(11731, quotes.get(2).getAskSize());
        assertEquals(5199, quotes.get(2).getAsk());
    }
}
