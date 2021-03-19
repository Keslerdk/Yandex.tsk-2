package com.example.yandextsk2.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock_symbol")
public class StockSymbol {

    String currency;
    String description;
    String displaySimbol;
    String figi;
    String mic;
    String type;
    @PrimaryKey(autoGenerate = false)
    String symbol;

    public StockSymbol(String currency, String description, String displaySimbol, String figi,
                       String mic, String symbol, String type) {
        this.currency = currency;
        this.description = description;
        this.displaySimbol = displaySimbol;
        this.figi = figi;
        this.mic = mic;
        this.symbol = symbol;
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplaySimbol() {
        return displaySimbol;
    }

    public String getFigi() {
        return figi;
    }

    public String getMic() {
        return mic;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }
}
