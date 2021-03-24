package com.example.yandextsk2.data.network.websocket;

import java.util.List;

public class ParseWebSocket {
    List<Data> data;
    String type;
    public class Data {
        List<Integer> c;
        float p;
        String s;
        long  t;
        int v;

        public Data(List<Integer> c,float p,String s, int t, int v) {
            this.c = c;
            this.p = p;
            this.s = s;
            this.t = t;
            this.v = v;
        }

        public List<Integer> getC() {
            return c;
        }

        public float getP() {
            return p;
        }

        public long getT() {
            return t;
        }

        public int getV() {
            return v;
        }

        public String getS() {
            return s;
        }
    }

    public List<Data> getData() {
        return data;
    }
}
