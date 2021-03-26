package com.example.yandextsk2.ui.favourite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.Favourite;
import com.example.yandextsk2.data.network.websocket.WebSocket;
import com.example.yandextsk2.ui.recyclerViews.FavouriteRecyclerViewAdapter;
import com.example.yandextsk2.ui.recyclerViews.StockItem;
import com.example.yandextsk2.ui.recyclerViews.StocksRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;

    private RecyclerView recyclerViewFav;
    private RecyclerView.LayoutManager layoutManager;
    private FavouriteRecyclerViewAdapter favAdapter;

    WebSocket webSocket;

    private boolean firstStart = true;
    private boolean alreadyOpened = false;

    private List<StockItem> favouriteList;

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_fragment, container, false);
        recyclerViewFav = view.findViewById(R.id.recyclerViewFav);
        return view;
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

                //перерисовывать ресайклер вью каждый раз когда фрагмент
                //становится видимым
                if (firstStart) {
                    favouriteList = new ArrayList<>();
                    for (Favourite favourite : favourites) favouriteList.add(new StockItem(favourite.getLogo(), favourite.getTicker(),
                            favourite.getCompanyName(), favourite.getCurrentPrice(), favourite.getDeltaPrice(), true));

                    recyclerViewFav.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                    favAdapter = new FavouriteRecyclerViewAdapter(favouriteList);
                    recyclerViewFav.setLayoutManager(layoutManager);
                    recyclerViewFav.setAdapter(favAdapter);

                    firstStart = false;

                    webSocket = new WebSocket(mViewModel, favouriteList);
                    webSocket.initWebSocket();
                }
                for (int i = 0; i < favourites.size(); i++) {
                    String curPrice = favourites.get(i).getCurrentPrice();
                    float lastPrice = favourites.get(i).getLastPrice();

                    //обновляется текущая цена и разница
                    favouriteList.get(i).changeCurPrice(curPrice);
                    favouriteList.get(i).changeDeltaPrice(String.valueOf(Float.valueOf(curPrice) - lastPrice));

                    favAdapter.notifyItemChanged(i);

                }

                favAdapter.setOnItemClickListener(new FavouriteRecyclerViewAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(int position) {

                    }

                    @Override
                    public void onStarClick(int position) {
                        //при нажатии на звездочку удалить элемент из списка
                        //и изменить список
                        mViewModel.updateIsFavourite(false, favourites.get(position).getTicker());
                        mViewModel.delete(favourites.get(position));

                        favouriteList.remove(position);
                        favAdapter.notifyItemRemoved(position);
                    }
                });
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
//        webSocket.closeWebSocket();
    }


    @Override
    public void onResume() {
        super.onResume();
        //открывается вебсокет
        Log.d("websocket Fav", "resume");
//        webSocket.initWebSocket();
    }

    @Override
    public void onPause() {
        //закрывается вебсокет
        super.onPause();
        Log.d("Websocket Fav", "pause");
        if (webSocket !=null) webSocket.closeWebSocket();
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        //при переключении закрывается вебсокет
        Log.d("first start", String.valueOf(firstStart));
        if (visible && isResumed()) {
            Log.d("visible Fav", "visible");
            super.onResume();
            if (webSocket!=null) webSocket.initWebSocket();
        } else {
            //при переключении фрагментов открывается веб сокет
            //и переприсваивается перемная для последющей отрисовки
            Log.d("unvisible Fav", "unvisible");
            if (!firstStart) {
                firstStart = true;
                Log.d("websoced Fav", "close");
                webSocket.closeWebSocket();
            }
        }
    }
}