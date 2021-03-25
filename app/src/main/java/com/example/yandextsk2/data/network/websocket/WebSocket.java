package com.example.yandextsk2.data.network.websocket;

import android.util.Log;

import com.example.yandextsk2.ui.favourite.FavouriteViewModel;
import com.example.yandextsk2.ui.stocks.StocksViewModel;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

public class WebSocket{

    String TAG = "Web Socket";
    URI WEB_SOCKET_URL = URI.create("wss://ws.finnhub.io?token=c0mlc6v48v6tkq133gdg");

    WebSocketClient webSocketClient;

    private List<String> symbols = new ArrayList<>();
    private StocksViewModel mViewModelStocks;
    private FavouriteViewModel mViewModelFav;

    float lastPrice;

    public WebSocket(StocksViewModel mViewModel) {
        this.mViewModelStocks = mViewModel;
    }
    public WebSocket(FavouriteViewModel mViewModelFav) {
        this.mViewModelFav = mViewModelFav;
    }

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
                if (mViewModelFav==null) setUpMsg(message, mViewModelStocks);
                else setUpMsg(message, mViewModelFav);

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

    private void setUpMsg (String message, StocksViewModel mViewModelStocks) {
        Log.d("message", message);
        Gson g = new Gson();
        ParseWebSocket parseMessage = g.fromJson(message, ParseWebSocket.class);
        for (ParseWebSocket.Data data : parseMessage.getData()) {
            mViewModelStocks.updateCurrentPrice(String.valueOf(data.getP()), data.getS());
//            mViewModel.updateDeltaPrice(String.valueOf(lastPrice - data.getP()), data.getS());
        }
//        Log.d("Parse message", parseMessage.getData().get(0).getS());
    }

    private void setUpMsg (String message, FavouriteViewModel mViewModelStocks) {
        Log.d("message", message);
        Gson g = new Gson();
        ParseWebSocket parseMessage = g.fromJson(message, ParseWebSocket.class);
        for (ParseWebSocket.Data data : parseMessage.getData()) {
            mViewModelStocks.updateCurrentPriceFav(String.valueOf(data.getP()), data.getS());
//            mViewModel.updateDeltaPrice(String.valueOf(lastPrice - data.getP()), data.getS());
        }
//        Log.d("Parse message", parseMessage.getData().get(0).getS());
    }

    public void setLastPrice(float lastPrice) {
        this.lastPrice = lastPrice;
    }
}
