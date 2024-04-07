package com.iung.fpv20.mixin.client;

import com.iung.fpv20.flying.GlobalFlying;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class Hud {
    @Inject(
            method = "renderHotbar",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin12(float tickDelta, DrawContext context, CallbackInfo ci) {
        if(GlobalFlying.getFlying()){
            ci.cancel();
        }
    }

    @Inject(
            method = "renderMountHealth",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin2(DrawContext context, CallbackInfo ci) {
        if(GlobalFlying.getFlying()){
            ci.cancel();
        }
    }

    @Inject(
            method = "renderStatusBars",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin3(DrawContext context, CallbackInfo ci) {
        if(GlobalFlying.getFlying()){
            ci.cancel();
        }
    }

    @Inject(
            method = "renderExperienceBar",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin4(DrawContext context, int x, CallbackInfo ci) {
        if(GlobalFlying.getFlying()){
            ci.cancel();
        }
    }
}
