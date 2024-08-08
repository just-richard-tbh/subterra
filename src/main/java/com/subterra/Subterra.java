package com.subterra;

import com.subterra.block.ModBlocks;
import com.subterra.block.entity.ModBlockEntities;
import com.subterra.item.ModItems;
import com.subterra.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subterra implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("subterra");
	public static final String MOD_ID = "subterra";

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntityTypes();
		ModScreenHandlers.registerModScreenHandlers();
		ModItems.registerModItems();

		Registry.register(Registries.ITEM_GROUP, Identifier.of("subterra", "subterra"),
				FabricItemGroup.builder().displayName(Text.translatable("itemgroup.subterra"))
						.icon(() -> new ItemStack(ModBlocks.ALLOY_FURNACE_BLOCK.asItem())).entries((displayContext, entries) -> {
							entries.add(ModBlocks.ALLOY_FURNACE_BLOCK);
							entries.add(ModItems.STEEL_INGOT);
							entries.add(ModItems.GRAPES);
						}).build());

		LOGGER.info("Hello Fabric world!");
	}
}