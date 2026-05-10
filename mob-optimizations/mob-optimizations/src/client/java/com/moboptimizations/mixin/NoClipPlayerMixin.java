package com.moboptimizations.mixin;

import com.moboptimizations.MobOptimizationsMod;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NoClip – allows the player to walk / fly through blocks.
 *
 * Every tick we set noClip=true on the player entity when the feature is
 * active. The flag is cleared automatically when the feature is turned off
 * because we stop setting it and Minecraft resets it based on game-mode rules.
 */
@Mixin(ClientPlayerEntity.class)
public class NoClipPlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ClientPlayerEntity self = (ClientPlayerEntity) (Object) this;
        if (MobOptimizationsMod.noclipEnabled || MobOptimizationsMod.freecamEnabled) {
            // noClip disables collision detection for this entity
            self.noClip = true;
        }
    }
}
