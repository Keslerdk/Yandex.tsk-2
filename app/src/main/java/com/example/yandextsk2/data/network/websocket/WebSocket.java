package com.example.yandextsk2.data.network.websocket;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

public class WebSocket{


    List<String> symbols = new ArrayList<>();
    URI WEB_SOCKET_URL = URI.create("wss://ws.finnhub.io?token=c0mlc6v48v6tkq133gdg");
    WebSocketClient webSocketClient;

    public void closeWebSocket() {
        webSocketClient.close();
    }

    public void initWebSocket() {
        Log.d("in onresume", "yes");
        URI coinbaseUri = WEB_SOCKET_URL;
        symbols.add("YNDX");
        symbols.add("AAPL");
        symbols.add("GOOGL");
        symbols.add("AMZN");
        symbols.add("BAC");
        symbols.add("MSFT");
        symbols.add("TSLA");
        symbols.add("MA");

        createWebSocketClient(coinbaseUri);

        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        webSocketClient.setSocketFactory(socketFactory);
        webSocketClient.connect();
    }

    private void createWebSocketClient(URI coinbaseUri) {
        webSocketClient = new WebSocketClient(coinbaseUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                subscribe();
            }

            @Override
            public void onMessage(String message) {
                setUpMsg(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };
    }

    private void subscribe() {
        for (String val :symbols)
//        webSocketClient.send("{\"type\":\"subscribe\",\"symbol\":\"MSFT\"}");
        webSocketClient.send("{\"type\":\"subscribe\",\"symbol\":\"" +  val + "\"}");

    }

    private void setUpMsg (String message) {
        Log.d("message", message);
    }
}
