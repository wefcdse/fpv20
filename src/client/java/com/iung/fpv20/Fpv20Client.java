package com.iung.fpv20;

import com.google.common.util.concurrent.AtomicDouble;
import com.iung.fpv20.consts.ScreenHandlers;
import com.iung.fpv20.events.ChangePlayer;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.gui.handle_screen.ReceiverScreen;
import com.iung.fpv20.input.Controller;
import com.iung.fpv20.utils.FastMath;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.Start;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

public class Fpv20Client implements ClientModInitializer {
    @Nullable
    public static volatile Controller controller = null;
    public static AtomicDouble last_time_tickDelta = new AtomicDouble(0.0);

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.RECEIVER_SCREEN_HANDLER, ReceiverScreen::new);
//


        ClientTickEvents.START_CLIENT_TICK.register((e) -> {
            Controller controller1 = controller;
            if (controller1 != null) {
                try {
                    controller1.poll();
                    controller1.sync_to_server();
                } catch (Exception err) {
//                    Fpv20.LOGGER.info("err:!", err);
                }
//                SpreadPlayersCommand

            }

//            Fpv20.LOGGER.info(String.valueOf(e));
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            last_time_tickDelta.getAndUpdate(v -> FastMath.clamp(v - 1, -1, 0));
            ClientPlayerEntity player = client.player;
            if (player != null) {
                GlobalFlying.G.update_tick_start(player);
                GlobalFlying.G.handle_flying_phy(player, 0.05f);
//                GlobalFlying.G.handle_flying(client);
            }
        });
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.


    }
}