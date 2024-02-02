package com.iung.fpv20.utils;

public class RateMapper {
    public float a;
    public float b;

    public RateMapper(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public float map(float v) {
        return LocalMath.bfRate(v, 1f, a, b) / LocalMath.bfRate(1f, 1f, a, b);
    }
}
