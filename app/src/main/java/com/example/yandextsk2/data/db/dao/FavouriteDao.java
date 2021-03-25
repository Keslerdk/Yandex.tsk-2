package com.example.yandextsk2.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yandextsk2.data.db.entity.Favourite;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favourite favourite);

    @Query("UPDATE favourite set currentPrice =:currentPrice where ticker =:ticker")
    void updateCurrentPriceFav(String currentPrice, String ticker);

    @Query("UPDATE favourite set deltaPrice =:deltaPrice where ticker =:ticker")
    void updateDeltaPriceFav(String deltaPrice, String ticker);

    @Query("select * from favourite")
    LiveData<List<Favourite>> getFavourite();

    @Query("select * from favourite where ticker =:ticker")
    LiveData<Favourite> getFavouriteItem(String ticker);

    @Delete
    void delete(Favourite favourite);

    @Query("select * from favourite where ticker =:ticker")
    Favourite getFavItemConst(String ticker);
}
