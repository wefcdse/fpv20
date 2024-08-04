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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class Entity extends net.minecraft.entity.Entity implements IsFlying {

    @Unique
//    private static final EntityDimensions DIM = new EntityDimensions(0.5f, 0.15f, false);
//    @Unique
//    private static int a = 1;
    private static final EntityDimensions DIM = new EntityDimensions(0.5f, 0.15f,0.15f * 0.85F, EntityAttachments.of(0.5f, 0.15f),false);

    @Unique
    public boolean isFlying = false;

    @Unique
    public Object obj = new Object();

    public Entity(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void injected(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
//        cir.getReturnValue();
        if (((IsFlying) this).get_is_flying()) {
            cir.setReturnValue(DIM);
        }

    }

//    @Inject(method = "getActiveEyeHeight", at = @At("RETURN"), cancellable = true)
//    private void injected(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
////        cir.getReturnValue();
//        if (((IsFlying) this).get_is_flying()) {
//            cir.setReturnValue(0.13f);
//        }
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

    @Override
    public Object get_obj() {
        return this.obj;
    }

    @Override
    public void set_obj(Object obj) {
        this.obj = obj;
    }
}
