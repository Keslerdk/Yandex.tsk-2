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
import android.view.View;
import android.view.ViewGroup;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.Base;
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

    public static StocksFragment newInstance() {
        return new StocksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stocks_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(StocksViewModel.class);
        mViewModel = ViewModelProviders.of(this).get(StocksViewModel.class);
        // TODO: Use the ViewModel

//        new ApiCall(mViewModel).firstApiCall();

//        mViewModel.getAllStockSymbols().observe(getViewLifecycleOwner(), new Observer<List<StockSymbol>>() {
//            @Override
//            public void onChanged(List<StockSymbol> stockSymbols) {
//                Log.d("size", String.valueOf(stockSymbols.size()));
//
////                new ApiCall(mViewModel).quoteApiCall(stockSymbols.get(0).getSymbol());
//
//                for (StockSymbol val : stockSymbols) {
////                    Log.d("all Stocks", val.getSymbol());
//                    stockList.add(new StockItem(val.getSymbol(), val.getDescription(), "45264", "245"));
//                }
//                build();
//            }
//        });

        webSocket = new WebSocket(mViewModel);
        mViewModel.getBase().observe(getViewLifecycleOwner(), new Observer<List<Base>>() {
            @Override
            public void onChanged(List<Base> bases) {
                if (bases.isEmpty() || bases == null) {
                    mViewModel.insert(new Base(R.drawable.yndx, "YNDX", "Yandex, LLC", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.aapl, "AAPL", "Apple Inc.", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.googl, "GOOGL", "Alphabet Class A", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.amzn, "AMZN", "Amazon.com", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.bac, "BAC", "Bank of America Corp", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.msft, "MSFT", "Microsoft Corporation", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.tsla, "TSLA", "Tesla Motors", "0", "0"));
                    mViewModel.insert(new Base(R.drawable.ma, "MA", "Mastercard", "0", "0"));}

                if (firstStart==true) {
                    stockItems = new ArrayList<>();
                    for (Base base : bases)  stockItems.add(new StockItem(base.getLogo(), base.getTicker(), base.getCompanyName(),
                            base.getCurrentPrice(), base.getDeltaPrice()));


                    recyclerViewStocks = getView().findViewById(R.id.recyclerStocks);
                    recyclerViewStocks.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                    stocksAdapter = new StocksRecyclerViewAdapter(stockItems);
                    recyclerViewStocks.setLayoutManager(layoutManager);
                    recyclerViewStocks.setAdapter(stocksAdapter);

                    firstStart = false;
                } else {

                    for (int i=0; i<bases.size(); i++) {
                        stockItems.get(i).changeCurPrice(bases.get(i).getCurrentPrice());
                        stocksAdapter.notifyItemChanged(i);
                    }
                }


                }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        webSocket.initWebSocket();
    }

    @Override
    public void onPause() {
        super.onPause();
        webSocket.closeWebSocket();
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