package com.example.yandextsk2.ui.recyclerViews;

public class StockItem {
    private int mIcon;
    private String mTicker;
    private String mFullName;
    private String mCurrentPrice;
    private String mDeltaPrice;

    private boolean isFavorite;

    public StockItem(int icon, String mTicker, String mFullName, String mCurrentPrice, String mDeltaPrice, boolean isFavourite) {
        this.mIcon = icon;
        this.mTicker = mTicker;
        this.mFullName = mFullName;
        this.mCurrentPrice = mCurrentPrice;
        this.mDeltaPrice = mDeltaPrice;
        this.isFavorite = isFavourite;
    }

    public String getmTicker() {
        return mTicker;
    }

    public String getmFullName() {
        return mFullName;
    }

    public String getmCurrentPrice() {
        return mCurrentPrice;
    }

    public String getmDeltaPrice() {
        return mDeltaPrice;
    }

    public int getmIcon() {
        return mIcon;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void changeCurPrice(String curPrice) {
        this.mCurrentPrice = curPrice;
    }

    public void changeDeltaPrice(String deltaPrice) {
        this.mDeltaPrice = deltaPrice;
    }

    public void changeIsFavourite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
