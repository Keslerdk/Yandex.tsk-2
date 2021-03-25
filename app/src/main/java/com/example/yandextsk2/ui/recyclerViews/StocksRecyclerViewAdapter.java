package com.example.yandextsk2.ui.recyclerViews;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yandextsk2.R;

import java.util.List;


public class StocksRecyclerViewAdapter extends RecyclerView.Adapter<StocksRecyclerViewAdapter.StocksViewHolder> {

    private List<StockItem> baseItemList;
    private OnItemClickListener mListener;

    public StocksRecyclerViewAdapter(List<StockItem> baseItemList) {
        this.baseItemList = baseItemList;
    }

    public interface OnItemClickListener {
        void onStarClick(int position);
    }

    public void setOnStarClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public StocksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stocks_card, parent, false);
        StocksViewHolder svh = new StocksViewHolder(v, mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StocksViewHolder holder, int position) {
        StockItem current = baseItemList.get(position);


        if (position % 2 != 0) {
            holder.itemCard.setCardBackgroundColor(Color.parseColor("#F0F4F7"));
        } else {
            holder.itemCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        float curPrice = Float.parseFloat(current.getmCurrentPrice());
        float deltaPrice = Float.parseFloat(current.getmDeltaPrice());
        if (curPrice >= 1000) {
            curPrice = (float) (((int) (curPrice * 10)) / 10.0);
        } else {
            curPrice = (float) (((int) (curPrice * 100)) / 100.0);
        }

        if (Math.abs(deltaPrice) > 1) {
            deltaPrice = (float) (((int) (deltaPrice * 10)) / 10.0);
        } else {
            deltaPrice = (float) (((int) (deltaPrice * 100)) / 100.0);
        }

        holder.icon.setImageResource(current.getmIcon());
        holder.ticker.setText(current.getmTicker());
        holder.fullName.setText(current.getmFullName());
        holder.currentPrice.setText("$" + curPrice);
        if (deltaPrice < 0) {
            holder.deltaPrice.setTextColor(Color.parseColor("#B32424"));
            holder.deltaPrice.setText("-$" + Math.abs(deltaPrice));
        } else {
            holder.deltaPrice.setTextColor(Color.parseColor("#24B25D"));
            holder.deltaPrice.setText("+$" + Math.abs(deltaPrice));
        }
        if (current.isFavorite()) {
            holder.star.setImageResource(R.drawable.ic_star_yel);
        } else {
            holder.star.setImageResource(R.drawable.ic_baseline_star_24);
        }


    }

    @Override
    public int getItemCount() {
        return baseItemList.size();
    }

    public class StocksViewHolder extends RecyclerView.ViewHolder {

        private CardView itemCard;
        private ImageView icon;
        private TextView ticker;
        private TextView fullName;
        private TextView currentPrice;
        private TextView deltaPrice;
        private ImageView star;

        public StocksViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemCard = itemView.findViewById(R.id.itemCard);
            icon = itemView.findViewById(R.id.icon);
            ticker = itemView.findViewById(R.id.ticker);
            fullName = itemView.findViewById(R.id.fullName);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            deltaPrice = itemView.findViewById(R.id.deltaPrice);
            star = itemView.findViewById(R.id.star);


            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onStarClick(position);
//                            star.setColorFilter(Color.parseColor("#FFCA1C"));


                            star.setImageResource(R.drawable.star_yel);
                        }
                    }
                }
            });
        }
    }
}
