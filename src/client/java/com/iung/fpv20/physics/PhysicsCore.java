package com.iung.fpv20.physics;

import com.iung.fpv20.port.PortMath;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.joml.Quaternion;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import static com.iung.fpv20.utils.LocalMath.DEG_TO_RAD;
import static com.iung.fpv20.utils.LocalMath.RAD_TO_DEG;

public class PhysicsCore {

    public static Quaternion rotate_from_local_yaw_pitch_roll(
            Quaternion dist,
            float yaw, float pitch, float roll,
            float yaw_a_deg_s, float pitch_a_deg_s, float roll_a_deg_s,
            float dt
    ) {
        float dy_rad = yaw * yaw_a_deg_s * dt * DEG_TO_RAD;
        float dp_rad = pitch * pitch_a_deg_s * dt * DEG_TO_RAD;
        float dr_rad = roll * roll_a_deg_s * dt * DEG_TO_RAD;


//        dist.rotateLocalZ(dr_rad);
        PortMath.rotateLocalZ(dist, dr_rad, dist);

//        dist.rotateLocalX(dp_rad);
        PortMath.rotateLocalX(dist, dp_rad, dist);

//        dist.rotateLocalY(dy_rad);
        PortMath.rotateLocalY(dist, dy_rad, dist);

        return dist;
    }

    public static Quaternion from_ypr_deg(float yaw_deg, float pitch_deg, float roll_deg) {
        Quaternion q = new Quaternion(0,0,0,false);
        q.rotateZ(roll_deg * DEG_TO_RAD);
        q.rotateX(pitch_deg * DEG_TO_RAD);
        q.rotateY((yaw_deg + 180.0F) * DEG_TO_RAD);
        return q;
    }

    public static Vec3f from_quaternion_to_ypr_deg(Quaternion q) {
        Vec3f x_y_z = q.getEulerAnglesZXY(new Vec3f());
        return new Vec3f(x_y_z.getY() * RAD_TO_DEG + 180, x_y_z.getX() * RAD_TO_DEG, x_y_z.getZ() * RAD_TO_DEG);
    }
}
