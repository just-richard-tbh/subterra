package com.subterra.client;


import com.subterra.client.screen.AlloyFurnaceScreen;
import com.subterra.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

import net.fabricmc.api.ClientModInitializer;

public class SubterraClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.ALLOY_FURNACE_SCREEN_HANDLER, AlloyFurnaceScreen::new);
	}
}
