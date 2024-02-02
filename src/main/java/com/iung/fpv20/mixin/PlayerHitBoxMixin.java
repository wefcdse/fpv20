package com.iung.fpv20.mixin;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.mixin_utils.IsFlying;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerHitBoxMixin extends LivingEntity implements IsFlying {

    @Unique
    private static final EntityDimensions DIM = new EntityDimensions(0.5f, 0.15f, false);
//    @Unique
//    private static int a = 1;

    @Unique
    public boolean isFlying = false;

    protected PlayerHitBoxMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.isFlying = false;
        Fpv20.LOGGER.info("new");

    }


//    @Override
//    public void tickMovement() {
//        super.tickMovement();
//    }

    @Inject(method = "getDimensions(Lnet/minecraft/entity/EntityPose;)Lnet/minecraft/entity/EntityDimensions;", at = @At("RETURN"), cancellable = true)
    private void injected(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
//        cir.getReturnValue();
        if (this.isFlying) {
            cir.setReturnValue(DIM);
        }

    }

    @Inject(method = "getActiveEyeHeight", at = @At("RETURN"), cancellable = true)
    private void injected(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
//        cir.getReturnValue();
        if (this.isFlying) {
            cir.setReturnValue(0.1f);
        }
    }

//    @Inject(method = "attack", at = @At("RETURN"))
//    private void injected(Entity target, CallbackInfo ci) {
//        Fpv20.LOGGER.info("attack");
//        this.isFlying = !this.isFlying;
//        this.setBoundingBox(this.getDimensions(null).getBoxAt(this.getPos()));
//        this.markEffectsDirty();
//    }

    @Override
    public boolean get_is_flying() {
        return this.isFlying;
    }

    @Override
    public void set_is_flying(boolean v) {

        this.isFlying = v;
        this.calculateDimensions();
    }
}
