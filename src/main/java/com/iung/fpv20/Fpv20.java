package com.iung.fpv20;

import com.iung.fpv20.blocks.ReceiverBlockEntity;
import com.iung.fpv20.config.Fpv20ConfigCommon;
import com.iung.fpv20.consts.ModBlocks;
import com.iung.fpv20.consts.ModItemGroups;
import com.iung.fpv20.consts.ScreenHandlers;
import com.iung.fpv20.globals.AllChannels;
import com.iung.fpv20.mixin_utils.IsFlying;
//import com.iung.fpv20.network.ChannelUpdatePacket;
import com.iung.fpv20.network.ChannelUpdatePayload;
//import com.iung.fpv20.network.DroneFlyPacket;
import com.iung.fpv20.network.DroneFlyPayload;
//import com.iung.fpv20.network.SetReceiverPacket;
import com.iung.fpv20.network.SetReceiverPayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fpv20 implements ModInitializer {
    public static final String MOD_ID = "fpv20";

    public static Fpv20ConfigCommon config = Fpv20ConfigCommon.createAndLoad();

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {

        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        if (config.client_only) {
            return;
        }
//        LOGGER.info("Hello Fabric world!");
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ScreenHandlers.reg();


        // 注册网络数据
        PayloadTypeRegistry.playC2S().register(ChannelUpdatePayload.ID, ChannelUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ChannelUpdatePayload.ID, (payload, context) -> {
            AllChannels.ALL_CHANNELS.put(payload.name(), new AllChannels.ChannelInfo(payload.value()));
        });

        PayloadTypeRegistry.playC2S().register(DroneFlyPayload.ID, DroneFlyPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DroneFlyPayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            if (payload.fly()) {
                boolean f = player.getAbilities().invulnerable;
                ((IsFlying) player).set_obj(f);
                player.getAbilities().invulnerable = true;
            } else {
                player.getAbilities().invulnerable = (boolean) (Boolean) ((IsFlying) player).get_obj();
            }
            ((IsFlying) player).set_is_flying(payload.fly());
//            player.setBoundingBox(player.getDimensions(null).getBoxAt(player.getPos()));
//            player.refreshPositionAfterTeleport(player.getPos());
//            player.calculateDimensions();
            Fpv20.LOGGER.info("updated");
        });

        PayloadTypeRegistry.playC2S().register(SetReceiverPayload.ID, SetReceiverPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SetReceiverPayload.ID, (payload, context) -> {
            BlockEntity be = context.player().getWorld().getBlockEntity(payload.pos());
            if (!(be instanceof ReceiverBlockEntity r)) {
                return;
            }
            r.channel = payload.channel_name();
            r.neg = payload.neg();
            context.player().getWorld().markDirty(payload.pos());

        });


        ServerTickEvents.START_SERVER_TICK.register((e) -> {
//            LOGGER.info("{}", AllChannels.ALL_CHANNELS);

        });

        ServerLifecycleEvents.SERVER_STOPPING.register(e -> config.save());

        //! here


    }
}