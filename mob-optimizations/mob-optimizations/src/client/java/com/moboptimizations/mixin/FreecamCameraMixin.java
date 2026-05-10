package com.moboptimizations.mixin;

import com.moboptimizations.MobOptimizationsMod;
import com.moboptimizations.feature.FreecamState;
import net.minecraft.block.BlockView;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * When freecam is active, overrides the camera position/rotation after the
 * normal update so it sits at the detached FreecamState position while still
 * using the player's current yaw/pitch (driven by mouse-look).
 */
@Mixin(Camera.class)
public class FreecamCameraMixin {

    @Inject(method = "update", at = @At("RETURN"))
    private void onCameraUpdate(
            BlockView area,
            Entity focusedEntity,
            boolean thirdPerson,
            boolean inverseView,
            float tickDelta,
            CallbackInfo ci
    ) {
        if (!MobOptimizationsMod.freecamEnabled) return;
        FreecamState state = MobOptimizationsMod.freecam;
        if (state == null || focusedEntity == null) return;

        // Cast self to our invoker interface to call protected Camera methods
        CameraInvokerMixin self = (CameraInvokerMixin) (Object) this;

        // Place the camera at the freecam position
        self.invokeSetPos(state.x, state.y, state.z);

        // Keep looking where the player's mouse points
        float yaw   = focusedEntity.getYaw(tickDelta);
        float pitch = focusedEntity.getPitch(tickDelta);
        self.invokeSetRotation(yaw, pitch);
    }
}
