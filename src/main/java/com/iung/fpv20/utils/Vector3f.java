package com.iung.fpv20.utils;

import org.joml.Math;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

public class Vector3f {
    /**
     * The x component of the vector.
     */
    public float x;
    /**
     * The y component of the vector.
     */
    public float y;
    /**
     * The z component of the vector.
     */
    public float z;

    /**
     * Create a new {@link org.joml.Vector3f} of <code>(0, 0, 0)</code>.
     */
    public Vector3f() {
    }

    /**
     * Create a new {@link org.joml.Vector3f} and initialize all three components with the given value.
     *
     * @param d the value of all three components
     */
    public Vector3f(float d) {
        this.x = d;
        this.y = d;
        this.z = d;
    }

    /**
     * Create a new {@link org.joml.Vector3f} with the given component values.
     *
     * @param x the value of x
     * @param y the value of y
     * @param z the value of z
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f rotate(Quaternionf quat) {
        return quat.transform(this, this);
    }

    public float length() {
        return Math.sqrt(Math.fma(x, x, Math.fma(y, y, z * z)));
    }

    public Vector3f add(Vector3f v) {
        this.x = this.x + v.x;
        this.y = this.y + v.y;
        this.z = this.z + v.z;
        return this;
    }

    public Vector3f div(float scalar) {
        float inv = 1.0f / scalar;
        this.x = this.x * inv;
        this.y = this.y * inv;
        this.z = this.z * inv;
        return this;
    }

    public Vector3f div(float scalar, Vector3f dest) {
        float inv = 1.0f / scalar;
        dest.x = this.x * inv;
        dest.y = this.y * inv;
        dest.z = this.z * inv;
        return dest;
    }

    public Vector3f mul(float scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
        this.z = this.z * scalar;
        return this;
    }

    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3f(org.joml.Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public org.joml.Vector3f to_joml() {
        return new org.joml.Vector3f(this.x, this.y, this.z);
    }

    public Vector3f normalize() {
        float scalar = Math.invsqrt(Math.fma(x, x, Math.fma(y, y, z * z)));
        this.x = this.x * scalar;
        this.y = this.y * scalar;
        this.z = this.z * scalar;
        return this;
    }
    public Vector3f zero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }
}
