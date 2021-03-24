package com.example.yandextsk2.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Base {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int logo;
    public String ticker;
    public String companyName;
    public String currentPrice;
    public String deltaPrice;

    public Base(int logo, String ticker, String companyName, String currentPrice, String deltaPrice) {
        this.logo = logo;
        this.ticker = ticker;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.deltaPrice = deltaPrice;
    }

    public int getId() {
        return id;
    }

    public int getLogo() {
        return logo;
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

    public void chageCurPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }
}
