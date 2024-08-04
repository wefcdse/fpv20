package com.iung.fpv20.mixin.client;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.flying.GlobalFlying;
import net.minecraft.client.render.Camera;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public class CameraMixin {
    @Redirect(
            method = "setRotation",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;"
            )
//            index = 2
    )
    private Quaternionf doABarrelRoll$setRoll(Quaternionf instance, float angleY, float angleX, float angleZ) {
//        var roll = tempRoll.get();
//        if (roll != null) {
//            this.roll = roll;
//            return (float) (this.roll * MagicNumbers.TORAD);
//        }
        if (GlobalFlying.getFlying()) {
//            matrix.multiply(GlobalFlying.G.cacl_cam_rotation_last().nlerp(GlobalFlying.G.cacl_cam_rotation(), tickDelta));
//            matrix.multiply(GlobalFlying.G.cacl_cam_rotation());
            Fpv20.LOGGER.info("{}", GlobalFlying.G.cacl_cam_rotation());
            instance.set(GlobalFlying.G.cacl_cam_rotation().conjugate());

            return instance;
        }
//        Fpv20.LOGGER.info("{}", angleZ);
        return instance.rotationYXZ(angleY, angleX, angleZ);
//        return instance;
    }
}
