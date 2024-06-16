package com.subterra.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {
    public static final AlloyFurnaceBlock ALLOY_FURNACE_BLOCK = new AlloyFurnaceBlock(
            AbstractBlock.
                    Settings.
                    create().
                    mapColor(MapColor.DEEPSLATE_GRAY).
                    requiresTool().
                    strength(3.5F).
                    luminance(createLightLevelFromLitBlockState(13))
    );

    public static void registerModBlocks(){
        Registry.register(Registries.BLOCK, Identifier.of("subterra", "alloy_furnace"), ALLOY_FURNACE_BLOCK);
        Registry.register(Registries.ITEM, Identifier.of("subterra", "alloy_furnace"), new BlockItem(ALLOY_FURNACE_BLOCK, new Item.Settings()));
    }
}
