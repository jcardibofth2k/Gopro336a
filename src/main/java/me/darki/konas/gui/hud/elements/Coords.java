package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.Class557;
import me.darki.konas.command.Command;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.util.EnumFacing;

public class Coords
extends Element {
    public Setting<Boolean> freecamCoords = new Setting<>("FreecamCoords", true);
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    public String Method905(EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.SOUTH) {
            return "+Z";
        }
        if (enumFacing == EnumFacing.WEST) {
            return "-X";
        }
        if (enumFacing == EnumFacing.NORTH) {
            return "-Z";
        }
        if (enumFacing == EnumFacing.EAST) {
            return "+X";
        }
        return null;
    }

    public Coords() {
        super("Coords", 0.0f, 350.0f, 5.0f, 10.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        String string = Coords.mc.player.getHorizontalFacing().getName().substring(0, 1).toUpperCase() + Coords.mc.player.getHorizontalFacing().getName().substring(1) + Command.Field122 + "7 [" + Command.Field122 + "r" + this.Method905(Coords.mc.player.getHorizontalFacing()) + Command.Field122 + "7]";
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        double d = Double.parseDouble(decimalFormat.format(this.freecamCoords.getValue() != false ? Coords.mc.getRenderViewEntity().posX : Coords.mc.player.posX));
        double d2 = Double.parseDouble(decimalFormat.format(this.freecamCoords.getValue() != false ? Coords.mc.getRenderViewEntity().posY : Coords.mc.player.posY));
        double d3 = Double.parseDouble(decimalFormat.format(this.freecamCoords.getValue() != false ? Coords.mc.getRenderViewEntity().posZ : Coords.mc.player.posZ));
        double d4 = Double.parseDouble(decimalFormat.format(this.Method906(Coords.mc.player.posX)));
        double d5 = Double.parseDouble(decimalFormat.format(this.Method906(Coords.mc.player.posZ)));
        String string2 = Command.Field122 + "7XYZ" + Command.Field122 + "r " + d + ", " + d2 + ", " + d3 + Command.Field122 + "7 [" + Command.Field122 + "r" + d4 + ", " + d5 + Command.Field122 + "7]";
        float f = Math.max(Class557.Method800(string2), Class557.Method800(string));
        this.Method2323(f + 1.0f);
        this.Method2319(Class557.Method799(string) + Class557.Method799(string2) + 1.0f);
        Class557.Method801(string, (int)this.Method2320(), (int)this.Method2324(), this.textColor.getValue().Method774());
        Class557.Method801(string2, (int)this.Method2320(), (float)((int)this.Method2324()) + Class557.Method799(string), this.textColor.getValue().Method774());
    }

    public double Method906(double d) {
        boolean bl = Coords.mc.world.getBiome(Coords.mc.player.getPosition()).getBiomeName().equals("Hell");
        return bl ? d * 8.0 : d / 8.0;
    }
}