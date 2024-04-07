package com.iung.fpv20.mixin;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.mixin_utils.IsFlying;
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
public abstract class Entity extends net.minecraft.entity.Entity implements IsFlying {

    @Unique
    private static final EntityDimensions DIM = new EntityDimensions(0.5f, 0.15f, false);
//    @Unique
//    private static int a = 1;

    @Unique
    public boolean isFlying = false;

    @Unique
    public Object obj = new Object();

    public Entity(EntityType<?> type, World world) {
        super(type, world);
    }


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
