package com.subterra.item;

import com.subterra.block.ModBlocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item STEEL_INGOT = new Item(new Item.Settings());
    public static final Item GRAPES = new AliasedBlockItem(ModBlocks.GRAPE_BUSH,new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static void registerModItems(){
        Registry.register(Registries.ITEM, Identifier.of("subterra", "steel_ingot"), STEEL_INGOT);
        Registry.register(Registries.ITEM, Identifier.of("subterra", "grapes"), GRAPES);
    }
}
