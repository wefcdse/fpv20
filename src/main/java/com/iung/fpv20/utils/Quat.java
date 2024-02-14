package com.iung.fpv20.utils;


import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

public class Quat {
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


    public Quat() {
        this.w = 1.0f;
    }

    public Quat conjugate(Quat dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = w;
        return dest;
    }

    public Quat conjugate() {
        return conjugate(this);
    }

    public Quat set(Quat q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }
    public Quat set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quat(Quat source) {
        set(source);
    }

    public org.joml.Quaternionf to_joml_quat() {
        return new org.joml.Quaternionf(x, y, z, w);
    }


    public Quat rotateLocalZ(float angle) {
        return rotateLocalZ(angle, this);
    }

    public Quat rotateLocalZ(float angle, Quat dest) {
        float hangle = angle * 0.5f;
        float s = Math.sin(hangle);
        float c = Math.cosFromSin(s, hangle);
        dest.set(c * x - s * y,
                c * y + s * x,
                c * z + s * w,
                c * w - s * z);
        return dest;
    }

    public Quat rotateLocalX(float angle) {
        return rotateLocalX(angle, this);
    }

    public Quat rotateLocalX(float angle, Quat dest) {
        float hangle = angle * 0.5f;
        float s = Math.sin(hangle);
        float c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * w,
                c * y - s * z,
                c * z + s * y,
                c * w - s * x);
        return dest;
    }

    public Quat rotateLocalY(float angle) {
        return rotateLocalY(angle, this);
    }

    public Quat rotateLocalY(float angle,Quat dest) {
        float hangle = angle * 0.5f;
        float s = Math.sin(hangle);
        float c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * z,
                c * y + s * w,
                c * z - s * x,
                c * w - s * y);
        return dest;
    }

    public Quat rotateX(float angle) {
        return rotateX(angle, this);
    }

    public Quat rotateX(float angle, Quat dest) {
        float sin = Math.sin(angle * 0.5f);
        float cos = Math.cosFromSin(sin, angle * 0.5f);
        return dest.set(w * sin + x * cos,
                y * cos + z * sin,
                z * cos - y * sin,
                w * cos - x * sin);
    }

    public Quat rotateY(float angle) {
        return rotateY(angle, this);
    }

    public Quat rotateY(float angle, Quat dest) {
        float sin = Math.sin(angle * 0.5f);
        float cos = Math.cosFromSin(sin, angle * 0.5f);
        return dest.set(x * cos - z * sin,
                w * sin + y * cos,
                x * sin + z * cos,
                w * cos - y * sin);
    }

    public Quat rotateZ(float angle) {
        return rotateZ(angle, this);
    }

    public Quat rotateZ(float angle, Quat dest) {
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

}
