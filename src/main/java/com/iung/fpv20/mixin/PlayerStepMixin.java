package com.iung.fpv20.mixin;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.mixin_utils.IsFlying;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class PlayerStepMixin {


    @Inject(method = "getStepHeight", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfoReturnable<Float> cir) {
//        cir.getReturnValue();
        if (((IsFlying) this).get_is_flying()) {
            cir.setReturnValue(Fpv20.config.step_height());
        }
    }


//    @Inject(method = "attack", at = @At("RETURN"))
//    private void injected(Entity target, CallbackInfo ci) {
//        Fpv20.LOGGER.info("attack");
//        this.isFlying = !this.isFlying;
//        this.setBoundingBox(this.getDimensions(null).getBoxAt(this.getPos()));
//        this.markEffectsDirty();
//    }

//    @Override
//    public boolean get_is_flying() {
//        return this.isFlying;
//    }
//
//    @Override
//    public void set_is_flying(boolean v) {
//
//        this.isFlying = v;
//        this.calculateDimensions();
//    }
}
