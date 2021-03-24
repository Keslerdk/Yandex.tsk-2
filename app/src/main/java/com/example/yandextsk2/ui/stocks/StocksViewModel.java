package com.example.yandextsk2.ui.stocks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.StocksRepo;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class StocksViewModel extends AndroidViewModel {

    private StocksRepo stocksRepo;
    private LiveData<List<StockSymbol>> stockSymbolLiveData;
    private LiveData<List<Base>> baseLiveData;

    public StocksViewModel(@NonNull Application application) {
        super(application);
        stocksRepo = new StocksRepo(application);
        stockSymbolLiveData = stocksRepo.getAllStockSymbols();
        baseLiveData = stocksRepo.getBase();
    }
    // TODO: Implement the ViewModel
    public void insert(StockSymbol stockSymbol) {stocksRepo.insert(stockSymbol);}
    public void insert (Base base) {
        stocksRepo.insert(base);
    }
    public void updateCurrentPrice(String currentPrice, String ticker) {
        stocksRepo.updateCurrentPrice(currentPrice, ticker);
    }
    public void deleteAllStockSymbol() {
        stocksRepo.delteAllStockSymbol();
    }
    public LiveData<List<StockSymbol>> getAllStockSymbols() {return stockSymbolLiveData;}
    public LiveData<StockSymbol> getItemStockSymbol(String symbol) {
        return stocksRepo.getItemStockSymbol(symbol);
    }
    public LiveData<List<Base>> getBase() {
        return baseLiveData;
    }
}