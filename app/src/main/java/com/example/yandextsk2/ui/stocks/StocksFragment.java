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
import com.example.yandextsk2.data.db.entity.StockSymbol;
import com.example.yandextsk2.data.network.ApiCall;
import com.example.yandextsk2.ui.recyclerViews.StockItem;
import com.example.yandextsk2.ui.recyclerViews.StocksRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class StocksFragment extends Fragment {

    private StocksViewModel mViewModel;

    private ArrayList<StockItem> stockList = new ArrayList<>();

    private RecyclerView recyclerViewStocks;
    private RecyclerView.LayoutManager layoutManager;
    private StocksRecyclerViewAdapter stocksAdapter;

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

        mViewModel.getAllStockSymbols().observe(getViewLifecycleOwner(), new Observer<List<StockSymbol>>() {
            @Override
            public void onChanged(List<StockSymbol> stockSymbols) {
                Log.d("size", String.valueOf(stockSymbols.size()));

                new ApiCall(mViewModel).quoteApiCall(stockSymbols.get(0).getSymbol());

                for (StockSymbol val : stockSymbols) {
//                    Log.d("all Stocks", val.getSymbol());
                    stockList.add(new StockItem(val.getSymbol(), val.getDescription(), "45264", "245"));
                }
                build();
            }
        });

    }

    private void build() {
        recyclerViewStocks = getView().findViewById(R.id.recyclerStocks);
        recyclerViewStocks.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        stocksAdapter = new StocksRecyclerViewAdapter(stockList);
        recyclerViewStocks.setLayoutManager(layoutManager);
        recyclerViewStocks.setAdapter(stocksAdapter);
    }

}