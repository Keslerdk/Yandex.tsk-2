package com.example.yandextsk2.ui.stocks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.StocksRepo;
import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class StocksViewModel extends AndroidViewModel {

    private StocksRepo stocksRepo;
    private LiveData<List<StockSymbol>> stockSymbolLiveData;

    public StocksViewModel(@NonNull Application application) {
        super(application);
        stocksRepo = new StocksRepo(application);
        stockSymbolLiveData = stocksRepo.getAllStockSymbols();
    }
    // TODO: Implement the ViewModel
    public void insert(StockSymbol stockSymbol) {stocksRepo.insert(stockSymbol);}
    public void deleteAllStockSymbol() {
        stocksRepo.delteAllStockSymbol();
    }
    public LiveData<List<StockSymbol>> getAllStockSymbols() {return stockSymbolLiveData;}
    public LiveData<StockSymbol> getItemStockSymbol(String symbol) {
        return stocksRepo.getItemStockSymbol(symbol);
    }
}