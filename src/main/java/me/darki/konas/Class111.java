package me.darki.konas;

import java.awt.Color;
import me.darki.konas.Class114;
import me.darki.konas.Class516;
import me.darki.konas.Class556;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Class111
extends Element {
    public Setting<Class114> Field2541 = new Setting<>("Background", Class114.TEXTURE);

    @Override
    public void onRender2D() {
        int n;
        int n2;
        int n3;
        int n4;
        super.onRender2D();
        if (this.Field2541.getValue() == Class114.TEXTURE) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
            Class111.mc.ingameGUI.drawTexturedModalRect(this.Method2320(), this.Method2324(), 7, 17, 162, 54);
        }
        GlStateManager.clear(256);
        for (n4 = 0; n4 < 3; ++n4) {
            for (n3 = 0; n3 < 9; ++n3) {
                n2 = (int)(this.Method2320() + 1.0f + (float)(n3 * 18));
                n = (int)(this.Method2324() + 1.0f + (float)(n4 * 18));
                if (this.Field2541.getValue() != Class114.OUTLINE) continue;
                Gui.drawRect(n2 - 1, n - 1, n2 - 1 + 18, n - 1 + 18, new Color(26, 26, 26, 40).hashCode());
                Class516.Method1278(n2 - 1, n - 1, n2 - 1 + 18, n - 1 + 18, 2.0f, Class556.Method807(300, new float[]{1.0f, 1.0f, 1.0f}));
            }
        }
        n4 = Class111.mc.player.inventory.mainInventory.size();
        for (n3 = 9; n3 < n4; ++n3) {
            n2 = (int)(this.Method2320() + 1.0f + (float)(n3 % 9 * 18));
            n = (int)(this.Method2324() + 1.0f + (float)((n3 / 9 - 1) * 18));
            GlStateManager.pushMatrix();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(Class111.mc.player.inventory.mainInventory.get(n3), n2, n);
            mc.getRenderItem().renderItemOverlays(Class111.mc.fontRenderer, Class111.mc.player.inventory.mainInventory.get(n3), n2, n);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableDepth();
            GlStateManager.popMatrix();
        }
    }

    public Class111() {
        super("Inventory", 400.0f, 400.0f, 162.0f, 54.0f);
    }
}
