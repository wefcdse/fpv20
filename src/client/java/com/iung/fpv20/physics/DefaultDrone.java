package com.iung.fpv20.physics;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.port.PortVec3f;
import com.iung.fpv20.utils.FastMath;
import com.iung.fpv20.utils.Utils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
//import org.joml.Quaternion;
//import org.joml.Vec3f;
import net.minecraft.util.math.Vec3f;
import oshi.driver.mac.net.NetStat;

public class DefaultDrone implements Drone {

    private static final Vec3f G = new Vec3f(0, -9.8f, 0);
    private static final float AIR_DENSITY = 1.225F;


    private Quaternion pose;
    private Utils.FloatGetter mass;

    private Utils.FloatGetter max_force;

    private float area;


    private Vec3f a;
    private Vec3f v;

    public DefaultDrone() {
        this.pose = new Quaternion(0, 0, 0, false);
        this.a = new Vec3f();
        this.v = new Vec3f();

        this.mass = () -> Fpv20Client.config1.drone.mass;

        this.area = (float) (FastMath.PI * 0.2 * 0.2);
        this.max_force = () -> Fpv20Client.config1.drone.max_force;
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

    @Override
    public void update_physics(float throttle, float dt) {
        Quaternion pose = this.get_pose();
        pose.conjugate();
        Vec3f drone_up = new Vec3f(0, 1, 0);
        drone_up.rotate(pose);

//        float speed = this.v.length();
        float speed = PortVec3f.length(this.v);


        float dragFactor = (AIR_DENSITY * area) /
                2F; // kg / m
        Vec3f ambientDragForce = new Vec3f(this.v.getX(), this.v.getY(), this.v.getZ());
        ambientDragForce.normalize();
        ambientDragForce.scale(-1f *
                speed *
                speed *
                dragFactor);
//        if (this.v.length() < 0.00001) {
        if (PortVec3f.length(this.v) < 0.00001) {
            ambientDragForce = new Vec3f();
        }
        Fpv20.LOGGER.debug("#ambientDragForce {}", ambientDragForce);


        float efficiency = MathHelper.lerp(Math.abs(throttle), 0.35f, 1f);
        Vec3f thrust = new Vec3f(drone_up.getX(), drone_up.getY(), drone_up.getZ());
        thrust.scale(max_force.get() * throttle * efficiency);

        Fpv20.LOGGER.debug("#thrust {}", thrust);

        Vec3f total_force = new Vec3f();
        total_force.add(ambientDragForce);
        total_force.add(thrust);

        // i don't know what's wrong, but it just falls too fast and i don't like it
        float gf = 0.1f;
        if (Math.abs(this.v.getY()) > 3) {
            gf = 0;
        }
        Fpv20.LOGGER.debug("####vy {}", this.v.getY());

//        this.a = total_force.div(mass.get()).add(new Vec3f(G).mul(gf));
        total_force.scale((float) (1.0 / mass.get()));
        var ade = new Vec3f(G.getX(), G.getY(), G.getZ());
        ade.scale(gf);
        total_force.add(ade);
        ;

        Fpv20.LOGGER.debug("#a {}", this.a);

        Fpv20.LOGGER.debug("#f {}", dt);

//        Fpv20.LOGGER.debug("#dv:? {}", new Vec3f(this.a).mul(dt));

        Vec3f added = new Vec3f(this.a.getX(), this.a.getY(), this.a.getZ());
        added.scale(dt);
        this.v.add(added);
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
//        this.v = v.toVec3f();
        this.v = new Vec3f(v);
    }

    @Override
    public void re_init() {
        this.pose = new Quaternion(0, 0, 0, false);
        this.v = new Vec3f();

    }
}
