package com.iung.fpv20.flying;

import com.iung.fpv20.mixin_utils.IsFlying;
import com.iung.fpv20.network.DroneFlyPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class GlobalFlying {
    public static GlobalFlying G = new GlobalFlying(0, 90);
    public int lastCamRoll;

    /**
     * deg/s
     */
    public float angular_speed;

    public GlobalFlying(int lastCamRoll, float angular_speed) {
        this.lastCamRoll = lastCamRoll;
        this.angular_speed = angular_speed;
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
}
