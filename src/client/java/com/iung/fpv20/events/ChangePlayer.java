package com.iung.fpv20.events;

import com.iung.fpv20.Fpv20;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


public class ChangePlayer implements ClientTickEvents.StartTick {


    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player != null) {
//            float yaw = client.player.getYaw();
//            client.player.setYaw(yaw + 360f * (1f / 20f));
//            client.player.setVelocityClient(0f, 20, 0);
            ClientPlayerEntity player = client.player;
            Vec3d v = player.getVelocity();
//            player.playSound(SoundEvent.of(Identifier.of("minecraft","block.amethyst_cluster.hit")), (float) v.length(),1);
//            player.getCameraPosVec()
            Box box = player.getBoundingBox();
//            Fpv20.LOGGER.info("{}",box);
//            player.setPosition();
//            player.setBoundingBox(player.getDimensions(null).getBoxAt(player.getPos()));
        }
//        client.player.getAbilities()
    }
}
