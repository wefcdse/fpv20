package com.iung.fpv20.mixin.client;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.events.ChangePlayer;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.utils.FastMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.GameRenderer.class)
public class GameRendererMixin {
//    @Shadow
//    @Final
//    private Camera camera;

    private static long last_render = System.nanoTime();


//    @Inject(
//            method = "tick",
//            at = @At(
//                    value = "HEAD"
//            )
//    )
//    public void tick(CallbackInfo ci) {
//        last_time_tickDelta = 0.0f;
//    }

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;",
                    ordinal = 2
            )
    )
    public void mixin(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {

        long time_start = System.nanoTime();


        MinecraftClient client = MinecraftClient.getInstance();

//        float dt = (float) ((tickDelta - Fpv20Client.last_time_tickDelta.get()) * 0.05f);
        float dt = (float) (time_start - last_render) / 1000_000_000f;
        last_render = time_start;
        // if fps is lower than 10, just use another computer
        dt = FastMath.clamp(dt, 0, 0.1f);



//        if (Fpv20Client.last_time_tickDelta>tickDelta){
//
//        }

        if (client != null) {
//            ChangePlayer.onStartTick(client);
            GlobalFlying.G.handle_flying_rotate(client, dt);
            long time_end = System.nanoTime();
            Fpv20.LOGGER.info("event_time {}", time_end - time_start);

        }

//        this_time_tickDelta = tickDelta;
//        GlobalFlying.G.lastCamRoll += tickDelta / 20f * GlobalFlying.G.angular_speed;
        if (GlobalFlying.getFlying()) {
//            matrix.multiply(GlobalFlying.G.cacl_cam_rotation_last().nlerp(GlobalFlying.G.cacl_cam_rotation(), tickDelta));
            matrix.multiply(GlobalFlying.G.cacl_cam_rotation());

        }

//        Matrix4f mat = new Matrix4f();
//
//        Quaternionf r = RotationAxis.POSITIVE_Z.rotationDegrees(GlobalFlying.G.lastCamRoll);
//        r.getEulerAnglesXYZ()

        //        r.mul()
//        matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
//        matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));


    }

//    @Inject(
//            method = "renderWorld",
//            at = @At(
//                    value = "INVOKE",
//                    target = "",
//                    ordinal = 2
//            )
//    )
//    public void mixin(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
//
//    }

    @Redirect(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V",
                    ordinal = 2
            )
    )
    public void mixin1(MatrixStack instance, Quaternionf quaternion) {
        if (GlobalFlying.getFlying()) {

        } else {
            instance.multiply(quaternion);
        }
    }

    @Redirect(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V",
                    ordinal = 3
            )
    )
    public void mixin12(MatrixStack instance, Quaternionf quaternion) {
        if (GlobalFlying.getFlying()) {

        } else {
            instance.multiply(quaternion);
        }
    }

}
