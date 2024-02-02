package com.iung.fpv20.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayNetworkHandler.class)
public class MoveFastMixin {

    @ModifyConstant(method = "onPlayerMove(Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;)V", constant = @Constant(floatValue = 100.0F))
    public float maxPlayerSpeed(float speed){
        return (float) 1000_000_000.;
    }

    @ModifyConstant(method = "onPlayerMove(Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;)V", constant = @Constant(floatValue = 300.0F))
    public float maxFastSpeed(float speed){
        return (float) 1000_000_000.;
    }

}
