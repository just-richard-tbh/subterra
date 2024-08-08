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
        boolean k;
        int l;
        if (((AlloyFurnaceScreenHandler)this.handler).isBurning()) {
            k = true;
            l = MathHelper.ceil(((AlloyFurnaceScreenHandler)this.handler).getFuelProgress() * 13.0F) + 1;
            drawContext.drawGuiTexture(this.LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - l, i + 56, j + 36 + 14 - l, 14, l);
        }

        k = true;
        l = MathHelper.ceil(((AlloyFurnaceScreenHandler)this.handler).getCookProgress() * 24.0F);
        drawContext.drawGuiTexture(this.BURN_PROGRESS_TEXTRUE, 24, 16, 0, 0, i + 79, j + 34, l, 16);
    }

    @Override
    protected void init() {
        super.init();
    }
}
