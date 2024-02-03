package com.iung.fpv20.physics;

import com.iung.fpv20.Fpv20;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DefaultDrone implements Drone {

    private static final Vector3f G = new Vector3f(0,-9.8f,0);
    private static final float AIR_DENSITY = 1.225F;


    private Quaternionf pose;
    private float mass;

    private float max_force;

    private float area;


    private Vector3f a;
    private Vector3f v;

    public DefaultDrone() {
        this.pose = new Quaternionf();
        this.mass = 20;
        this.a = new Vector3f();
        this.v = new Vector3f();
    }

    @Override
    public void update_pose(Quaternionf new_pos) {
        this.pose = new Quaternionf(new_pos);
    }

    @Override
    public Quaternionf get_pose() {
        return new Quaternionf(this.pose);
    }

    @Override
    public void update_physics(float throttle, float dt) {
        Quaternionf pose = this.get_pose().conjugate();
//        Vector3f v0 = new Vector3f(1, 0, 0).rotate(pose).mul(throttle);
        Vector3f v1 = new Vector3f(0, 1, 0).rotate(pose).mul(throttle);
//        Vector3f v2 = new Vector3f(0, 0, 1).rotate(pose).mul(throttle);

//        float dragFactor = (PhysicsConstants.airDensity * area * dragCoefficient) /
//                2F; // kg / m

//        Fpv20.LOGGER.info("x:{}",v0);
        Fpv20.LOGGER.info("y:{}", v1);
//        Fpv20.LOGGER.info("z:{}", v2);
//        Fpv20.LOGGER.info("p:{}", pose);

//        Vector3f v1 = new Vector3f(0, 1, 0),(this.pose).mul(throttle);
        this.a = v1;
//               this.pose.transformUnitPositiveY(new Vector3f()).mul(throttle);
        this.v.add(new Vector3f(this.a).mul(dt));
    }

    @Override
    public Vec3d get_acceleration() {
//        Fpv20.LOGGER.info("acc:{}", this.a);
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
