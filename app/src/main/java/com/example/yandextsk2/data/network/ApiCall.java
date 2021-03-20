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
        Call<List<StockSymbol>> callStockSymbol = jsonPlaceHolderApi.getStockSymbol();
        callStockSymbol.enqueue(new Callback<List<StockSymbol>>() {
            @Override
            public void onResponse(Call<List<StockSymbol>> call, Response<List<StockSymbol>> response) {
                List<StockSymbol> callstocksSymbols = response.body();
                for (StockSymbol val : callstocksSymbols) {
                    mViewModel.insert(new StockSymbol(val.getCurrency(), val.getDescription(), val.getDisplaySimbol(),
                            val.getFigi(), val.getMic(), val.getType(), val.getSymbol()));
                }
            }

            @Override
            public void onFailure(Call<List<StockSymbol>> call, Throwable t) {

            }
        });
    }
}
