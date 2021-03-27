package com.example.yandextsk2.data.network;


import com.example.yandextsk2.data.db.entity.StockSymbol;
import com.example.yandextsk2.ui.SearchViewModel;
import com.example.yandextsk2.ui.stocks.StocksViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {

    private StocksViewModel stocksViewModel;
    private SearchViewModel searchViewModel;

    public ApiCall(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    public ApiCall(StocksViewModel mViewModel) {
        this.stocksViewModel = mViewModel;
    }

    public void firstApiCall() {
        ApiRequests jsonPlaceHolderApi = ApiRequests.invoke();
        Call<List<StockSymbol>> callStockSymbol = jsonPlaceHolderApi.getStockSymbol("US");
        callStockSymbol.enqueue(new Callback<List<StockSymbol>>() {
            @Override
            public void onResponse(Call<List<StockSymbol>> call, Response<List<StockSymbol>> response) {
                List<StockSymbol> stocksSymbols = response.body();
                if (stocksViewModel==null) {searchViewModel.deleteAllStockSymbol();}
                else stocksViewModel.deleteAllStockSymbol();
                for (StockSymbol val : stocksSymbols) {
//                    Log.d("in api call", val.getSymbol());
                    if (stocksViewModel==null) {searchViewModel.insert(new StockSymbol(val.getCurrency(), val.getDescription(), val.getDisplaySimbol(),
                            val.getFigi(), val.getMic(),  val.getSymbol(), val.getType()));}

                    else {stocksViewModel.insert(new StockSymbol(val.getCurrency(), val.getDescription(), val.getDisplaySimbol(),
                            val.getFigi(), val.getMic(),  val.getSymbol(), val.getType())); }
                }
            }

            @Override
            public void onFailure(Call<List<StockSymbol>> call, Throwable t) {

            }
        });
    }

    public void quoteApiCall(String symbol) {
        ApiRequests jsonPlaceHolderApi = ApiRequests.invoke();
        Call<Quote> callQuote = jsonPlaceHolderApi.getQuote(symbol);
        callQuote.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                if (stocksViewModel==null) {
//                    searchViewModel.updateCurrentPrice(String.valueOf(response.body().getC()), symbol);
//                    stocksViewModel.updateLastPrice(response.body().getPc(), symbol);
//                    stocksViewModel.updateDeltaPrice(String.valueOf(response.body().getC()- response.body().getPc()), symbol);
                }
                stocksViewModel.updateCurrentPrice(String.valueOf(response.body().getC()), symbol);
                stocksViewModel.updateLastPrice(response.body().getPc(), symbol);
                stocksViewModel.updateDeltaPrice(String.valueOf(response.body().getC()- response.body().getPc()), symbol);
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {

            }
        });
    }
}
