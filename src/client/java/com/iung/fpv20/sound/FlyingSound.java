//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.iung.fpv20.sound;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class FlyingSound extends MovingSoundInstance {
    public static final int field_32996 = 20;
    private final ClientPlayerEntity player;
    private int tickCount;

    public FlyingSound(ClientPlayerEntity player) {
        super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.1F;
    }

    public void tick() {
        ++this.tickCount;
        if (!this.player.isRemoved() && GlobalFlying.getFlying()) {
            this.x = this.player.getX();
            this.y = this.player.getY();
            this.z = this.player.getZ();

            float f = Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_value_by_name("t"), 0f);

            this.volume = MathHelper.lerp(f, 0.25F, 1.0F);
            this.pitch = MathHelper.lerp(f, 0.5F, 2.0F);


        } else {
            this.setDone();
        }
    }
}
