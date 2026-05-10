package com.moboptimizations.mixin;

import com.moboptimizations.MobOptimizationsMod;
import com.moboptimizations.feature.XRayHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * X-Ray part 2 – visibility.
 *
 * Skips actually drawing geometry for non-ore blocks when X-Ray is enabled.
 * Combined with the opacity mixin (part 1), the result is:
 *   • Ore faces bordering "transparent" terrain ARE built into the chunk mesh
 *   • Non-ore blocks themselves produce no geometry → you see straight through them
 *
 * Note: this hooks the chunk-building render path; the chunk must be rebuilt
 * (worldRenderer.reload()) for the change to take effect – handled in the toggle.
 */
@Mixin(BlockRenderManager.class)
public class XRayBlockRenderMixin {

    @Inject(
            method = "renderBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRenderBlock(
            BlockState state,
            BlockPos pos,
            BlockRenderView world,
            MatrixStack matrices,
            VertexConsumer vertices,
            boolean cull,
            Random random,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!MobOptimizationsMod.xrayEnabled) return;
        if (!XRayHelper.isOre(state.getBlock())) {
            cir.setReturnValue(false); // skip geometry for non-ores
        }
    }
}
