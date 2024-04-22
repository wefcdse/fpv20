package com.iung.fpv20;

//import com.iung.fpv20.config.Fpv20ClientConfig;

import com.iung.fpv20.config.Fpv20ClientConfig1;
import com.iung.fpv20.config.Fpv20ConfigClientManual;
import com.iung.fpv20.config.Fpv20ConfigCommon;
import com.iung.fpv20.consts.ScreenHandlers;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.gui.handle_screen.ReceiverScreen;
import com.iung.fpv20.gui.hud.SticksHud;
import com.iung.fpv20.input.Controller;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class Fpv20Client implements ClientModInitializer {
    @Nullable
    public static volatile Controller controller;
    public static Fpv20ClientConfig1 config = Fpv20ClientConfig1.createAndLoad();


    static {
        int c = Fpv20Client.config.selected_controller();
        if (c >= 0) {
            controller = new Controller(c);
        }
    }

    public static Fpv20ConfigClientManual config1 = Fpv20ConfigClientManual.createAndLoad();


    static boolean last_tick_flying = false;
    static boolean last_tick_reload = false;

    public static boolean in_slow_motion = false;

    private static KeyBinding osdKeybind;

    @Override
    public void onInitializeClient() {
        osdKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "fpv20.keybind.osd",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "fpv20.keybinds.category"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (osdKeybind.wasPressed()) {
                config.setShow_osd(!config.show_osd());
            }
        });

        HandledScreens.register(ScreenHandlers.RECEIVER_SCREEN_HANDLER, ReceiverScreen::new);
//

        HudRenderCallback.EVENT.register(new SticksHud());

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

                try {
                    in_slow_motion = controller1.get_value_by_name(config1.slow_motion_switch_name) > 0.5f;
                } catch (Exception err) {
                    Fpv20.LOGGER.error("err:!", err);
                }

                try {
                    boolean reload = controller1.get_value_by_name("reload") > 0.5;
                    if (last_tick_reload != reload) {
                        Fpv20Client.config1 = Fpv20ConfigClientManual.createAndLoad();
                        Fpv20.config = Fpv20ConfigCommon.createAndLoad();
                    }
                    last_tick_reload = reload;
                } catch (Exception err) {
                    Fpv20.LOGGER.error("err:!", err);
                }
//                SpreadPlayersCommand

            }
        });
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
//            update_config();
            config.save();
            config1.save();
        });

    }

    public static void update_config() {
        Controller controller1 = controller;
        if (controller1 != null) {
            config.calibrations(null);
            config.stick_channel_names(null);
            config.button_channel_names(null);

            config.calibrations(controller1.calibrations);
            config.stick_channel_names(controller1.names);
            config.button_channel_names(controller1.btn_names);
            Fpv20.LOGGER.info("saving config");
        }
    }
}

