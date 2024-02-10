package com.iung.fpv20;

import com.iung.fpv20.consts.ScreenHandlers;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.gui.handle_screen.ReceiverScreen;
import com.iung.fpv20.input.Controller;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Fpv20Client implements ClientModInitializer {
    @Nullable
    public static volatile Controller controller = null;


    static boolean last_tick_flying = false;

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.RECEIVER_SCREEN_HANDLER, ReceiverScreen::new);
//


        ClientTickEvents.START_CLIENT_TICK.register((e) -> {
            Controller controller1 = controller;
            if (controller1 != null) {
                try {
//                    long t = System.nanoTime();
                    controller1.poll();
//                    Fpv20.LOGGER.info("{}", System.nanoTime() - t);
                    controller1.sync_to_server();
                } catch (Exception err) {
                    Fpv20.LOGGER.error("err:!", err);
                }
//                SpreadPlayersCommand

            }

//            Fpv20.LOGGER.debug(String.valueOf(e));
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player != null) {
                GlobalFlying.G.update_tick_start(player);
                GlobalFlying.G.handle_flying_phy(player, 0.05f);
//                GlobalFlying.G.handle_flying(client);
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            Controller controller1 = controller;
            if (controller1 != null) {
                try {
                    boolean start_flying = controller1.get_value_by_name("sw") > 0.5f;
                    if (last_tick_flying != start_flying) {
                        GlobalFlying.setFlying(start_flying);
                    }
                    last_tick_flying = start_flying;
                } catch (Exception err) {
                    Fpv20.LOGGER.error("err:!", err);
                }
//                SpreadPlayersCommand

            }
        });
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.


    }
}