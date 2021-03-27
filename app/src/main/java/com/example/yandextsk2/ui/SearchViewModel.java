package com.example.yandextsk2.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.StocksRepo;
import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    StocksRepo stocksRepo;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        stocksRepo = new StocksRepo(application);
    }

    public void insert(StockSymbol stockSymbol) {
        stocksRepo.insert(stockSymbol);
    }
    public void deleteAllStockSymbol() {
        stocksRepo.delteAllStockSymbol();
    }
    public LiveData<List<StockSymbol>> searchDatabase (String query) {
        return stocksRepo.searchDatabase(query);
    }
}
