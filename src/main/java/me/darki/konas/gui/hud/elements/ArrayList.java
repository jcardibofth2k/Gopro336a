package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.util.Comparator;
import me.darki.konas.Class107;
import me.darki.konas.Class167;
import me.darki.konas.ColorValue;
import me.darki.konas.RenderUtil2;
import me.darki.konas.Class556;
import me.darki.konas.Class557;
import me.darki.konas.command.Command;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ArrayList
extends Element {
    public Setting<Boolean> Field841 = new Setting<>("Lines", true);
    public Setting<Boolean> Field842 = new Setting<>("Cute", false);
    public Setting<ColorValue> Field843 = new Setting<>("LineColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), true)).Method1191(this::Method901);
    public Setting<Boolean> Field844 = new Setting<>("Pulse", true);
    public Setting<Float> Field845 = new Setting<>("Range", Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).Method1191(this::Method903);
    public Setting<Float> Field846 = new Setting<>("Spread", Float.valueOf(1.0f), Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).Method1191(this::Method900);
    public Setting<Float> Field847 = new Setting<>("Speed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).Method1191(this::Method904);
    public float Field848 = 0.0f;
    public Class107 Field849 = Class107.TOP_RIGHT;

    public String Method894(Module module) {
        if (module.Method756() != null) {
            return module.getName() + Command.Field122 + "7 " + module.Method756();
        }
        return module.getName();
    }

    public void Method895(boolean bl, int[] nArray, int[] nArray2, Module module) {
        float f = Class557.Method800(module.Method756() != null ? module.getName() + " " + module.Method756() : module.getName());
        String string = this.Method894(module);
        RenderUtil2.Method1338(this.Method2320() + (bl ? this.Method2329() - f - 2.0f : 0.0f), this.Method2324() + (float)nArray[0], f + 2.0f, (int)(Class557.Method799(string) + 1.5f), new Color(20, 20, 20, 60).hashCode());
        int n = this.Method902(nArray2[0]);
        if (this.Field841.getValue().booleanValue()) {
            RenderUtil2.Method1338(this.Method2320() + (bl ? this.Method2329() - f - 2.0f : f + 2.0f), this.Method2324() + (float)nArray[0], 1.0f, (int)(Class557.Method799(string) + 1.5f), n);
        }
        Class557.Method801(string, (int)((float)((int)this.Method2320()) + (bl ? this.Method2329() - f : 0.0f)), (int)(this.Method2324() + (float)nArray[0] + 0.5f), module.Method1635() ? n : Color.GRAY.getRGB());
        nArray[0] = nArray[0] + (int)(Class557.Method799(string) + 1.5f);
        nArray2[0] = nArray2[0] + 1;
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        this.Method899();
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{1};
        boolean bl = this.Field849 == Class107.TOP_LEFT || this.Field849 == Class107.TOP_RIGHT;
        boolean bl2 = this.Field849 == Class107.BOTTOM_RIGHT || this.Field849 == Class107.TOP_RIGHT;
        java.util.ArrayList<Module> arrayList = Class167.Method1613();
        this.Field848 = (float)arrayList.stream().mapToDouble(ArrayList::Method897).max().orElse(0.0);
        this.Method2323(this.Field848);
        arrayList.stream().sorted(Comparator.comparingInt(arg_0 -> ArrayList.Method898(bl, arg_0))).forEach(arg_0 -> this.Method895(bl2, nArray, nArray2, arg_0));
        this.Method2319(nArray[0]);
    }

    public int Method896(int n) {
        int n2 = Class167.Method1611().size();
        int n3 = new Color(91, 206, 250).getRGB();
        int n4 = Color.WHITE.getRGB();
        int n5 = new Color(245, 169, 184).getRGB();
        int n6 = n2 / 5;
        if (n < n6) {
            return n3;
        }
        if (n < n6 * 2) {
            return n5;
        }
        if (n < n6 * 3) {
            return n4;
        }
        if (n < n6 * 4) {
            return n5;
        }
        if (n < n6 * 5) {
            return n3;
        }
        return n3;
    }

    public static double Method897(Module module) {
        return Class557.Method800(module.Method756() != null ? module.getName() + " " + module.Method756() : module.getName());
    }

    public static int Method898(boolean bl, Module module) {
        return bl ? -((int)Class557.Method800(module.Method756() != null ? module.getName() + " " + module.Method756() : module.getName())) : (int)Class557.Method800(module.Method756() != null ? module.getName() + " " + module.Method756() : module.getName());
    }

    public void Method899() {
        ScaledResolution scaledResolution;
        float f = this.Method2320() + this.Method2329() / 2.0f;
        float f2 = this.Method2324() + this.Method2322() / 2.0f;
        if (f2 >= (float)(scaledResolution = new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight() / 2.0f && f >= (float)scaledResolution.getScaledWidth() / 2.0f) {
            this.Field849 = Class107.BOTTOM_RIGHT;
        } else if (f2 >= (float)scaledResolution.getScaledHeight() / 2.0f && f <= (float)scaledResolution.getScaledWidth() / 2.0f) {
            this.Field849 = Class107.BOTTOM_LEFT;
        } else if (f2 <= (float)scaledResolution.getScaledHeight() / 2.0f && f >= (float)scaledResolution.getScaledWidth() / 2.0f) {
            this.Field849 = Class107.TOP_RIGHT;
        } else if (f2 <= (float)scaledResolution.getScaledHeight() / 2.0f && f <= (float)scaledResolution.getScaledWidth() / 2.0f) {
            this.Field849 = Class107.TOP_LEFT;
        }
    }

    public boolean Method900() {
        return this.Field844.getValue() != false && !this.Field843.getValue().Method783();
    }

    public ArrayList() {
        super("ArrayList", 100.0f, 100.0f, 100.0f, 100.0f);
    }

    public boolean Method901() {
        return this.Field842.getValue() == false;
    }

    public int Method902(int n) {
        float[] fArray = Color.RGBtoHSB(this.Field843.getValue().Method778() >> 16 & 0xFF, this.Field843.getValue().Method778() >> 8 & 0xFF, this.Field843.getValue().Method778() & 0xFF, null);
        if (this.Field842.getValue().booleanValue()) {
            return this.Method896(n - 1);
        }
        if (this.Field844.getValue().booleanValue()) {
            if (this.Field843.getValue().Method783()) {
                return Class556.Method807(300 * n, fArray);
            }
            return Class556.Method805(n, fArray, this.Field846.getValue().floatValue(), this.Field847.getValue().floatValue(), this.Field845.getValue().floatValue());
        }
        return this.Field843.getValue().Method774();
    }

    public boolean Method903() {
        return this.Field844.getValue() != false && !this.Field843.getValue().Method783();
    }

    public boolean Method904() {
        return this.Field844.getValue() != false && !this.Field843.getValue().Method783();
    }
}
