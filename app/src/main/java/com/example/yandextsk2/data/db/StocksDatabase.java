package com.example.yandextsk2.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.yandextsk2.data.db.entity.StockSymbol;

@androidx.room.Database(entities = {StockSymbol.class},
    version = 1)
public abstract class StocksDatabase extends RoomDatabase{

    public abstract StockSymbolDao stockSymbolDao();
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

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
