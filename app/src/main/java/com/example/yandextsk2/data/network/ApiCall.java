package com.example.yandextsk2.data.network;

import android.util.Log;

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
                mViewModel.deleteAllStockSymbol();
                for (StockSymbol val : callstocksSymbols) {
                    Log.d("in api call", val.getSymbol());
                    mViewModel.insert(new StockSymbol(val.getCurrency(), val.getDescription(), val.getDisplaySimbol(),
                            val.getFigi(), val.getMic(),  val.getSymbol(), val.getType()));
                }
            }

            @Override
            public void onFailure(Call<List<StockSymbol>> call, Throwable t) {

            }
        });
    }
}
