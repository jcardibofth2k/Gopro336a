package me.darki.konas.unremaped;

import java.awt.Color;

import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.RainbowUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Inventory
extends Element {
    public Setting<Class114> background = new Setting<>("Background", Class114.TEXTURE);

    @Override
    public void onRender2D() {
        int n;
        int n2;
        int n3;
        int n4;
        super.onRender2D();
        if (this.background.getValue() == Class114.TEXTURE) {
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
            Inventory.mc.ingameGUI.drawTexturedModalRect(this.Method2320(), this.Method2324(), 7, 17, 162, 54);
        }
        GlStateManager.clear((int)256);
        for (n4 = 0; n4 < 3; ++n4) {
            for (n3 = 0; n3 < 9; ++n3) {
                n2 = (int)(this.Method2320() + 1.0f + (float)(n3 * 18));
                n = (int)(this.Method2324() + 1.0f + (float)(n4 * 18));
                if (this.background.getValue() != Class114.OUTLINE) continue;
                Gui.drawRect((int)(n2 - 1), (int)(n - 1), (int)(n2 - 1 + 18), (int)(n - 1 + 18), (int)new Color(26, 26, 26, 40).hashCode());
                Class516.Method1278(n2 - 1, n - 1, n2 - 1 + 18, n - 1 + 18, 2.0f, RainbowUtil.Method807(300, new float[]{1.0f, 1.0f, 1.0f}));
            }
        }
        n4 = Inventory.mc.player.inventory.mainInventory.size();
        for (n3 = 9; n3 < n4; ++n3) {
            n2 = (int)(this.Method2320() + 1.0f + (float)(n3 % 9 * 18));
            n = (int)(this.Method2324() + 1.0f + (float)((n3 / 9 - 1) * 18));
            GlStateManager.pushMatrix();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack) Inventory.mc.player.inventory.mainInventory.get(n3), n2, n);
            mc.getRenderItem().renderItemOverlays(Inventory.mc.fontRenderer, (ItemStack) Inventory.mc.player.inventory.mainInventory.get(n3), n2, n);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableDepth();
            GlStateManager.popMatrix();
        }
    }

    public Inventory() {
        super("Inventory", 400.0f, 400.0f, 162.0f, 54.0f);
    }
}