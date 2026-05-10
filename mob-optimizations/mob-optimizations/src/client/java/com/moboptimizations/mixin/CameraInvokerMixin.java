package com.moboptimizations.mixin;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Exposes Camera's protected helper methods so FreecamCameraMixin can call them.
 */
@Mixin(Camera.class)
public interface CameraInvokerMixin {

    @Invoker("setRotation")
    void invokeSetRotation(float yaw, float pitch);

    @Invoker("setPos")
    void invokeSetPos(double x, double y, double z);
}
