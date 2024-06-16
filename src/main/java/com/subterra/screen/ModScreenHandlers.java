package com.subterra.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<AlloyFurnaceScreenHandler> ALLOY_FURNACE_SCREEN_HANDLER;
    public static void registerModScreenHandlers(){
        ALLOY_FURNACE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of("subterra", "alloy_furnace"), new ScreenHandlerType<>(AlloyFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    }
}
