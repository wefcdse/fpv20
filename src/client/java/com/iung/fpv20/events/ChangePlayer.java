package com.iung.fpv20.events;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.flying.GlobalFlying;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


public class ChangePlayer  {


    public static void onStartTick(MinecraftClient client) {
        if (client.player != null) {

            ClientPlayerEntity player = client.player;
            Vec3d v = player.getVelocity();

            GlobalFlying.G._handle_flying(client);
        }
    }
}
