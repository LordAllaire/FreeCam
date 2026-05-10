package com.moboptimizations.feature;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import java.util.Set;

/**
 * Defines which blocks are treated as "ores" and thus remain visible
 * during X-Ray mode. Everything else is hidden (made non-opaque / unrendered).
 */
public final class XRayHelper {

    private static final Set<Block> ORES = Set.of(
            // Overworld ores
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            // Nether ores
            Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.ANCIENT_DEBRIS,
            // Extra – raw ore blocks & chests (useful to see)
            Blocks.RAW_COPPER_BLOCK,
            Blocks.RAW_IRON_BLOCK,
            Blocks.RAW_GOLD_BLOCK,
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST,
            Blocks.ENDER_CHEST,
            // Structures
            Blocks.SPAWNER,
            Blocks.TRIAL_SPAWNER
    );

    private XRayHelper() {}

    public static boolean isOre(Block block) {
        return ORES.contains(block);
    }
}
