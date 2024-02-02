package com.iung.fpv20.mixin.client;

import com.iung.fpv20.flying.GlobalFlying;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private Camera camera;

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;",
                    ordinal = 2
            )
    )
    public void mixin(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {

//        GlobalFlying.G.lastCamRoll += tickDelta / 20f * GlobalFlying.G.angular_speed;
        matrix.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(GlobalFlying.G.lastCamRoll));

        Matrix4f mat = new Matrix4f();

        Quaternionf r = RotationAxis.POSITIVE_Z.rotationDegrees(GlobalFlying.G.lastCamRoll);
//        r.getEulerAnglesXYZ()

        //        r.mul()
//        matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
//        matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));



    }

}
