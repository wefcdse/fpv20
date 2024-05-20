package com.iung.fpv20.consts;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.screen_handler.ReceiverBlockHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenHandlers {
    public static final ScreenHandlerType<ReceiverBlockHandler> RECEIVER_SCREEN_HANDLER;

    static {
        RECEIVER_SCREEN_HANDLER = Fpv20.config.client_only ? null : Registry.register(Registries.SCREEN_HANDLER, ModBlocks.RECEIVER_BLOCK_ID,
                new ExtendedScreenHandlerType<>(ReceiverBlockHandler::new));

    }

    public static void reg() {

    }
}
