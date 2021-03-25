package com.example.yandextsk2.ui.favourite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.Favourite;
import com.example.yandextsk2.ui.recyclerViews.FavouriteRecyclerViewAdapter;
import com.example.yandextsk2.ui.recyclerViews.StocksRecyclerViewAdapter;

import java.util.List;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;

    private RecyclerView recyclerViewFav;
    private RecyclerView.LayoutManager layoutManager;
    private FavouriteRecyclerViewAdapter favAdapter;

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.favourite_fragment, container, false);
        recyclerViewFav = view.findViewById(R.id.recyclerViewFav);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        mViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getFavourite().observe(getViewLifecycleOwner(), new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {

//                recyclerViewFav = getView().findViewById(R.id.recyclerViewFav);
                recyclerViewFav.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getContext());
                favAdapter = new FavouriteRecyclerViewAdapter(favourites);
                recyclerViewFav.setLayoutManager(layoutManager);
                recyclerViewFav.setAdapter(favAdapter);

                favAdapter.setOnItemClickListener(new FavouriteRecyclerViewAdapter.OnItemClickListener() {
                    boolean deleted = false;
                    @Override
                    public void onItemClick(int position) {

                    }

                    @Override
                    public void onStarClick(int position) {
                        mViewModel.updateIsFavourite(false, favourites.get(position).getTicker());
                        mViewModel.delete(favourites.get(position));
                        favAdapter.notifyItemRemoved(position);
                    }
                });
            }
        });
    }

}