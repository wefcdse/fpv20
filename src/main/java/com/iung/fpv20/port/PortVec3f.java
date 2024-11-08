package com.iung.fpv20.port;

import net.minecraft.util.math.Vec3f;

public class PortVec3f {
    public static void setX(Vec3f dst, float x) {
        dst.set(x, dst.getY(), dst.getZ());
    }

    public static void setY(Vec3f dst, float y) {
        dst.set(dst.getX(), y, dst.getZ());
    }

    public static void setZ(Vec3f dst, float z) {
        dst.set(dst.getX(), dst.getY(), z);
    }

    public static float length(Vec3f src){
        return (float) Math.sqrt(Math.fma(src.getX(), src.getX(), Math.fma(src.getY(), src.getY(), src.getZ() * src.getZ())));
    }
}
