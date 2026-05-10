package com.moboptimizations.mixin;

import com.moboptimizations.MobOptimizationsMod;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Blocks the player's keyboard movement when freecam is active so that
 * pressing WASD only moves the detached camera, not the player entity.
 * The key binding's "isPressed()" state is still readable for the camera tick.
 */
@Mixin(KeyboardInput.class)
public class FreecamKeyboardInputMixin {

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(boolean slowDown, float f, CallbackInfo ci) {
        if (!MobOptimizationsMod.freecamEnabled) return;

        Input self = (Input) (Object) this;
        // Zero out player movement so the entity stays in place
        self.pressingForward  = false;
        self.pressingBack     = false;
        self.pressingLeft     = false;
        self.pressingRight    = false;
        self.jumping          = false;
        self.sneaking         = false;
        self.movementForward  = 0f;
        self.movementSideways = 0f;
    }
}
