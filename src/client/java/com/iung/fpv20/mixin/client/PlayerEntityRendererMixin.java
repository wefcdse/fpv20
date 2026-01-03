package com.iung.fpv20.mixin.client;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.flying.GlobalFlying;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(
            method = "renderRightArm",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin0(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, Identifier skinTexture, boolean sleeveVisible, CallbackInfo ci) {
        if(GlobalFlying.getFlying() && Fpv20Client.config1.disable_player_render_when_flying){
            ci.cancel();
        }
    }

    @Inject(
            method = "renderLeftArm",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin1(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, Identifier skinTexture, boolean sleeveVisible, CallbackInfo ci) {
        if(GlobalFlying.getFlying() && Fpv20Client.config1.disable_player_render_when_flying){
            ci.cancel();
        }
    }

    @Inject(
            method = "renderArm",
            at =  @At("HEAD"), cancellable = true
    )
    public void mixin2(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
        if(GlobalFlying.getFlying() && Fpv20Client.config1.disable_player_render_when_flying){
            ci.cancel();
        }
    }
}
