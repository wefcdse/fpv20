package com.iung.fpv20.physics;

import com.iung.fpv20.utils.Quaternionf;
import net.minecraft.util.math.Vec3d;
//import org.joml.Quaternionf;
//import org.joml.Vector3f;
import com.iung.fpv20.utils.Vector3f;

//import org.joml.Vector3fc;

import static com.iung.fpv20.utils.LocalMath.DEG_TO_RAD;
import static com.iung.fpv20.utils.LocalMath.RAD_TO_DEG;

public class PhysicsCore {

    public static Quaternionf rotate_from_local_yaw_pitch_roll(
            Quaternionf dist,
            float yaw, float pitch, float roll,
            float yaw_a_deg_s, float pitch_a_deg_s, float roll_a_deg_s,
            float dt
    ) {
        float dy_rad = yaw * yaw_a_deg_s * dt * DEG_TO_RAD;
        float dp_rad = pitch * pitch_a_deg_s * dt * DEG_TO_RAD;
        float dr_rad = roll * roll_a_deg_s * dt * DEG_TO_RAD;


        dist.rotateLocalZ(dr_rad);
        dist.rotateLocalX(dp_rad);
        dist.rotateLocalY(dy_rad);

        return dist;
    }

    public static Quaternionf from_ypr_deg(float yaw_deg, float pitch_deg, float roll_deg) {
        Quaternionf q = new Quaternionf();
        q.rotateZ(roll_deg * DEG_TO_RAD);
        q.rotateX(pitch_deg * DEG_TO_RAD);
        q.rotateY((yaw_deg + 180.0F) * DEG_TO_RAD);
        return q;
    }

    public static Vector3f from_quaternion_to_ypr_deg(Quaternionf q) {
        Vector3f x_y_z = q.getEulerAnglesZXY(new Vector3f());
        return new Vector3f(x_y_z.y * RAD_TO_DEG + 180, x_y_z.x * RAD_TO_DEG, x_y_z.z * RAD_TO_DEG);
    }
}
