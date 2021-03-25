package com.example.yandextsk2.ui.stocks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.StocksRepo;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.Favourite;
import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class StocksViewModel extends AndroidViewModel {

    private StocksRepo stocksRepo;
    private LiveData<List<StockSymbol>> stockSymbolLiveData;
    private LiveData<List<Base>> baseLiveData;
    private LiveData<List<Favourite>> favouriteLiveData;

    public StocksViewModel(@NonNull Application application) {
        super(application);
        stocksRepo = new StocksRepo(application);

        stockSymbolLiveData = stocksRepo.getAllStockSymbols();
        baseLiveData = stocksRepo.getBase();
        favouriteLiveData = stocksRepo.getFavourite();
    }
    // TODO: Implement the ViewModel
    public void insert(StockSymbol stockSymbol) {stocksRepo.insert(stockSymbol);}
    public void insert (Base base) {
        stocksRepo.insert(base);
    }
    public void insert (Favourite favourite) {stocksRepo.insert(favourite);}

    public void updateCurrentPrice(String currentPrice, String ticker) {
        stocksRepo.updateCurrentPrice(currentPrice, ticker);
    }
    public void updateDeltaPrice(String deltaPrice, String ticker) {
        stocksRepo.updateDeltaPrice(deltaPrice, ticker);
    }
    public void updateLastPrice(float lastPrice, String ticker) {
        stocksRepo.updateLastPrice(lastPrice, ticker);
    }
    public void updateIsFavourite(boolean isFavourite, String ticker) {
        stocksRepo.updateIsFavourite(isFavourite, ticker);
    }
    public void deleteAllStockSymbol() {
        stocksRepo.delteAllStockSymbol();
    }
    public void delete(Favourite favourite) {
        stocksRepo.delete(favourite);
    }

    public LiveData<List<StockSymbol>> getAllStockSymbols() {return stockSymbolLiveData;}
    public LiveData<StockSymbol> getItemStockSymbol(String symbol) {
        return stocksRepo.getItemStockSymbol(symbol);
    }
    public LiveData<List<Base>> getBase() {
        return baseLiveData;
    }
    public LiveData<Base> getBaseItem(String ticker) {
        return stocksRepo.getBaseItem(ticker);
    }
    public LiveData<Favourite> getFavouriteItem(String ticker) {
        return  stocksRepo.getFavouriteItem(ticker);
    }
    public LiveData<List<Favourite>> getFavourite() {
        return favouriteLiveData;
    }
}