package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.gui.screen.KonasBeaconGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class Class162
extends Class164 {
    public Potion Field1788;
    public int Field1789;
    public KonasBeaconGui Field1790;

    public Class162(KonasBeaconGui konasBeaconGui, int n, int n2, int n3, Potion potion, int n4) {
        this.Field1790 = konasBeaconGui;
        super(n, n2, n3, GuiContainer.INVENTORY_BACKGROUND, potion.getStatusIconIndex() % 8 * 18, 198 + potion.getStatusIconIndex() / 8 * 18);
        this.Field1788 = potion;
        this.Field1789 = n4;
    }

    public void drawButtonForegroundLayer(int n, int n2) {
        String string = I18n.format((String)this.Field1788.getName(), (Object[])new Object[0]);
        if (this.Field1789 >= 3 && this.Field1788 != MobEffects.REGENERATION) {
            string = string + " II";
        }
        this.Field1790.drawHoveringText(string, n, n2);
    }

    public static int Method1687(Class162 class162) {
        return class162.Field1789;
    }

    public static Potion Method1688(Class162 class162) {
        return class162.Field1788;
    }
}