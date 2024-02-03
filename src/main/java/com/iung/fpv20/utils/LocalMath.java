package com.iung.fpv20.utils;

public class LocalMath {
    public static float DEG_TO_RAD = 0.017453292F;
    public static float RAD_TO_DEG = 1 / 0.017453292F;

    public static float bfRate(
            float rcCommand,
            float rcRate,
            float superRate,
            float expo
    ) {
        float absRcCommand = FastMath.abs(rcCommand);

//        if (rcRate > 2.0f) {
//            rcRate = rcRate + (14.54f * (rcRate - 2.0f));
//        }

        if (expo != 0)
            rcCommand = rcCommand *
                    FastMath.pow(FastMath.abs(rcCommand), 3) *
                    expo + rcCommand * (1.0f - expo);

//        float angleRate = rcRate * rcCommand;
        float angleRate = rcCommand;
        if (superRate != 0) {
            float rcSuperFactor = 1.0f / (FastMath.clamp(
                    1.0f -
                            absRcCommand *
                                    (superRate),
                    0.01f,
                    1.00f
            ));
            angleRate *= rcSuperFactor;
        }

        return angleRate;
    }
}
