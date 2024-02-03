package com.iung.fpv20.flying;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.input.Controller;
import com.iung.fpv20.mixin_utils.IsFlying;
import com.iung.fpv20.network.DroneFlyPacket;
import com.iung.fpv20.physics.DefaultDrone;
import com.iung.fpv20.physics.Drone;
import com.iung.fpv20.physics.PhysicsCore;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static com.iung.fpv20.utils.LocalMath.DEG_TO_RAD;

public class GlobalFlying {
    public static GlobalFlying G = new GlobalFlying(0, 90);
    private float lastCamRoll;
    private float camRoll;

    private Quaternionf lastDroneRotation;
    private Quaternionf droneRotation;

    private float last_update_time;
    /**
     * deg/s
     */
    private float angular_speed;

    private boolean last_tick_flying;

    private float cam_angel_deg;
    private Drone drone;

    private Vec3d last_pos;

    public static float time_now_float() {
        return (float) ((double) System.nanoTime() / 1000_000_000.d);
    }

    public GlobalFlying(int camRoll, float angular_speed) {
        this.lastCamRoll = camRoll;
        this.camRoll = camRoll;
        this.angular_speed = angular_speed;
        this.last_update_time = time_now_float();
        this.last_tick_flying = false;
        this.drone = new DefaultDrone();
        this.droneRotation = new Quaternionf();
        this.lastDroneRotation = new Quaternionf();
        this.cam_angel_deg = 40;
        this.last_pos = new Vec3d(0, 0, 0);
    }

    public static void setFlying(boolean if_fly) {
        IsFlying p = (IsFlying) MinecraftClient.getInstance().player;
        if (p != null) {

            if (ClientPlayNetworking.canSend(DroneFlyPacket.TYPE)) {
                ClientPlayNetworking.send(new DroneFlyPacket(if_fly));
            }
            p.set_is_flying(if_fly);
        }
    }

    public static boolean getFlying() {
        IsFlying p = (IsFlying) MinecraftClient.getInstance().player;
        if (p != null) {
            return p.get_is_flying();
        } else {
            return false;
        }
    }

    public Quaternionf cacl_cam_rotation() {
        Quaternionf new_r = new Quaternionf(this.droneRotation);
        new_r.rotateLocalX(-this.cam_angel_deg * DEG_TO_RAD);
        return new_r;
    }

    public Quaternionf cacl_cam_rotation_last() {
        Quaternionf new_r = new Quaternionf(this.lastDroneRotation);
        new_r.rotateLocalX(-this.cam_angel_deg * DEG_TO_RAD);
        return new_r;
    }

    public void handle_flying(MinecraftClient client) {
        Fpv20.LOGGER.info("handle_flying");
        float now_time = handle_flying_inner(client);
        this.last_tick_flying = getFlying();
        this.last_update_time = now_time;
    }

    public void set_cam_roll(float roll) {
        this.lastCamRoll = this.camRoll;
        this.camRoll = roll;
    }

    public void set_drone_rotation(Quaternionf camPos) {
        this.lastDroneRotation = this.droneRotation;
        this.droneRotation = new Quaternionf(camPos);
    }

    public float handle_flying_inner(MinecraftClient client) {
        float now_time = time_now_float();

        float dt = now_time - this.last_update_time;

        if (!getFlying()) {
            return now_time;
        }

        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        if (p == null) {
            return now_time;
        }


        float roll = this.camRoll;
        float yaw = p.getYaw();
        float pitch = p.getPitch();

        if (this.last_tick_flying != getFlying()) {
            drone.re_init();
            drone.update_pose(PhysicsCore.from_ypr_deg(yaw, pitch, roll));
        }

//        Quaternionf q = PhysicsCore.from_ypr_deg(yaw, pitch, roll);
        Quaternionf q = drone.get_pose();

        Controller controller = Fpv20Client.controller;
        if (controller == null) {
            return now_time;
        }

        float input_t = controller.get_calibrated_value("t");
        float input_y = controller.get_calibrated_value("y");
        float input_p = controller.get_calibrated_value("p");
        float input_r = controller.get_calibrated_value("r");


        PhysicsCore.rotate_from_local_yaw_pitch_roll(q, input_y, input_p, input_r,
                300, 300, 300,
                dt
        );
        drone.update_pose(q);
        this.set_drone_rotation(q);


//        Vec3d a = drone.get_acceleration().multiply(dt);

//        p.setVelocity(p.getVelocity().add(a));
//        p.isc

        // process hit
        Vec3d v = drone.get_speed();
        Vector3f vd = new Vector3f((float) v.x, (float) v.y, (float) v.z);
//        Vec3d v0 = p.getPos();
        Vec3d pos = p.getPos();
        Vec3d v0 = pos.subtract(last_pos).multiply(1 / dt);
        Fpv20.LOGGER.info("v0 {}", v0);
        last_pos = pos;
        float aaa = 0.5f;
        boolean to_set_x = false;
        boolean to_set_y = false;
        boolean to_set_z = false;


        if (Math.abs(v0.x) < 0.0001) {
            vd.x = 0;
            to_set_y = true;
            to_set_z = true;
        }
        if (Math.abs(v0.y) < 0.0001) {
            vd.y = 0;
            to_set_x = true;
            to_set_z = true;
        }
        if (Math.abs(v0.z) < 0.0001) {
            vd.z = 0;
            to_set_y = true;
            to_set_x = true;
        }

        if (to_set_x) {
            float d = dt * aaa;
            if (vd.x < -d) {
                vd.x += d;
            } else if (vd.x > d) {
                vd.x -= d;
            } else {
                vd.x = 0;
            }
        }
        if (to_set_y) {
            float d = dt * aaa;
            if (vd.y < -d) {
                vd.y += d;
            } else if (vd.y > d) {
                vd.y -= d;
            } else {
                vd.y = 0;
            }
        }
        if (to_set_z) {
            float d = dt * aaa;
            if (vd.z < -d) {
                vd.z += d;
            } else if (vd.z > d) {
                vd.z -= d;
            } else {
                vd.z = 0;
            }
        }

        drone.set_speed(new Vec3d(vd));


        drone.update_physics(input_t, dt);


//        Fpv20.LOGGER.info("{}", dt);
        Vec3d v1 = drone.get_speed();
        p.setVelocity(v1);

//        Vector3f vd = new Vector3f((float) v.x, (float) v.y, (float) v.z);
//        Vec3d v0 = p.getVelocity();
//        if (Math.abs(v0.x) < 0.0001) {
//            vd.x = 0;
//        }
//        if (Math.abs(v0.y) < 0.0001) {
//            vd.y = 0;
//        }
//        if (Math.abs(v0.z) < 0.0001) {
//            vd.z = 0;
//        }
//        drone.set_speed(new Vec3d(vd));


//        Fpv20.LOGGER.info("v0 {}", p.getVelocity());
//        p.setVelocity(drone.get_speed());
        Fpv20.LOGGER.info("v1 {}", p.getVelocity());


        Vector3f new_ypr = PhysicsCore.from_quaternion_to_ypr_deg(this.cacl_cam_rotation());
        p.setYaw(new_ypr.x);
        p.setPitch(new_ypr.y);
        this.set_cam_roll(new_ypr.z);


        return now_time;
    }
}
