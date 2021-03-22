package com.example.yandextsk2.data.network;

public class Quote {
    float c;
    float h;
    float l;
    float o;
    float pc;
    float t;

    public Quote(float c, float h, float l, float o, float pc, float t) {
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.pc = pc;
        this.t = t;
    }

    public float getC() {
        return c;
    }

    public float getH() {
        return h;
    }

    public float getL() {
        return l;
    }

    public float getO() {
        return o;
    }

    public float getPc() {
        return pc;
    }

    public float getT() {
        return t;
    }
}
