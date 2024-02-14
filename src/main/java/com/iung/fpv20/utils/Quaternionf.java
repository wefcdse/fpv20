package com.iung.fpv20.utils;


import org.joml.Math;
import com.iung.fpv20.utils.Vector3f;
import org.joml.Vector3fc;

public class Quaternionf {
//    static public class Quaternionf extends Quat {
//        public Quaternionf() {
//        }
//
//        public Quaternionf(Quat source) {
//            super(source);
//        }
//    }

    public float x;
    public float y;
    public float z;
    public float w;


    public Quaternionf() {
        this.w = 1.0f;
    }

    public Quaternionf conjugate(Quaternionf dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = w;
        return dest;
    }

    public Quaternionf conjugate() {
        return conjugate(this);
    }

    public Quaternionf set(Quaternionf q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public Quaternionf set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaternionf(Quaternionf source) {
        set(source);
    }

    public org.joml.Quaternionf to_joml_quat() {
        return new org.joml.Quaternionf(x, y, z, w);
    }


    public Quaternionf rotateLocalZ(float angle) {
        return rotateLocalZ(angle, this);
    }

    public Quaternionf rotateLocalZ(float angle, Quaternionf dest) {
        float hangle = angle * 0.5f;
        float s = Math.sin(hangle);
        float c = Math.cosFromSin(s, hangle);
        dest.set(c * x - s * y,
                c * y + s * x,
                c * z + s * w,
                c * w - s * z);
        return dest;
    }

    public Quaternionf rotateLocalX(float angle) {
        return rotateLocalX(angle, this);
    }

    public Quaternionf rotateLocalX(float angle, Quaternionf dest) {
        float hangle = angle * 0.5f;
        float s = Math.sin(hangle);
        float c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * w,
                c * y - s * z,
                c * z + s * y,
                c * w - s * x);
        return dest;
    }

    public Quaternionf rotateLocalY(float angle) {
        return rotateLocalY(angle, this);
    }

    public Quaternionf rotateLocalY(float angle, Quaternionf dest) {
        float hangle = angle * 0.5f;
        float s = Math.sin(hangle);
        float c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * z,
                c * y + s * w,
                c * z - s * x,
                c * w - s * y);
        return dest;
    }

    public Quaternionf rotateX(float angle) {
        return rotateX(angle, this);
    }

    public Quaternionf rotateX(float angle, Quaternionf dest) {
        float sin = Math.sin(angle * 0.5f);
        float cos = Math.cosFromSin(sin, angle * 0.5f);
        return dest.set(w * sin + x * cos,
                y * cos + z * sin,
                z * cos - y * sin,
                w * cos - x * sin);
    }

    public Quaternionf rotateY(float angle) {
        return rotateY(angle, this);
    }

    public Quaternionf rotateY(float angle, Quaternionf dest) {
        float sin = Math.sin(angle * 0.5f);
        float cos = Math.cosFromSin(sin, angle * 0.5f);
        return dest.set(x * cos - z * sin,
                w * sin + y * cos,
                x * sin + z * cos,
                w * cos - y * sin);
    }

    public Quaternionf rotateZ(float angle) {
        return rotateZ(angle, this);
    }

    public Quaternionf rotateZ(float angle, Quaternionf dest) {
        float sin = Math.sin(angle * 0.5f);
        float cos = Math.cosFromSin(sin, angle * 0.5f);
        return dest.set(x * cos + y * sin,
                y * cos - x * sin,
                w * sin + z * cos,
                w * cos - z * sin);
    }

    public Vector3f getEulerAnglesZXY(Vector3f eulerAngles) {
        eulerAngles.x = Math.safeAsin(2.0f * (w * x + y * z));
        eulerAngles.y = Math.atan2(w * y - x * z, 0.5f - y * y - x * x);
        eulerAngles.z = Math.atan2(w * z - x * y, 0.5f - z * z - x * x);
        return eulerAngles;
    }


    public Vector3f transform(float x, float y, float z, Vector3f dest) {
        float xx = this.x * this.x, yy = this.y * this.y, zz = this.z * this.z, ww = this.w * this.w;
        float xy = this.x * this.y, xz = this.x * this.z, yz = this.y * this.z, xw = this.x * this.w;
        float zw = this.z * this.w, yw = this.y * this.w, k = 1 / (xx + yy + zz + ww);
        return dest.set(Math.fma((xx - yy - zz + ww) * k, x, Math.fma(2 * (xy - zw) * k, y, (2 * (xz + yw) * k) * z)),
                Math.fma(2 * (xy + zw) * k, x, Math.fma((yy - xx - zz + ww) * k, y, (2 * (yz - xw) * k) * z)),
                Math.fma(2 * (xz - yw) * k, x, Math.fma(2 * (yz + xw) * k, y, ((zz - xx - yy + ww) * k) * z)));
    }

    public Vector3f transform(Vector3f vec, Vector3f dest) {
        return transform(vec.x, vec.y, vec.z, dest);
    }
}
