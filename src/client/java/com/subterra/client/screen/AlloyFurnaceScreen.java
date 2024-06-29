package com.subterra.client.screen;

import com.subterra.screen.AlloyFurnaceScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AlloyFurnaceScreen extends HandledScreen<AlloyFurnaceScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("subterra", "textures/gui/container/alloy_furnace.png");
    private final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/blast_furnace/lit_progress");
    private final Identifier BURN_PROGRESS_TEXTRUE = Identifier.ofVanilla("container/blast_furnace/burn_progress");

    public AlloyFurnaceScreen(AlloyFurnaceScreenHandler screenHandler, PlayerInventory inventory, Text title){
        super(screenHandler, inventory, title);
    }

    public void handledScreenTick(){
        super.handledScreenTick();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        drawContext.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();
    }
}
