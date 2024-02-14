package com.iung.fpv20.physics;

import net.minecraft.util.math.Vec3d;
//import org.joml.Quaternionf;
import com.iung.fpv20.utils.Quaternionf;



public interface Drone {
    void update_pose(Quaternionf new_pos);

    Quaternionf get_pose();

    void update_physics(float throttle, float dt);

    Vec3d get_acceleration();

    Vec3d get_speed();

    void re_init();

    void set_speed(Vec3d v);
}
