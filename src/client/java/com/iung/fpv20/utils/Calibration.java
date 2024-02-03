package com.iung.fpv20.utils;

public class Calibration {
    public float min;
    public float max;
    public float mid;
    public RateMapper rate;
    public CalibrateMethod calibrateMethod;

    public Calibration(float min, float max, float mid, RateMapper rate) {
        this.min = min;
        this.max = max;
        this.mid = mid;
        this.rate = rate;
    }

    public Calibration() {
        this.min = -1;
        this.max = 1;
        this.mid = 0;
        this.rate = new RateMapper(0.3f, 0.5f);
        this.calibrateMethod = CalibrateMethod.MaxMidMin;
    }

    public Calibration(float min, float max, float mid, RateMapper rate, CalibrateMethod calibrateMethod) {
        this.min = Math.min(max, min);
        this.max = Math.max(max, min);
        if (this.max > mid && this.min < mid) {
            this.mid = mid;
        } else {
            this.mid = (this.max + this.min) / 2;
        }
        this.rate = rate;
        this.calibrateMethod = calibrateMethod;
    }

    public float map(float value) {
        switch (this.calibrateMethod) {
            case MaxMin -> {
                value = value - min;
                value = value / (max - min);
                value = FastMath.clamp(value, 0, 1);
            }
            case MaxMidMin -> {
                if (value < mid) {
                    value = mid - value;
                    value = value / (mid - min);
                    value = FastMath.clamp(value, 0, 1);
                    value = -value;
                } else {
                    value = value - mid;
                    value = value / (max - mid);
                    value = FastMath.clamp(value, 0, 1);
                }
            }
            case Raw -> {
                return value;
            }
        }
        return this.rate.map(value);
    }

    public float map_no_rate(float value){
        switch (this.calibrateMethod) {
            case MaxMin -> {
                value = value - min;
                value = value / (max - min);
                value = FastMath.clamp(value, 0, 1);
            }
            case MaxMidMin -> {
                if (value < mid) {
                    value = mid - value;
                    value = value / (mid - min);
                    value = FastMath.clamp(value, 0, 1);
                    value = -value;
                } else {
                    value = value - mid;
                    value = value / (max - mid);
                    value = FastMath.clamp(value, 0, 1);
                }
            }
            case Raw -> {
            }
        }
        return value;
    }


    public enum CalibrateMethod {
        MaxMin,
        MaxMidMin,
        Raw

    }
}
