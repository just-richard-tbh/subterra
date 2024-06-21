package com.subterra;

import com.subterra.block.ModBlocks;
import com.subterra.block.entity.ModBlockEntities;
//import com.subterra.recipe.ModRecipes;
import com.subterra.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subterra implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("subterra");
	public static final String MOD_ID = "subterra";

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntityTypes();
		ModScreenHandlers.registerModScreenHandlers();
		//ModRecipes.registerRecipes();

		Registry.register(Registries.ITEM_GROUP, Identifier.of("subterra", "subterra"),
				FabricItemGroup.builder().displayName(Text.translatable("itemgroup.subterra"))
						.icon(() -> new ItemStack(ModBlocks.ALLOY_FURNACE_BLOCK.asItem())).entries((displayContext, entries) -> {
							entries.add(ModBlocks.ALLOY_FURNACE_BLOCK);
						}).build());

		LOGGER.info("Hello Fabric world!");
	}
}