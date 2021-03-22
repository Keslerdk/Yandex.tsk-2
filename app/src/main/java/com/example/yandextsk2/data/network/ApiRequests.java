package com.example.yandextsk2.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequests {
    String API_KEY = "c0mlc6v48v6tkq133gdg";

    //https://finnhub.io/api/v1/stock/symbol?exchange=US&token=c0mlc6v48v6tkq133gdg
    @GET("stock/symbol")
    Call<List<StockSymbol>> getStockSymbol(
            @Query("exchange") String exchange
    );

//    https://finnhub.io/api/v1/quote?symbol=AAPL&token=c0mlc6v48v6tkq133gdg
    @GET("quote")
    Call<Quote> getQuote(
            @Query("symbol") String symbol
    );

    static ApiRequests invoke() {
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                HttpUrl url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("token", API_KEY)
                        .build();

                Request request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);
            }
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        ApiRequests builder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://finnhub.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests.class);
        return builder;
    }

    static Boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiCon != null && wifiCon.isConnected() || mobCon != null && mobCon.isConnected());
    }
}
