package com.example.yandextsk2.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {


    public void firstApiCall() {
        ApiRequests jsonPlaceHolderApi = ApiRequests.invoke();
        Call<List<StockSymbol>> callStockSymbol = jsonPlaceHolderApi.getStockSymbol();
        callStockSymbol.enqueue(new Callback<List<StockSymbol>>() {
            @Override
            public void onResponse(Call<List<StockSymbol>> call, Response<List<StockSymbol>> response) {

            }

            @Override
            public void onFailure(Call<List<StockSymbol>> call, Throwable t) {

            }
        });
    }
}
