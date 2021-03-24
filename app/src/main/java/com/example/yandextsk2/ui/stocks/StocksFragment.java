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

    private List<Base>  baseList= new ArrayList<>();

    private RecyclerView recyclerViewStocks;
    private RecyclerView.LayoutManager layoutManager;
    private StocksRecyclerViewAdapter stocksAdapter;

    private WebSocket webSocket;

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
                    mViewModel.insert(new Base(R.drawable.ma, "MA", "Mastercard", "0", "0"));
                } else {
//                    baseList = bases;
//                    baseList.get()
//                    Log.d("size", String.valueOf(bases.size()));

                    recyclerViewStocks = getView().findViewById(R.id.recyclerStocks);
                    recyclerViewStocks.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                    stocksAdapter = new StocksRecyclerViewAdapter(bases);
                    recyclerViewStocks.setLayoutManager(layoutManager);
                    recyclerViewStocks.setAdapter(stocksAdapter);
                }
            }
        });
        webSocket = new WebSocket(mViewModel);
        build();
//        new WebSocket();
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


    private void build() {

        recyclerViewStocks = getView().findViewById(R.id.recyclerStocks);
        recyclerViewStocks.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        stocksAdapter = new StocksRecyclerViewAdapter(baseList);
        recyclerViewStocks.setLayoutManager(layoutManager);
        recyclerViewStocks.setAdapter(stocksAdapter);
    }

    public void test () {
        Gson g = new Gson();
        String mes = "{\"data\":[{\"c\":[\"1\",\"12\"],\"p\":633.18,\"s\":\"TSLA\",\"t\":1616614408490,\"v\":1}], \"type\":\"trade\"}";
        ParseWebSocket parseWebSocket = g.fromJson(mes, ParseWebSocket.class);
        Log.d("test ", parseWebSocket.getData().get(0).getS());
    }

}