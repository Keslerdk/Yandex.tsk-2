package com.example.yandextsk2.ui.favourite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yandextsk2.data.db.StocksRepo;
import com.example.yandextsk2.data.db.entity.Favourite;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private StocksRepo stocksRepo;
    private LiveData<List<Favourite>> favouriteLiveData;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        stocksRepo = new StocksRepo(application);

        favouriteLiveData = stocksRepo.getFavourite();
    }
    // TODO: Implement the ViewModel

    public LiveData<List<Favourite>> getFavourite () {
        return favouriteLiveData;
    }
}