package com.example.yandextsk2.ui.stocks;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.Base;
import com.example.yandextsk2.data.db.entity.Favourite;
import com.example.yandextsk2.data.db.entity.StockSymbol;
import com.example.yandextsk2.data.network.ApiCall;
import com.example.yandextsk2.data.network.websocket.ParseWebSocket;
import com.example.yandextsk2.data.network.websocket.WebSocket;
import com.example.yandextsk2.ui.recyclerViews.StockItem;
import com.example.yandextsk2.ui.recyclerViews.StocksRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StocksFragment extends Fragment {

    private StocksViewModel mViewModel;

    private RecyclerView recyclerViewStocks;
    private RecyclerView.LayoutManager layoutManager;
    private StocksRecyclerViewAdapter stocksAdapter;

    private WebSocket webSocket;

    List<StockItem> stockItems;

    private boolean firstStart = true;
    private boolean isFavourite;
    private List<String> symbols = new ArrayList<>();


    public static StocksFragment newInstance() {
        return new StocksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stocks_fragment, container, false);

        symbols.add("YNDX");
        symbols.add("AAPL");
        symbols.add("GOOGL");
        symbols.add("AMZN");
        symbols.add("BAC");
        symbols.add("MSFT");
        symbols.add("TSLA");
        symbols.add("MA");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(StocksViewModel.class);
        mViewModel = ViewModelProviders.of(this).get(StocksViewModel.class);
        // TODO: Use the ViewModel
        if (firstStart) {
            for (String val : symbols) new ApiCall(mViewModel).quoteApiCall(val);
        }

        mViewModel.getBase().observe(getViewLifecycleOwner(), new Observer<List<Base>>() {
            @Override
            public void onChanged(List<Base> bases) {


                if (firstStart == true) {

                    if (bases.isEmpty() || bases == null) {
                        mViewModel.insert(new Base(R.drawable.yndx, "YNDX", "Yandex, LLC", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.aapl, "AAPL", "Apple Inc.", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.googl, "GOOGL", "Alphabet Class A", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.amzn, "AMZN", "Amazon.com", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.bac, "BAC", "Bank of America Corp", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.msft, "MSFT", "Microsoft Corporation", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.tsla, "TSLA", "Tesla Motors", "0", "0"));
                        mViewModel.insert(new Base(R.drawable.ma, "MA", "Mastercard", "0", "0"));
                    }

                    stockItems = new ArrayList<>();
                    for (Base base : bases) {
                        stockItems.add(new StockItem(base.getLogo(), base.getTicker(), base.getCompanyName(),
                                base.getCurrentPrice(), base.getDeltaPrice(), base.isFavourite()));
                    }

                    recyclerViewStocks = getView().findViewById(R.id.recyclerStocks);
                    recyclerViewStocks.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                    stocksAdapter = new StocksRecyclerViewAdapter(stockItems);
                    recyclerViewStocks.setLayoutManager(layoutManager);
                    recyclerViewStocks.setAdapter(stocksAdapter);

                    firstStart = false;
                } else {
                    for (int i = 0; i < bases.size(); i++) {
                        String curPrice = bases.get(i).getCurrentPrice();
                        float lastPrice = bases.get(i).getLastPrice();
                        stockItems.get(i).changeCurPrice(curPrice);

                        stockItems.get(i).changeDeltaPrice(String.valueOf(Float.valueOf(curPrice) - lastPrice));
//                        mViewModel.updateDeltaPrice(String.valueOf(Float.valueOf(curPrice) - lastPrice), bases.get(i).getTicker());

                        stockItems.get(i).changeIsFavourite(bases.get(i).isFavourite());

                        stocksAdapter.notifyItemChanged(i);

                    }

                    stocksAdapter.setOnStarClickListener(new StocksRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onStarClick(int position) {
                            Log.d("Fav", String.valueOf(bases.get(position).isFavourite()));
//                            if (!bases.get(position).isFavourite()) {
                            isFavourite = bases.get(position).isFavourite();
                            if (!isFavourite) {
                                isFavourite = true;
                                Log.d("on click", "CLICKED");
                                mViewModel.updateIsFavourite(true, bases.get(position).getTicker());
                                mViewModel.insert(new Favourite(bases.get(position).getLogo(), bases.get(position).getTicker(),
                                        bases.get(position).getCompanyName(), bases.get(position).getCurrentPrice(),
                                        bases.get(position).getDeltaPrice(), bases.get(position).getLastPrice()));

                            } else {
                                isFavourite = false;
                                final boolean[] deleted = {false};
                                Log.d("on click", "UNCLICKED");
                                mViewModel.getFavouriteItem(bases.get(position).getTicker()).observe(getViewLifecycleOwner(), new Observer<Favourite>() {
                                    @Override
                                    public void onChanged(Favourite favourite) {
//                                        Log.d("Favourite", String.valueOf(favourite) + 222);
                                        if (!deleted[0]) {
                                            mViewModel.delete(favourite);
                                            mViewModel.updateIsFavourite(false, bases.get(position).getTicker());
                                            deleted[0] = true;
                                        }

                                    }
                                });
                            }
                        }
                    });
                }


            }
        });


        webSocket = new WebSocket(mViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        webSocket.initWebSocket();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Websocket", "pause");
        webSocket.closeWebSocket();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Websocket", "stopped");
        webSocket.closeWebSocket();
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            Log.d("visible", "vdfe");
            super.onResume();
        }
        else  {Log.d("unvisible", "kdb"); }
    }


    /*
    private void build() {

        recyclerViewStocks = getView().findViewById(R.id.recyclerStocks);
        recyclerViewStocks.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        stocksAdapter = new StocksRecyclerViewAdapter(baseList);
        recyclerViewStocks.setLayoutManager(layoutManager);
        recyclerViewStocks.setAdapter(stocksAdapter);
    } */


}