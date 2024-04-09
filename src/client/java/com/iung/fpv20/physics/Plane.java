package com.iung.fpv20.physics;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.utils.FastMath;
import com.iung.fpv20.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.nio.channels.NotYetBoundException;

public class Plane implements Drone {

    public static class Plate {
        private Quaternionf pose;
        private float area;

        public Plate(Quaternionf pose, float area) {
            this.pose = pose;
            this.area = area;
        }

        /**
         * @param super_pose
         * @param v_global
         * @param c1         总之表示有效流速*面积到受力的换算关系
         * @return
         */
        public Vector3f force(Quaternionf super_pose, Vector3f v_global, float c1) {
            Vector3f up_global = new Vector3f(0, 1, 0).rotate(pose).rotate(super_pose);
            sb(up_global, 10);

            float a = -up_global.dot(v_global);
            float b = a * c1;
            return up_global.normalize().mul(b);
//            throw (new NullPointerException());
        }

    }

    private static final Vector3f G = new Vector3f(0, -9.8f, 0);
//    private static final float AIR_DENSITY = 1.225F;


    private Quaternionf pose;
    private Utils.FloatGetter mass;

    private Utils.FloatGetter max_force;

//    private float area;

    private Plate main_wing;

    private Vector3f a;
    private Vector3f v;

    public Plane() {
        this.pose = new Quaternionf();
        this.a = new Vector3f();
        this.v = new Vector3f();

        this.mass = () -> Fpv20Client.config1.drone.mass;

//        this.area = (float) (FastMath.PI * 0.2 * 0.2);
        this.max_force = () -> Fpv20Client.config1.drone.max_force;

        this.main_wing = new Plate(new Quaternionf(), 1);
    }

    @Override
    public void update_pose(Quaternionf new_pos) {
//        Fpv20.LOGGER.info("{},{}", mass, max_force);

        this.pose = new Quaternionf(new_pos);
    }

    @Override
    public Quaternionf get_pose() {
        return new Quaternionf(this.pose);
    }

    private static void sb(Vector3f v, float sc) {
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
        Quaternionf pose = this.get_pose().conjugate();
        Vector3f drone_up = new Vector3f(0, 1, 0).rotate(pose);
        Vector3f drone_front = new Vector3f(0, 0, -1).rotate(pose);

        float speed = this.v.length();


        float efficiency = MathHelper.lerp(Math.abs(throttle), 0.35f, 1f);
        Vector3f thrust = new Vector3f(drone_front).mul(max_force.get() * throttle * efficiency);
        Fpv20.LOGGER.debug("#thrust {}", thrust);


        // i don't know what's wrong, but it just falls too fast and i don't like it
        float gf = 0.1f;
        if (Math.abs(this.v.y) > 3) {
            gf = 0;
        }
        Fpv20.LOGGER.debug("####vy {}", this.v.y);
        Vector3f g1 = new Vector3f(G).mul(gf);

        Vector3f drag = main_wing.force(pose, this.v, Fpv20Client.config1.plane.c1);
        Vector3f da = drag.div(mass.get());

        Fpv20.LOGGER.info("#drag {}", da);
        Fpv20.LOGGER.info("#g {}", g1);

//        this.a = total_force.div(mass.get()).add(new Vector3f(G).mul(gf));
        this.a = thrust.div(mass.get()).add(g1).add(da);
        if (this.a.length() > 20f) {
            this.a.normalize();
            this.a.mul(20);
        }
        Fpv20.LOGGER.debug("#a {}", this.a);

        Fpv20.LOGGER.debug("#f {}", dt);

        Fpv20.LOGGER.debug("#dv:? {}", new Vector3f(this.a).mul(dt));


        this.v.add(new Vector3f(this.a).mul(dt));
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
        this.v = v.toVector3f();
    }

    @Override
    public void re_init() {
        this.pose = new Quaternionf();
        this.v = new Vector3f();

    }
}
