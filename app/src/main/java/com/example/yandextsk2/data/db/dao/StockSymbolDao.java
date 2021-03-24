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

}
