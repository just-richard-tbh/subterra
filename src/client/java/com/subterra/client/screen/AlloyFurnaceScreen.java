package com.subterra.client.screen;

import com.subterra.screen.AlloyFurnaceScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlloyFurnaceScreen extends HandledScreen<AlloyFurnaceScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("subterra", "textures/gui/container/alloy_furnace.png");

    public AlloyFurnaceScreen(AlloyFurnaceScreenHandler screenHandler, PlayerInventory inventory, Text title){
        super(screenHandler, inventory, title);
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();
    }

}
