package com.moboptimizations.mixin;

import com.moboptimizations.MobOptimizationsMod;
import com.moboptimizations.feature.XRayHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * X-Ray part 1 – opacity.
 *
 * When X-Ray is enabled, every non-ore block reports itself as non-opaque.
 * This tells the chunk builder to render the faces of adjacent ore blocks
 * that would normally be hidden behind solid terrain, making ores visible
 * through walls after a chunk reload.
 */
@Mixin(AbstractBlock.AbstractBlockState.class)
public class XRayBlockStateMixin {

    @Inject(method = "isOpaque", at = @At("HEAD"), cancellable = true)
    private void onIsOpaque(CallbackInfoReturnable<Boolean> cir) {
        if (!MobOptimizationsMod.xrayEnabled) return;

        Block block = ((BlockState) (Object) this).getBlock();
        if (!XRayHelper.isOre(block)) {
            // Non-ore → pretend it is transparent so neighbouring ore faces render
            cir.setReturnValue(false);
        }
    }
}
