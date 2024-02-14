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
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
//import org.joml.Quaternionf;
import com.iung.fpv20.utils.Quaternionf;
import com.iung.fpv20.utils.Vector3f;
//import org.joml.Vector3f;


import static com.iung.fpv20.utils.LocalMath.DEG_TO_RAD;

public class GlobalFlying {
    public static GlobalFlying G = new GlobalFlying(0);
//    private float lastCamRoll;
//    private float camRoll;

    private Quaternionf lastDroneRotation;
    private Quaternionf droneRotation;

    private float last_update_time;
    /**
     * deg/s
     */
//    private float angular_speed;

    private boolean last_tick_flying;

    private float cam_angel_deg;
    private Drone drone;

    private Vec3d last_pos;

    private long last_tick_nano;
    private long this_tick_nano;
    private Vec3d last_tick_pos;
    private Vec3d this_tick_pos;
    private Vec3d speed;

    private void update_speed_tick(Entity player) {
        last_tick_pos = this_tick_pos;
        this_tick_pos = player.getPos();

        last_tick_nano = this_tick_nano;
        this_tick_nano = System.nanoTime();

        speed = this_tick_pos.subtract(last_tick_pos)
                .multiply((this_tick_nano - last_tick_nano) / 1000_000_000.0);
    }

    private Vec3d get_speed() {
        return this.speed;
    }


    private static float time_now_float() {
        return (float) ((double) System.nanoTime() / 1000_000_000.d);
    }

    public GlobalFlying(int camRoll) {
//        this.lastCamRoll = camRoll;
//        this.camRoll = camRoll;
//        this.angular_speed = angular_speed;
        this.last_update_time = time_now_float();
        this.last_tick_flying = false;
        this.drone = new DefaultDrone();
        this.droneRotation = new Quaternionf();
        this.lastDroneRotation = new Quaternionf();
        this.cam_angel_deg = 30;
        this.last_pos = new Vec3d(0, 0, 0);
        this.last_tick_pos = new Vec3d(0, 0, 0);
        this.last_tick_nano = System.nanoTime();
        this.this_tick_pos = new Vec3d(0, 0, 0);
        this.this_tick_nano = System.nanoTime() + 1;
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

    public void update_tick_start(Entity player) {
        this.update_speed_tick(player);
    }

    public Quaternionf cacl_cam_rotation() {
        Quaternionf new_r = new Quaternionf(this.droneRotation);
        new_r.rotateLocalX(-this.cam_angel_deg * DEG_TO_RAD);
        return new_r;
    }

    //    @Deprecated
    public Quaternionf cacl_cam_rotation_last() {
        Quaternionf new_r = new Quaternionf(this.lastDroneRotation);
        new_r.rotateLocalX(-this.cam_angel_deg * DEG_TO_RAD);
        return new_r;
    }

    public void handle_flying(MinecraftClient client) {

        float now_time = time_now_float();

        float dt = now_time - this.last_update_time;

        this.handle_flying_inner(client, dt);

        this.last_tick_flying = getFlying();
        this.last_update_time = now_time;
    }

    private void handle_flying(MinecraftClient client, float tick_delta) {

        float now_time = time_now_float();

        float dt = (float) (tick_delta * 0.05);

        this.handle_flying_inner(client, dt);

        this.last_tick_flying = getFlying();
        this.last_update_time = now_time;
    }

//    private void set_cam_roll(float roll) {
//        this.lastCamRoll = this.camRoll;
//        this.camRoll = roll;
//    }

    private void set_drone_rotation(Quaternionf camPos) {
        this.lastDroneRotation = this.droneRotation;
        this.droneRotation = new Quaternionf(camPos);
    }


    private void handle_flying_inner(MinecraftClient client, float dt) {

//        float now_time = time_now_float();
//
//        float dt = now_time - this.last_update_time;

        Fpv20.LOGGER.debug("handle_flying:dt {}", dt);

        if (!getFlying()) {
            return;
        }

        ClientPlayerEntity p = client.player;
        if (p == null) {
            return;
        }


//        float roll = this.camRoll;

        // start flying, init the drone
        if (this.last_tick_flying != getFlying()) {
            float yaw = p.getYaw();
            float pitch = p.getPitch();
            drone.re_init();
            drone.update_pose(PhysicsCore.from_ypr_deg(yaw, pitch, 0));
        }

        Quaternionf q = drone.get_pose();

        Controller controller = Fpv20Client.controller;
        if (controller == null) {
            return;
        }

        float input_t = controller.get_value_by_name("t");
        float input_y = controller.get_value_by_name("y");
        float input_p = controller.get_value_by_name("p");
        float input_r = controller.get_value_by_name("r");


        PhysicsCore.rotate_from_local_yaw_pitch_roll(q, input_y, input_p, input_r,
                300, 300, 300,
                dt
        );
        drone.update_pose(q);
        this.set_drone_rotation(q);


        // process hit
        Vec3d v = drone.get_speed();
        Vector3f vd = new Vector3f((float) v.x, (float) v.y, (float) v.z);
        Vec3d pos = p.getPos();
//        Vec3d v0 = pos.subtract(last_pos).multiply(1 / dt);
        Vec3d v0 = this.get_speed();
        Fpv20.LOGGER.debug("process hit:v0 {}", v0);
        last_pos = pos;
        float aaa = 0.5f;
        boolean to_set_x = false;
        boolean to_set_y = false;
        boolean to_set_z = false;


        if (Math.abs(v0.x) < 0.0001) {
            Fpv20.LOGGER.debug("process hit:x");
            vd.x = 0;
            to_set_y = true;
            to_set_z = true;
        }
        if (Math.abs(v0.y) < 0.0001) {
            Fpv20.LOGGER.debug("process hit:y");
            vd.y = 0;
            to_set_x = true;
            to_set_z = true;
        }
        if (Math.abs(v0.z) < 0.0001) {
            Fpv20.LOGGER.debug("process hit:z");
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

        drone.set_speed(new Vec3d(vd.to_joml()));
        // // process hit


        drone.update_physics(input_t, dt);


//        Fpv20.LOGGER.debug("{}", dt);
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


//        Fpv20.LOGGER.debug("v0 {}", p.getVelocity());
//        p.setVelocity(drone.get_speed());
        Fpv20.LOGGER.debug("after update phy:v1 {}", p.getVelocity());


        Vector3f new_ypr = PhysicsCore.from_quaternion_to_ypr_deg(this.cacl_cam_rotation());
        p.setYaw(new_ypr.x);
        p.setPitch(new_ypr.y);
//        this.set_cam_roll(new_ypr.z);


        return;
    }

    public void handle_flying_phy(ClientPlayerEntity player, float dt) {


        if (!getFlying()) {
            return;
        }

        ClientPlayerEntity p = player;
        if (p == null) {
            return;
        }


        Controller controller = Fpv20Client.controller;
        if (controller == null) {
            return;
        }
        float input_t = controller.get_value_by_name("t");

        // process hit
        Vec3d v = drone.get_speed();
        Vector3f vd = new Vector3f((float) v.x, (float) v.y, (float) v.z);
        Vec3d pos = p.getPos();

        Vec3d v0 = this.get_speed();
        Fpv20.LOGGER.debug("process hit:v0 {}", v0);
        last_pos = pos;
        float aaa = 0.5f;
        boolean hit = false;

        final float ZERO = 0.000001f;

        if (Math.abs(v0.x) < ZERO) {
//            hit = true;
            Fpv20.LOGGER.debug("process hit:x");
            vd.x = 0;
        }
        if (Math.abs(v0.y) < ZERO) {
            hit = true;
            Fpv20.LOGGER.debug("process hit:y");
            vd.y = 0;
        }
        if (Math.abs(v0.z) < ZERO) {
//            hit = true;
            Fpv20.LOGGER.debug("process hit:z");
            vd.z = 0;
        }
        if (hit) {
            if (vd.length() > aaa * dt && vd.length() > 0.0000001) {
                Vector3f vd1 = new Vector3f(vd).normalize().mul(-1f * aaa * dt);
                vd.add(vd1);
            } else {
                vd.zero();
            }
        }


        drone.set_speed(new Vec3d(vd.to_joml()));
        // // process hit


        drone.update_physics(input_t, dt);


        Vec3d v1 = drone.get_speed();
        p.setVelocity(v1);


        Fpv20.LOGGER.debug("after update phy:v1 {}", p.getVelocity());


    }

    public void handle_flying_rotate(MinecraftClient client, float dt) {

        if (!getFlying()) {
            return;
        }

        ClientPlayerEntity p = client.player;
        if (p == null) {
            return;
        }


        // start flying, init the drone
        if (this.last_tick_flying != getFlying()) {
            float yaw = p.getYaw();
            float pitch = p.getPitch();
            drone.re_init();
            drone.update_pose(PhysicsCore.from_ypr_deg(yaw, pitch, 0));
        }
        this.last_tick_flying = getFlying();

        Quaternionf q = drone.get_pose();

        Controller controller = Fpv20Client.controller;
        if (controller == null) {
            return;
        }

        float input_t = controller.get_value_by_name("t");
        float input_y = controller.get_value_by_name("y");
        float input_p = controller.get_value_by_name("p");
        float input_r = controller.get_value_by_name("r");

        PhysicsCore.rotate_from_local_yaw_pitch_roll(q, input_y, input_p, input_r,
                300, 300, 300,
                dt
        );
        drone.update_pose(q);
        this.set_drone_rotation(q);


        Vector3f new_ypr = PhysicsCore.from_quaternion_to_ypr_deg(this.cacl_cam_rotation());
        p.setYaw(new_ypr.x);
        p.setPitch(new_ypr.y);

    }
}
