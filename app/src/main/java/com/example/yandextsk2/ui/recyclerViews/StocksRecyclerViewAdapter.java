package com.example.yandextsk2.ui.recyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.Base;

import java.util.ArrayList;
import java.util.List;


public class StocksRecyclerViewAdapter extends RecyclerView.Adapter<StocksRecyclerViewAdapter.StocksViewHolder> {

    private List<Base> baseItemList;

    public StocksRecyclerViewAdapter(List<Base> baseItemList) {
        this.baseItemList = baseItemList;
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
        Base current  = baseItemList.get(position);

        holder.icon.setImageResource(current.getLogo());
        holder.ticker.setText(current.getTicker());
        holder.fullName.setText(current.getCompanyName());
        holder.currentPrice.setText(current.getCurrentPrice());
        holder.deltaPrice.setText(current.getDeltaPrice());

    }

    @Override
    public int getItemCount() {
        return baseItemList.size();
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
