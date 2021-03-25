package com.example.yandextsk2.data.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.dao.BaseDao;
import com.example.yandextsk2.data.db.dao.FavouriteDao;
import com.example.yandextsk2.data.db.dao.StockSymbolDao;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.Favourite;
import com.example.yandextsk2.data.db.entity.StockSymbol;

@androidx.room.Database(entities = {StockSymbol.class, Base.class, Favourite.class},
    version = 5)
public abstract class StocksDatabase extends RoomDatabase{

    public abstract StockSymbolDao stockSymbolDao();
    public abstract BaseDao baseDao();
    public abstract FavouriteDao favouriteDao();
    public static StocksDatabase instance;

    public static StocksDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (StocksDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            StocksDatabase.class, "stocks.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//        }
//    };

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(instance).execute();
        }
    };

    private static class PopulatedbAsyncTask extends AsyncTask<Void, Void, Void> {

        private BaseDao baseDao;

        public PopulatedbAsyncTask(StocksDatabase db) {
            baseDao = db.baseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            baseDao.insert(new Base(R.drawable.yndx, "YNDX", "Yandex, LLC", "0", "0"));
            baseDao.insert(new Base(R.drawable.aapl, "AAPL", "Apple Inc.", "0", "0"));
            baseDao.insert(new Base(R.drawable.googl, "GOOGL", "Alphabet Class A", "0", "0"));
            baseDao.insert(new Base(R.drawable.amzn, "AMZN", "Amazon.com", "0", "0"));
            baseDao.insert(new Base(R.drawable.bac, "BAC", "Bank of America Corp", "0", "0"));
            baseDao.insert(new Base(R.drawable.msft, "MSFT", "Microsoft Corporation", "0", "0"));
            baseDao.insert(new Base(R.drawable.tsla, "TSLA", "Tesla Motors", "0", "0"));
            baseDao.insert(new Base(R.drawable.ma, "MA", "Mastercard", "0", "0"));
            return null;
        }
    }
}
