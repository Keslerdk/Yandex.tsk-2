package com.example.yandextsk2.data.network;


import com.example.yandextsk2.data.db.entity.StockSymbol;
import com.example.yandextsk2.ui.stocks.StocksViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {

    private StocksViewModel mViewModel;

    public ApiCall(StocksViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public void firstApiCall() {
        ApiRequests jsonPlaceHolderApi = ApiRequests.invoke();
        Call<List<StockSymbol>> callStockSymbol = jsonPlaceHolderApi.getStockSymbol("US");
        callStockSymbol.enqueue(new Callback<List<StockSymbol>>() {
            @Override
            public void onResponse(Call<List<StockSymbol>> call, Response<List<StockSymbol>> response) {
                List<StockSymbol> stocksSymbols = response.body();
                mViewModel.deleteAllStockSymbol();
                for (StockSymbol val : stocksSymbols) {
//                    Log.d("in api call", val.getSymbol());
                    mViewModel.insert(new StockSymbol(val.getCurrency(), val.getDescription(), val.getDisplaySimbol(),
                            val.getFigi(), val.getMic(),  val.getSymbol(), val.getType()));
                }
//                quoteApiCall(stocksSymbols.get(0).getSymbol());
            }

            @Override
            public void onFailure(Call<List<StockSymbol>> call, Throwable t) {

            }
        });
    }
    /*
    public void quoteApiCall(String symbol) {
        ApiRequests jsonPlaceHolderApi = ApiRequests.invoke();
        Call<Quote> callQuote = jsonPlaceHolderApi.getQuote(symbol);
        callQuote.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {

            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {

            }
        });
    } */
}
