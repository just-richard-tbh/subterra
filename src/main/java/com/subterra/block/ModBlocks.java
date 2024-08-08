package com.subterra.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
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
                    luminance(createLightLevelFromLitBlockState(13)).
                    sounds(BlockSoundGroup.DEEPSLATE_TILES)
    );
    public static final Block CRYSLATE_BLOCK = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(4.0F).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final GrapeBushBlock GRAPE_BUSH = new GrapeBushBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.LICHEN_GREEN).
            strength(0.5F).
            sounds(BlockSoundGroup.AZALEA).
            nonOpaque().
            noCollision()
    );
    public static final SubterraPortalBlock SUBTERRA_PORTAL = new SubterraPortalBlock(AbstractBlock.Settings.create().
            strength(32767.0F).
            sounds(BlockSoundGroup.GLASS).
            nonOpaque().
            luminance(state -> 8).
            noCollision()
    );

    public static void registerModBlocks(){
        Registry.register(Registries.BLOCK, Identifier.of("subterra", "alloy_furnace"), ALLOY_FURNACE_BLOCK);
        Registry.register(Registries.ITEM, Identifier.of("subterra", "alloy_furnace"), new BlockItem(ALLOY_FURNACE_BLOCK, new Item.Settings()));
        Registry.register(Registries.BLOCK, Identifier.of("subterra", "cryslate"), CRYSLATE_BLOCK);
        Registry.register(Registries.ITEM, Identifier.of("subterra", "cryslate"), new BlockItem(CRYSLATE_BLOCK, new Item.Settings()));
        Registry.register(Registries.BLOCK, Identifier.of("subterra", "grape_bush"), GRAPE_BUSH);
        Registry.register(Registries.ITEM, Identifier.of("subterra", "grape_bush"), new BlockItem(GRAPE_BUSH, new Item.Settings()));
        Registry.register(Registries.BLOCK, Identifier.of("subterra", "subterra_portal"), SUBTERRA_PORTAL);
    }
}
