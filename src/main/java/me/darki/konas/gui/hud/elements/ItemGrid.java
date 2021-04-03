package me.darki.konas.gui.hud.elements;

import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.mixin.mixins.IInventoryPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemGrid
extends Element {
    @Override
    public void onRender2D() {
        super.onRender2D();
        int n = ((IInventoryPlayer) ItemGrid.mc.player.inventory).getAllInventories().stream().mapToInt(ItemGrid::Method2003).sum();
        int n2 = ((IInventoryPlayer) ItemGrid.mc.player.inventory).getAllInventories().stream().mapToInt(ItemGrid::Method2004).sum();
        int n3 = ((IInventoryPlayer) ItemGrid.mc.player.inventory).getAllInventories().stream().mapToInt(ItemGrid::Method1811).sum();
        int n4 = ((IInventoryPlayer) ItemGrid.mc.player.inventory).getAllInventories().stream().mapToInt(ItemGrid::Method2007).sum();
        this.Method2008(new ItemStack(Items.END_CRYSTAL, n), (int)this.Method2320() + 2, (int)this.Method2324() + 2);
        this.Method2008(new ItemStack(Items.GOLDEN_APPLE, n2, 1), (int)this.Method2320() + 22, (int)this.Method2324() + 2);
        this.Method2008(new ItemStack(Items.TOTEM_OF_UNDYING, n3), (int)this.Method2320() + 2, (int)this.Method2324() + 22);
        this.Method2008(new ItemStack(Items.EXPERIENCE_BOTTLE, n4), (int)this.Method2320() + 22, (int)this.Method2324() + 22);
        RenderUtil2.Method1336(this.Method2320(), this.Method2324(), 21.0f, 21.0f, 1.0f, this.outline.getValue().Method774());
        RenderUtil2.Method1336(this.Method2320() + 21.0f, this.Method2324(), 21.0f, 21.0f, 1.0f, this.outline.getValue().Method774());
        RenderUtil2.Method1336(this.Method2320(), this.Method2324() + 21.0f, 21.0f, 21.0f, 1.0f, this.outline.getValue().Method774());
        RenderUtil2.Method1336(this.Method2320() + 21.0f, this.Method2324() + 21.0f, 21.0f, 21.0f, 1.0f, this.outline.getValue().Method774());
    }

    public ItemGrid() {
        super("ItemGrid", 350.0f, 300.0f, 42.0f, 42.0f);
    }

    public static int Method2003(NonNullList nonNullList) {
        return nonNullList.stream().filter(ItemGrid::Method2009).mapToInt(ItemStack::func_190916_E).sum();
    }

    public static int Method2004(NonNullList nonNullList) {
        return nonNullList.stream().filter(ItemGrid::Method2005).mapToInt(ItemStack::func_190916_E).sum();
    }

    public static boolean Method2005(ItemStack itemStack) {
        return itemStack.getItem() == Items.GOLDEN_APPLE;
    }

    public static int Method1811(NonNullList nonNullList) {
        return nonNullList.stream().filter(ItemGrid::Method2006).mapToInt(ItemStack::func_190916_E).sum();
    }

    public static boolean Method2006(ItemStack itemStack) {
        return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
    }

    public static int Method2007(NonNullList nonNullList) {
        return nonNullList.stream().filter(ItemGrid::Method1812).mapToInt(ItemStack::func_190916_E).sum();
    }

    public void Method2008(ItemStack itemStack, int n, int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2);
        mc.getRenderItem().renderItemOverlays(ItemGrid.mc.fontRenderer, itemStack, n, n2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
    }

    public static boolean Method1812(ItemStack itemStack) {
        return itemStack.getItem() == Items.EXPERIENCE_BOTTLE;
    }

    public static boolean Method2009(ItemStack itemStack) {
        return itemStack.getItem() == Items.END_CRYSTAL;
    }
}