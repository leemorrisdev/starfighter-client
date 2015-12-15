package uk.co.leemorris.starfighter;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.mortbay.jetty.Server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author lmorris
 */
public class TestWebsocketServer extends WebSocketServer{

    List<String> messages;

    public TestWebsocketServer(int port, String fileName) throws Exception {
        super(new InetSocketAddress(port));
        Path path = Paths.get(TestUtil.class.getResource("/sample-responses/" + fileName).toURI());
        messages = Files.readAllLines(path);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        messages.forEach(conn::send);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }
}
