package com.example.yandextsk2.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class StocksRepo {
    private StockSymbolDao stockSymbolDao;
    LiveData<List<StockSymbol>> stocksLiveData;

    public StocksRepo(Application application) {
        StocksDatabase stocksDatabase = StocksDatabase.getDatabase(application);

        stockSymbolDao = stocksDatabase.stockSymbolDao();
        stocksLiveData = stockSymbolDao.getAllStockSymbol();
    }

    public void insert(StockSymbol stockSymbol) {
        new InserAsyncTask(stockSymbolDao).execute(stockSymbol);
    }

    public LiveData<List<StockSymbol>> getAllStockSymbols() {
        return  stocksLiveData;
    }
    public LiveData<StockSymbol> getItemStockSymbol(String symbol) {
        return stockSymbolDao.getItemStockSymbol(symbol);
    }

    private class InserAsyncTask extends AsyncTask<StockSymbol, Void, Void> {

        private StockSymbolDao stockSymbolDao;
        public InserAsyncTask(StockSymbolDao stockSymbolDao) {
            this.stockSymbolDao = stockSymbolDao;
        }

        @Override
        protected Void doInBackground(StockSymbol... stockSymbols) {
            stockSymbolDao.insert(stockSymbols[0]);
            return null;
        }
    }
}
