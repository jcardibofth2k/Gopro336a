package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.Class557;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class ChestCount
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    public ChestCount() {
        super("ChestCount", 150.0f, 250.0f, 5.0f, 10.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        String string = ChestCount.mc.world.loadedTileEntityList.stream().filter(ChestCount::Method936).count() + " chests";
        float f = Math.max(5.0f, Class557.Method800(string));
        this.Method2323(f + 1.0f);
        this.Method2319(Class557.Method799(string) + 1.0f);
        Class557.Method801(string, (float)((int)this.Method2320() + (int)this.Method2329()) - Class557.Method800(string), (int)this.Method2324(), this.textColor.getValue().Method774());
    }

    public static boolean Method936(TileEntity tileEntity) {
        return tileEntity instanceof TileEntityChest;
    }
}