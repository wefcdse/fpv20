package com.iung.fpv20.port;

import net.minecraft.util.math.Quaternion;

import static java.lang.Math.PI;

public class PortMath {
    static final float PI_f = (float) java.lang.Math.PI;
    static final float PI2_f = PI_f * 2.0f;
    static final float PIHalf_f = (float) (PI * 0.5);

    public static Quaternion rotateLocalX(Quaternion src, float angle, Quaternion dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = PortMath.cosFromSinInternal(s, hangle);
        dest.set(c * src.getX() + s * src.getW(),
                c * src.getY() - s * src.getZ(),
                c * src.getZ() + s * src.getY(),
                c * src.getW() - s * src.getX());
        return dest;
    }

    private static float cosFromSinInternal(float sin, float angle) {
        // sin(x)^2 + cos(x)^2 = 1
        float cos = (float) Math.sqrt(1.0f - sin * sin);
        float a = angle + PIHalf_f;
        float b = a - (int) (a / PI2_f) * PI2_f;
        if (b < 0.0)
            b = PI2_f + b;
        if (b >= PI_f)
            return -cos;
        return cos;
    }

    public static Quaternion rotateLocalZ(Quaternion src, float angle, Quaternion dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = PortMath.cosFromSinInternal(s, hangle);
        dest.set(c * src.getX() - s * src.getY(),
                c * src.getY() + s * src.getX(),
                c * src.getZ() + s * src.getW(),
                c * src.getW() - s * src.getZ());
        return dest;
    }

    public static Quaternion rotateLocalY(Quaternion src, float angle, Quaternion dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = PortMath.cosFromSinInternal(s, hangle);
        dest.set(c * src.getX() + s * src.getZ(),
                c * src.getY() + s * src.getW(),
                c * src.getZ() - s * src.getX(),
                c * src.getW() - s * src.getY());
        return dest;
    }
}
