package com.subterra.block.entity;

import com.subterra.Subterra;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.subterra.block.ModBlocks.ALLOY_FURNACE_BLOCK;

public class ModBlockEntities {
    public static BlockEntityType<AlloyFurnaceBlockEntity> ALLOY_FURNACE_BLOCK_ENTITY;
    public static void registerBlockEntityTypes() {
        ALLOY_FURNACE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, Identifier.of(Subterra.MOD_ID, "alloy_furnace_be"),
                BlockEntityType.Builder.create(AlloyFurnaceBlockEntity::new, ALLOY_FURNACE_BLOCK).build()
        );

    }
}
