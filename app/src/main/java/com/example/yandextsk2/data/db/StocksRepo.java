package com.example.yandextsk2.data.db;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.dao.BaseDao;
import com.example.yandextsk2.data.db.dao.FavouriteDao;
import com.example.yandextsk2.data.db.dao.StockSymbolDao;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.Favourite;
import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

public class StocksRepo {
    private StockSymbolDao stockSymbolDao;
    private BaseDao baseDao;
    private FavouriteDao favouriteDao;

    LiveData<List<StockSymbol>> stocksLiveData;
    LiveData<List<Base>> baseLiveData;
    LiveData<List<Favourite>> favouriteLiveData;

    public StocksRepo(Application application) {
        StocksDatabase stocksDatabase = StocksDatabase.getDatabase(application);

        stockSymbolDao = stocksDatabase.stockSymbolDao();
        baseDao = stocksDatabase.baseDao();
        favouriteDao = stocksDatabase.favouriteDao();

        stocksLiveData = stockSymbolDao.getAllStockSymbol();
        baseLiveData = baseDao.getBase();
        favouriteLiveData = favouriteDao.getFavourite();

    }

    //stocks symbols
    public void insert(StockSymbol stockSymbol) {
        new InserAsyncTask(stockSymbolDao).execute(stockSymbol);
    }

    public void delteAllStockSymbol() {
        new DelteAllAsyncTask(stockSymbolDao).execute();
    }

    public LiveData<List<StockSymbol>> getAllStockSymbols() {
        return stocksLiveData;
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

    public void updateIsFavourite(boolean isFavourite, String ticker) {
        new UpdateIsFavouriteAsyncTask(baseDao, ticker).execute(isFavourite);
    }

    public LiveData<List<Base>> getBase() {
        return baseLiveData;
    }

    public LiveData<Base> getBaseItem(String ticker) {
        return baseDao.getBaseItem(ticker);
    }

    //favourite
    public void insert(Favourite favourite) {
        new InsertFavouriteAsyncTask(favouriteDao).execute(favourite);
    }

    public void delete(Favourite favourite) {
        new DeleteFavouriteAsycTask(favouriteDao).execute(favourite);
    }

    public void updateCurrentPriceFav(String currentPrice, String ticker) {
        new UpdateCurPriceFav(favouriteDao, ticker).execute(currentPrice);
    }

    public void updateDeltaPriceFav(String deltaPrice, String ticker) {
        new UpdateDeltaPricaFav(favouriteDao, ticker).execute(ticker);
    }

    public Favourite getFavItemConst(String ticker) {
        return  favouriteDao.getFavItemConst(ticker);
    }

    public LiveData<List<Favourite>> getFavourite() {
        return favouriteLiveData;
    }

    public LiveData<Favourite> getFavouriteItem(String ticker) {
        return favouriteDao.getFavouriteItem(ticker);
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

    private class DelteAllAsyncTask extends AsyncTask<Void, Void, Void> {

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

    private class UpdateCurPriceAsynTask extends AsyncTask<String, Void, Void> {
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

    private class InsertBaseAsyncTask extends AsyncTask<Base, Void, Void> {
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

    private class UpdateDeltaPriceAsyncTask extends AsyncTask<String, Void, Void> {
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

    private class UpdateLastPriceAsyncTask extends AsyncTask<Float, Void, Void> {
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

    private class UpdateCurPriceFav extends AsyncTask<String, Void, Void>{
        private FavouriteDao favouriteDao;
        private String ticker;

        public UpdateCurPriceFav(FavouriteDao favouriteDao, String ticker) {
            this.favouriteDao = favouriteDao;
            this.ticker = ticker;
        }

        @Override
        protected Void doInBackground(String... strings) {
            favouriteDao.updateCurrentPriceFav(strings[0], ticker);
            return null;
        }
    }

    private class UpdateDeltaPricaFav extends AsyncTask<String, Void, Void> {
        private FavouriteDao favouriteDao;
        private String ticker;

        public UpdateDeltaPricaFav(FavouriteDao favouriteDao, String ticker) {
            this.favouriteDao = favouriteDao;
            this.ticker = ticker;
        }

        @Override
        protected Void doInBackground(String... strings) {
            favouriteDao.updateDeltaPriceFav(strings[0], ticker);
            return null;
        }
    }

    private class InsertFavouriteAsyncTask extends AsyncTask<Favourite, Void, Void>{
        private FavouriteDao favouriteDao;

        public InsertFavouriteAsyncTask(FavouriteDao favouriteDao) {
            this.favouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(Favourite... favourites) {
            favouriteDao.insert(favourites[0]);
            return null;
        }
    }

    private class DeleteFavouriteAsycTask extends AsyncTask<Favourite, Void, Void>{
        private FavouriteDao favouriteDao;

        public DeleteFavouriteAsycTask(FavouriteDao favouriteDao) {
            this.favouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(Favourite... favourites) {
            favouriteDao.delete(favourites[0]);
            return null;
        }
    }

    private class UpdateIsFavouriteAsyncTask extends AsyncTask<Boolean, Void, Void>{
        private BaseDao baseDao;
        private String ticker;

        public UpdateIsFavouriteAsyncTask(BaseDao baseDao, String ticker) {
            this.baseDao = baseDao;
            this.ticker = ticker;
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            baseDao.updateIsFavourite(booleans[0], ticker);
            return null;
        }
    }
}
