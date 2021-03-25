package com.example.yandextsk2.ui.recyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.Favourite;

import java.util.List;

public class FavouriteRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteRecyclerViewAdapter.FavouriteViewHolder> {

    List<Favourite> favouriteList;

    public FavouriteRecyclerViewAdapter(List<Favourite> favouriteList) {
        this.favouriteList = favouriteList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stocks_card, parent, false);
        FavouriteRecyclerViewAdapter.FavouriteViewHolder fvh = new FavouriteRecyclerViewAdapter.FavouriteViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        Favourite current = favouriteList.get(position);

        holder.icon.setImageResource(current.getLogo());
        holder.ticker.setText(current.getTicker());
        holder.fullName.setText(current.getCompanyName());
        holder.currentPrice.setText(current.getCurrentPrice());
        holder.deltaPrice.setText(current.getDeltaPrice());
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {

        private CardView itemCard;
        private ImageView icon;
        private TextView ticker;
        private TextView fullName;
        private TextView currentPrice;
        private TextView deltaPrice;
        private ImageView star;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.itemCard);
            icon = itemView.findViewById(R.id.icon);
            ticker = itemView.findViewById(R.id.ticker);
            fullName = itemView.findViewById(R.id.fullName);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            deltaPrice = itemView.findViewById(R.id.deltaPrice);
            star = itemView.findViewById(R.id.star);
        }
    }
}
