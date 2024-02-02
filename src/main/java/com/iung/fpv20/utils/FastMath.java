package com.iung.fpv20.utils;

public class FastMath {
    public static float abs(float fValue) {
        if (fValue < 0) {
            return -fValue;
        }
        return fValue;
    }

    public static float pow(float fBase, int fExponent) {
        return (float) java.lang.Math.pow(fBase, fExponent);
    }

    public static int clamp(int input, int min, int max) {
        return (input < min) ? min : java.lang.Math.min(input, max);
    }

    public static float clamp(float input, float min, float max) {
        return (input < min) ? min : java.lang.Math.min(input, max);
    }


}
