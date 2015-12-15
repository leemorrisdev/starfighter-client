package uk.co.leemorris.starfighter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import rx.Observable;
import rx.Subscriber;
import uk.co.leemorris.starfighter.model.QuoteSubscriptionWrapper;
import uk.co.leemorris.starfighter.model.StockQuote;

import java.io.IOException;
import java.net.URI;

/**
 * @author lmorris
 */
@WebSocket
public class QuoteSubscription implements WebSocketListener {

    private ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient webSocketClient;
    private String baseUrl;
    private Session session;

    private Subscriber<? super StockQuote> quoteSubscriber;

    public Observable<StockQuote> logon(String baseUrl, String account, String venue) {
        return logon(baseUrl + "/ws/" + account + "/venues/" + venue + "/tickertape/");
    }

    public Observable<StockQuote> logon(String baseUrl, String account, String venue, String stock) {
        return logon(baseUrl + "/ws/" + account + "/venues/" + venue + "/tickertape/stocks/" + stock);
    }

    private Observable<StockQuote> logon(String url) {
        return Observable.create(new Observable.OnSubscribe<StockQuote>() {
            @Override
            public void call(Subscriber<? super StockQuote> subscriber) {

                quoteSubscriber = subscriber;

                try {

                    if(url.startsWith("wss:")) {
                        webSocketClient = new WebSocketClient(new SslContextFactory());
                    } else {
                        webSocketClient = new WebSocketClient();
                    }

                    URI uri = new URI(url);
                    webSocketClient.start();
                    webSocketClient.connect(QuoteSubscription.this, uri);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if(!quoteSubscriber.isUnsubscribed()) {
                        subscriber.onError(ex);
                    }
                }
            }
        });
    }



    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {

    }

    @Override
    public void onWebSocketText(String msg) {
        System.out.println(msg);
        try {
            QuoteSubscriptionWrapper wrapper = objectMapper.readValue(msg, QuoteSubscriptionWrapper.class);

            if(!quoteSubscriber.isUnsubscribed()) {
                quoteSubscriber.onNext(wrapper.getQuote());
            }
        } catch (IOException e) {
            if(!quoteSubscriber.isUnsubscribed()) {
                quoteSubscriber.onError(e);
            }
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println(statusCode + " " + reason);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        System.out.println("Connected");
    }

    @Override
    public void onWebSocketError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
