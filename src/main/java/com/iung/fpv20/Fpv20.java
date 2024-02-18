package com.iung.fpv20;

import com.iung.fpv20.blocks.ReceiverBlockEntity;
import com.iung.fpv20.config.Fpv20ConfigCommon;
import com.iung.fpv20.consts.ModBlocks;
import com.iung.fpv20.consts.ModItemGroups;
import com.iung.fpv20.consts.ScreenHandlers;
import com.iung.fpv20.globals.AllChannels;
import com.iung.fpv20.mixin_utils.IsFlying;
import com.iung.fpv20.network.ChannelUpdatePacket;
import com.iung.fpv20.network.DroneFlyPacket;
import com.iung.fpv20.network.SetReceiverPacket;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fpv20 implements ModInitializer {
    public static final String MOD_ID = "fpv20";

    public static Fpv20ConfigCommon config = Fpv20ConfigCommon.createAndLoad();

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("fpv20");


    @Override
    public void onInitialize() {

        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

//        LOGGER.info("Hello Fabric world!");
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ScreenHandlers.reg();
        ServerPlayNetworking.registerGlobalReceiver(ChannelUpdatePacket.TYPE, (packet, player, responseSender) -> {
            AllChannels.ALL_CHANNELS.put(packet.name, new AllChannels.ChannelInfo(packet.value));
        });

        ServerPlayNetworking.registerGlobalReceiver(SetReceiverPacket.TYPE, (packet, player, responseSender) -> {
            BlockEntity be = player.getWorld().getBlockEntity(packet.pos);
            if (!(be instanceof ReceiverBlockEntity r)) {
                return;
            }
            r.channel = packet.channel_name;
            r.neg = packet.neg;
            player.getWorld().markDirty(packet.pos);

        });


        ServerTickEvents.START_SERVER_TICK.register((e) -> {
//            LOGGER.info("{}", AllChannels.ALL_CHANNELS);

        });

        ServerLifecycleEvents.SERVER_STOPPING.register(e -> config.save());

        ServerPlayNetworking.registerGlobalReceiver(DroneFlyPacket.TYPE, (packet, player, responseSender) -> {

            ((IsFlying) player).set_is_flying(packet.fly);
//            player.setBoundingBox(player.getDimensions(null).getBoxAt(player.getPos()));
//            player.refreshPositionAfterTeleport(player.getPos());
//            player.calculateDimensions();
            Fpv20.LOGGER.info("updated");
        });

    }
}