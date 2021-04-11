package me.darki.konas.gui.hud.elements;

import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.mixin.mixins.IInventoryPlayer;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class557;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class Gapples
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    public Gapples() {
        super("Gapples", 100.0f, 250.0f, 5.0f, 10.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        int n = ((IInventoryPlayer) Gapples.mc.player.inventory).getAllInventories().stream().mapToInt(Gapples::Method1811).sum();
        String string = n + " Gapples";
        float f = Math.max(5.0f, Class557.Method800(string));
        this.Method2323(f + 1.0f);
        this.Method2319(Class557.Method799(string) + 1.0f);
        Class557.Method801(string, (float)((int)this.Method2320() + (int)this.Method2329()) - Class557.Method800(string), (int)this.Method2324(), this.textColor.getValue().Method774());
    }

    public static boolean Method1812(ItemStack itemStack) {
        return itemStack.getItem() == Items.GOLDEN_APPLE;
    }

    public static int Method1811(NonNullList nonNullList) {
        return nonNullList.stream().filter(Gapples::Method1812).mapToInt(ItemStack::func_190916_E).sum();
    }
}