package com.example.yandextsk2.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yandextsk2.data.db.entity.StockSymbol;

import java.util.List;

@Dao
public interface StockSymbolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StockSymbol stockSymbol);

    @Query("select * from stock_symbol")
    LiveData<List<StockSymbol>> getAllStockSymbol();

    @Query("select * from stock_symbol where symbol = :symbol")
    LiveData<StockSymbol> getItemStockSymbol(String symbol);

    @Query("DELETE FROM stock_symbol")
    void deleteAllStockSymbol();

    @Query("SELECT * FROM stock_symbol WHERE symbol LIKE '%' || :searchQuery  || '%' OR description LIKE '%' || :searchQuery  || '%'")
    LiveData<List<StockSymbol>> searchDatabase(String searchQuery);

//    @Query("UPDATE stock_symbol set deltaPrice =:deltaPrice where ticker =:ticker")
//    void updateDeltaPrice(String deltaPrice, String ticker);
//
//    @Query("UPDATE stock_symbol set lastPrice =:lastPrice where ticker =:ticker")
//    void updateLastPrice(float lastPrice, String ticker);
//
//    @Query("UPDATE stock_symbol set isFavourite =:isFavourite where ticker=:ticker")
//    void updateIsFavourite(boolean isFavourite, String ticker);

}
