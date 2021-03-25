package com.example.yandextsk2.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int logo;
    public String ticker;
    public String companyName;
    public String currentPrice;
    public String deltaPrice;
    public float lastPrice;

    public String procent;

    public Favourite(int logo, String ticker, String companyName, String currentPrice, String deltaPrice, float lastPrice) {
        this.logo = logo;
        this.ticker = ticker;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.deltaPrice = deltaPrice;
        this.lastPrice = lastPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public String getDeltaPrice() {
        return deltaPrice;
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public int getLogo() {
        return logo;
    }
}
