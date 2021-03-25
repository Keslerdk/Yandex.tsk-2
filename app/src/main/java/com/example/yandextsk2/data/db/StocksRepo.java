package com.example.yandextsk2.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.dao.BaseDao;
import com.example.yandextsk2.data.db.dao.StockSymbolDao;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class StocksRepo {
    private StockSymbolDao stockSymbolDao;
    private BaseDao baseDao;

    LiveData<List<StockSymbol>> stocksLiveData;
    LiveData<List<Base>> baseLiveData;

    public StocksRepo(Application application) {
        StocksDatabase stocksDatabase = StocksDatabase.getDatabase(application);

        stockSymbolDao = stocksDatabase.stockSymbolDao();
        baseDao = stocksDatabase.baseDao();

        stocksLiveData = stockSymbolDao.getAllStockSymbol();
        baseLiveData = baseDao.getBase();
    }

    //stocks symbols
    public void insert(StockSymbol stockSymbol) {
        new InserAsyncTask(stockSymbolDao).execute(stockSymbol);
    }

    public void delteAllStockSymbol() {
        new DelteAllAsyncTask(stockSymbolDao).execute();
    }

    public LiveData<List<StockSymbol>> getAllStockSymbols() {
        return  stocksLiveData;
    }

    public LiveData<StockSymbol> getItemStockSymbol(String symbol) {
        return stockSymbolDao.getItemStockSymbol(symbol);
    }

    //base
    public void insert(Base base) {
        new InsertBaseAsyncTask(baseDao).execute(base);
    }

    public void updateCurrentPrice(String currentPrice, String ticker) {
        new UpdateCurPriceAsynTask(baseDao, ticker).execute(currentPrice);
    }
    public void updateDeltaPrice(String deltaPrice, String ticker) {
        new UpdateDeltaPriceAsyncTask(baseDao, ticker).execute(deltaPrice);
    }

    public void updateLastPrice(float lastPrice, String ticker) {
        new UpdateLastPriceAsyncTask(baseDao, ticker).execute(lastPrice);
    }
    public LiveData<List<Base>> getBase() {
        return baseLiveData;
    }

    public LiveData<Base> getBaseItem(String ticker) {
        return baseDao.getBaseItem(ticker);
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

    private class DelteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private StockSymbolDao stockSymbolDao;

        public DelteAllAsyncTask(StockSymbolDao stockSymbolDao) {
            this.stockSymbolDao = stockSymbolDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stockSymbolDao.deleteAllStockSymbol();
            return null;
        }
    }

    private class UpdateCurPriceAsynTask extends AsyncTask<String, Void, Void>{
        private String ticker;
        private BaseDao baseDao;
        public UpdateCurPriceAsynTask(BaseDao baseDao, String ticker) {
            this.ticker = ticker;
            this.baseDao = baseDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            baseDao.updateCurrentPrice(strings[0], ticker);
            return null;
        }
    }

    private class InsertBaseAsyncTask extends AsyncTask<Base, Void, Void>{
        private BaseDao baseDao;

        public InsertBaseAsyncTask(BaseDao baseDao) {
            this.baseDao = baseDao;
        }

        @Override
        protected Void doInBackground(Base... bases) {
            baseDao.insert(bases[0]);
            return null;
        }
    }

    private class UpdateDeltaPriceAsyncTask extends AsyncTask<String, Void, Void>{
        BaseDao baseDao;
        String symbol;

        public UpdateDeltaPriceAsyncTask(BaseDao baseDao, String symbol) {
            this.baseDao = baseDao;
            this.symbol = symbol;
        }

        @Override
        protected Void doInBackground(String... strings) {
            baseDao.updateDeltaPrice(strings[0], symbol);
            return null;
        }
    }

    private class UpdateLastPriceAsyncTask extends AsyncTask<Float, Void, Void>{
        BaseDao baseDao;
        String symbol;

        public UpdateLastPriceAsyncTask(BaseDao baseDao, String symbol) {
            this.baseDao = baseDao;
            this.symbol = symbol;
        }

        @Override
        protected Void doInBackground(Float... floats) {
            baseDao.updateLastPrice(floats[0], symbol);
            return null;
        }
    }
}
