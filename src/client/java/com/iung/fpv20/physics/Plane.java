package com.iung.fpv20.physics;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.utils.FastMath;
import com.iung.fpv20.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.*;

import java.nio.channels.NotYetBoundException;

public class Plane implements Drone {

    public static class Plate {
        private Quaternion pose;
        private float area;

        public Plate(Quaternion pose, float area) {
            this.pose = pose;
            this.area = area;
        }

        /**
         * @param super_pose
         * @param v_global
         * @param c1         总之表示有效流速*面积到受力的换算关系
         * @return
         */
        public Vec3f force(Quaternion super_pose, Vec3f v_global, float c1) {
            Vec3f up_global = new Vec3f(0, 1, 0).rotate(pose).rotate(super_pose);
            sb(up_global, 10);

            float a = -up_global.dot(v_global);
            float b = a * c1;
            return up_global.normalize().mul(b);
//            throw (new NullPointerException());
        }

    }

    private static final Vec3f G = new Vec3f(0, -9.8f, 0);
//    private static final float AIR_DENSITY = 1.225F;


    private Quaternion pose;
    private Utils.FloatGetter mass;

    private Utils.FloatGetter max_force;

//    private float area;

    private Plate main_wing;

    private Vec3f a;
    private Vec3f v;

    public Plane() {
        this.pose = new Quaternion(0,0,0,false);
        this.a = new Vec3f();
        this.v = new Vec3f();

        this.mass = () -> Fpv20Client.config1.drone.mass;

//        this.area = (float) (FastMath.PI * 0.2 * 0.2);
        this.max_force = () -> Fpv20Client.config1.drone.max_force;

        this.main_wing = new Plate(new Quaternion(0,0,0,false), 1);
    }

    @Override
    public void update_pose(Quaternion new_pos) {
//        Fpv20.LOGGER.info("{},{}", mass, max_force);

        this.pose = new Quaternion(new_pos);
    }

    @Override
    public Quaternion get_pose() {
        return new Quaternion(this.pose);
    }

    private static void sb(Vec3f v, float sc) {
        if (true) {
            return;
        }
        float x = (float) MinecraftClient.getInstance().player.getPos().x;
        float y = (float) MinecraftClient.getInstance().player.getPos().y;
        float z = (float) MinecraftClient.getInstance().player.getPos().z;
        MinecraftClient.getInstance().world.setBlockState(new BlockPos((int) (x + v.x * sc), (int) (y + v.y * sc), (int) (z + v.z * sc)), Blocks.STONE.getDefaultState());
    }

    @Override
    public void update_physics(float throttle, float dt) {
        Quaternion pose = this.get_pose().conjugate();
        Vec3f drone_up = new Vec3f(0, 1, 0).rotate(pose);
        Vec3f drone_front = new Vec3f(0, 0, -1).rotate(pose);

        float speed = this.v.length();


        float efficiency = MathHelper.lerp(Math.abs(throttle), 0.35f, 1f);
        Vec3f thrust = new Vec3f(drone_front).mul(max_force.get() * throttle * efficiency);
        Fpv20.LOGGER.debug("#thrust {}", thrust);


        // i don't know what's wrong, but it just falls too fast and i don't like it
        float gf = 0.1f;
        if (Math.abs(this.v.y) > 3) {
            gf = 0;
        }
        Fpv20.LOGGER.debug("####vy {}", this.v.y);
        Vec3f g1 = new Vec3f(G).mul(gf);

        Vec3f drag = main_wing.force(pose, this.v, Fpv20Client.config1.plane.c1);
        Vec3f da = drag.div(mass.get());

        Fpv20.LOGGER.info("#drag {}", da);
        Fpv20.LOGGER.info("#g {}", g1);

//        this.a = total_force.div(mass.get()).add(new Vec3f(G).mul(gf));
        this.a = thrust.div(mass.get()).add(g1).add(da);
        if (this.a.length() > 20f) {
            this.a.normalize();
            this.a.mul(20);
        }
        Fpv20.LOGGER.debug("#a {}", this.a);

        Fpv20.LOGGER.debug("#f {}", dt);

        Fpv20.LOGGER.debug("#dv:? {}", new Vec3f(this.a).mul(dt));


        this.v.add(new Vec3f(this.a).mul(dt));
    }

    @Override
    public Vec3d get_acceleration() {
//        Fpv20.LOGGER.debug("acc:{}", this.a);
        return new Vec3d(this.a);
    }

    @Override
    public Vec3d get_speed() {
        return new Vec3d(this.v);
    }

    @Override
    public void set_speed(Vec3d v) {
        this.v = v.toVec3f();
    }

    @Override
    public void re_init() {
        this.pose = new Quaternion(0,0,0,false);
        this.v = new Vec3f();

    }
}
