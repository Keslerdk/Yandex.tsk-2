package com.example.yandextsk2.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.yandextsk2.data.db.entity.Base;

import java.util.List;

@Dao
public interface BaseDao {
    @Insert
    void insert(Base base);

    @Query("select * from base")
    LiveData<List<Base>> getBase();

    @Query("UPDATE base set currentPrice =:currentPrice where ticker =:ticker")
    void updateCurrentPrice(String currentPrice, String ticker);
}