package com.example.yandextsk2.ui.recyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yandextsk2.R;

import java.util.ArrayList;


public class StocksRecyclerViewAdapter extends RecyclerView.Adapter<StocksRecyclerViewAdapter.StocksViewHolder> {

    private ArrayList<StockItem> stockItemList;

    public StocksRecyclerViewAdapter(ArrayList<StockItem> stockItemList) {
        this.stockItemList = stockItemList;
    }

    @NonNull
    @Override
    public StocksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stocks_card, parent, false);
        StocksViewHolder svh = new StocksViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StocksViewHolder holder, int position) {
        StockItem current  = stockItemList.get(position);

//        holder.icon.setImageResource(current.);
        holder.ticker.setText(current.getmTicker());
        holder.fullName.setText(current.getmFullName());
        holder.currentPrice.setText(current.getmCurrentPrice());
        holder.deltaPrice.setText(current.getmDeltaPrice());

    }

    @Override
    public int getItemCount() {
        return stockItemList.size();
    }

    public class StocksViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView ticker;
        private TextView fullName;
        private TextView currentPrice;
        private  TextView deltaPrice;
        public StocksViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            ticker = itemView.findViewById(R.id.ticker);
            fullName = itemView.findViewById(R.id.fullName);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            deltaPrice= itemView.findViewById(R.id.deltaPrice);
        }
    }
}
