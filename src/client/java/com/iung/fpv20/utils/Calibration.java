package com.iung.fpv20.utils;

import com.iung.fpv20.Fpv20;

public class Calibration {
    public float min;
    public float max;
    public float mid;
    //    public RateMapper rate;
    public float rate_a;
    public float rate_b;
    public CalibrateMethod calibrateMethod;

    public boolean reversed = false;


    //    public Calibration() {
//    }
    public Calibration() {
        this.min = -1;
        this.max = 1;
        this.mid = 0;
        this.rate_a = 0.3f;
        this.rate_b = 0.5f;
        Fpv20.LOGGER.info("?????");
        this.calibrateMethod = CalibrateMethod.MaxMidMin;
    }

//    public Calibration(float min, float max, float mid, RateMapper rate, CalibrateMethod calibrateMethod) {
//        this.min = Math.min(max, min);
//        this.max = Math.max(max, min);
//        if (this.max > mid && this.min < mid) {
//            this.mid = mid;
//        } else {
//            this.mid = (this.max + this.min) / 2;
//        }
//        this.rate = rate;
//        this.calibrateMethod = calibrateMethod;
//    }

    public Calibration(float min, float max, float mid, float rate_a, float rate_b, CalibrateMethod calibrateMethod) {
        this.min = min;
        this.max = max;
        this.mid = mid;
        this.rate_a = rate_a;
        this.rate_b = rate_b;
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
        if (this.reversed) {
            if (this.calibrateMethod == CalibrateMethod.MaxMin) {
                value = 1 - value;
            } else {
                value = -value;
            }
        }

        return this.rate_map(value);
    }

    public float rate_map(float v) {
        return LocalMath.bfRate(v, 1f, rate_a, rate_b) / LocalMath.bfRate(1f, 1f, rate_a, rate_b);
    }

    public float map_no_rate(float value) {
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
        if (this.reversed) {
            if (this.calibrateMethod == CalibrateMethod.MaxMin) {
                value = 1 - value;
            } else {
                value = -value;
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
