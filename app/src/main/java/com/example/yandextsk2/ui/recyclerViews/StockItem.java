package com.example.yandextsk2.ui.recyclerViews;

public class StockItem {
//    private int mIcon;
    private String mTicker;
    private String mFullName;
    private String mCurrentPrice;
    private String mDeltaPrice;

    public StockItem(String mTicker, String mFullName, String mCurrentPrice, String mDeltaPrice) {
        this.mTicker = mTicker;
        this.mFullName = mFullName;
        this.mCurrentPrice = mCurrentPrice;
        this.mDeltaPrice = mDeltaPrice;
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
}
