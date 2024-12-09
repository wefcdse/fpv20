package com.iung.fpv20;

import com.iung.fpv20.config.Fpv20ConfigClientManual;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.network.DroneExplosionPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

import static com.iung.fpv20.flying.GlobalFlying.ZERO_SPEED;

public class Explosion {
    public static boolean explosion_trig() {
        if (controller() || Fpv20Client.config1.explosion_config.always_explode) {
            if (Fpv20Client.config1.explosion_config.stop_fly_after_explosion) {
                GlobalFlying.setFlying(false);
            }
//            GlobalFlying.G.drone.set_speed(new Vec3d(0,0,0));
            ClientPlayNetworking.send(
                    new DroneExplosionPacket(
                            Fpv20Client.config1.explosion_config.power,
                            Fpv20Client.config1.explosion_config.stop_fly_after_explosion,
                            GlobalFlying.start_fly_pos(),
                            Fpv20Client.config1.explosion_config.teleport_after_explosion
                    ));
            return true;
        }
        return false;
    }

    public static boolean controller() {
        float e = Objects.requireNonNull(Fpv20Client.controller).get_value_by_name("expl");
        return e > 0.5;
    }

    private static Fpv20ConfigClientManual.ExplosionConfig.Vec3d min_speed() {
        return Fpv20Client.config1.explosion_config.min_speed;
    }

    public static boolean handle_explosion(Vec3d last_speed, Vec3d now_speed) {
        boolean trig = false;
        if (Math.abs(last_speed.x) > min_speed().x() && Math.abs(now_speed.x) < ZERO_SPEED) {
            trig = true;
        }
        if (Math.abs(last_speed.y) > min_speed().y() && Math.abs(now_speed.y) < ZERO_SPEED) {
            trig = true;
        }
        if (Math.abs(last_speed.z) > min_speed().z() && Math.abs(now_speed.z) < ZERO_SPEED) {
            trig = true;
        }
//        Fpv20.LOGGER.info("he {} / {}", last_speed, now_speed);

        if (trig) {
            Fpv20.LOGGER.info("trigged");

            return explosion_trig();
        }
        return false;
    }


}
