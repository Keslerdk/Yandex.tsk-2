package com.example.yandextsk2.data.network;

public class StockSymbol {
    String currency;
    String description;
    String displaySimbol;
    String figi;
    String mic;
    String symbol;
    String type;

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
