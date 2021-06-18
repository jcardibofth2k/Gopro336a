package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.util.HashMap;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGui;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.Class247;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.StringUtils;

public class Class230
extends Class215 {
    public Category Field2514 = Category.COMBAT;
    public Class236 Field2515;
    public Class224 Field2516 = null;
    public Class270 Field2517;
    public Class270 Field2518;
    public Class272 Field2519;
    public Class270 Field2520;
    public Class270 Field2521;
    public boolean Field2522 = false;
    public String Field2523;
    public TimerUtil Field2524 = new TimerUtil();
    public int Field2525 = (Boolean) KonasGui.singleColumn.getValue() != false ? 65 : 75;
    public int Field2526 = (Boolean) KonasGui.singleColumn.getValue() != false ? 16 : 56;
    public Class213[] Field2527 = new Class213[]{new Class213(Category.COMBAT, this.Method486(), this.Method492(), this.Field2526, 8.0f, this.Field2525, 38.0f, 1), new Class213(Category.MOVEMENT, this.Method486(), this.Method492(), this.Field2526 + this.Field2525, 8.0f, this.Field2525, 38.0f, 0), new Class213(Category.PLAYER, this.Method486(), this.Method492(), this.Field2526 + this.Field2525 * 2, 8.0f, this.Field2525, 38.0f, 0), new Class213(Category.RENDER, this.Method486(), this.Method492(), this.Field2526 + this.Field2525 * 3, 8.0f, this.Field2525, 38.0f, 0), new Class213(Category.MISC, this.Method486(), this.Method492(), this.Field2526 + this.Field2525 * 4, 8.0f, this.Field2525, 38.0f, 0), new Class213(Category.EXPLOIT, this.Method486(), this.Method492(), this.Field2526 + this.Field2525 * 5, 8.0f, this.Field2525, 38.0f, 0), new Class213(Category.CLIENT, this.Method486(), this.Method492(), this.Field2526 + this.Field2525 * 6, 8.0f, this.Field2525, 38.0f, 2)};
    public HashMap<Category, Class232> Field2528 = new HashMap();

    @Override
    public boolean Method491(int n, int n2, int n3, long l) {
        if (this.Field2515.Method491(n, n2, n3, l)) {
            return true;
        }
        return super.Method491(n, n2, n3, l);
    }

    @Override
    public void Method481(int n, int n2, int n3) {
        super.Method481(n, n2, n3);
        this.Field2515.Method481(n, n2, n3);
    }

    @Override
    public void Method485(float f, float f2) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.Method496((int)((float)scaledResolution.getScaledWidth() / 2.0f - this.Method489() / 2.0f));
        this.Method484((int)((float)scaledResolution.getScaledHeight() / 2.0f - this.Method476() / 2.0f));
        for (Class213 class213 : this.Field2527) {
            class213.Method485(this.Method486(), this.Method492());
        }
        if (this.Field2516 != null) {
            this.Field2516.Method485(this.Method486(), this.Method492());
        }
        this.Field2528.forEach(this::Method2172);
        if (this.Field2515 != null) {
            this.Field2515.Method485(this.Method486(), this.Method492());
        }
        super.Method485(f, f2);
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (Class230.Method487(n, n2, this.Method486() + 594.0f, this.Method492() + 14.0f, 192.0, 20.0) && !((Boolean) KonasGui.singleColumn.getValue()).booleanValue()) {
            this.Field2522 = true;
            this.Field2523 = "";
            return true;
        }
        this.Field2522 = false;
        for (Class213 class213 : this.Field2527) {
            if (!class213.Method494(n, n2, n3)) continue;
            return true;
        }
        if (this.Field2516 != null ? this.Field2516.Method494(n, n2, n3) : this.Field2528.get((Object)this.Field2514).Method494(n, n2, n3)) {
            return true;
        }
        if (this.Field2515.Method494(n, n2, n3)) {
            return true;
        }
        return super.Method494(n, n2, n3);
    }

    public void Method2170(Module module) {
        this.Field2515 = new Class236(module, this.Method486(), this.Method492(), 160.0f, 48.0f, (Boolean) KonasGui.singleColumn.getValue() != false ? 328.0f : 640.0f, this.Method476() - 72.0f);
    }

    public static void Method2171() {
        Class230.Method500();
    }

    public void Method2172(Category category, Class232 class232) {
        class232.Method485(this.Method486(), this.Method492());
    }

    public Category Method2173() {
        return this.Field2514;
    }

    public void Method2174(Category category) {
        this.Field2516 = null;
        this.Field2514 = category;
        this.Field2528.get((Object)category).Method2171();
    }

    @Override
    public void Method479(char c, int n) {
        block2: {
            block0: {
                block3: {
                    block1: {
                        super.Method479(c, n);
                        if (!this.Field2522) break block0;
                        if (n != 28) break block1;
                        this.Field2522 = false;
                        this.Field2516 = new Class224(this.Field2523, this.Method486(), this.Method492(), 0.0f, 48.0f, 160.0f, this.Method476() - 72.0f);
                        this.Field2516.Method485(this.Method486(), this.Method492());
                        break block2;
                    }
                    if (n != 14) break block3;
                    this.Field2523 = StringUtils.chop(this.Field2523);
                    break block2;
                }
                if (c < 'A' || c > 'z') break block2;
                this.Field2523 = this.Field2523 + c;
                break block2;
            }
            this.Field2515.Method479(c, n);
        }
    }

    public Class230(float f, float f2) {
        super("Konas", 0.0f, 0.0f, 0.0f, 0.0f, f, f2);
        for (Category category : Category.Method1764()) {
            this.Field2528.put(category, new Class232(category, this.Method486(), this.Method492(), 0.0f, 48.0f, 160.0f, this.Method476() - 72.0f));
        }
    }

    @Override
    public void Method499() {
        this.Field2517 = new Class270(this.Method486(), this.Method492(), this.Method489(), this.Method476(), 3.0f, 25, 15);
        this.Field2518 = new Class270(this.Method486(), this.Method492(), this.Method489(), 46.0f, 3.0f, 25, 3);
        this.Field2519 = new Class272(this.Method486(), this.Method492() + 46.0f, this.Method489(), 2.0f);
        this.Field2520 = new Class270(this.Method486(), this.Method492() + this.Method476() - 24.0f, this.Method489(), 24.0f, 3.0f, 25, 12);
        this.Field2521 = new Class270(this.Method486() + 594.0f, this.Method492() + 14.0f, 192.0f, 20.0f, 3.0f);
    }

    public HashMap<Category, Class232> Method2175() {
        return this.Field2528;
    }

    public Class236 Method2176() {
        return this.Field2515;
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class247.Method2043(this.Field2517, Field318);
        Class247.Method2043(this.Field2518, Field314);
        Class247.Method2043(this.Field2520, Field314);
        Class247.Method2043(this.Field2519, Field315);
        if (!((Boolean) KonasGui.singleColumn.getValue()).booleanValue()) {
            Class247.Method2043(this.Field2521, Field323);
        }
        Class247.Method2042(this.Field2517, Field317, 1.0f);
        if (this.Field2522 || this.Field2516 != null) {
            Class247.cfontRenderer.Method863(this.Field2523, this.Method486() + 599.0f, (int)(this.Method492() + 24.0f - Class247.cfontRenderer.Method831(this.Field2523) / 2.0f), Field325);
            if (this.Field2524.GetDifferenceTiming(500.0)) {
                Class272 object = new Class272(this.Method486() + 599.0f + (this.Field2523.equals("") ? 0.0f : Class247.cfontRenderer.Method830(this.Field2523)) + 1.0f, this.Method492() + 24.0f - (this.Field2523.equals("") ? Class247.cfontRenderer.Method831("Search features") : Class247.cfontRenderer.Method831(this.Field2523)) / 2.0f, 1.0f, this.Field2523.equals("") ? Class247.cfontRenderer.Method831("Search features") : Class247.cfontRenderer.Method831(this.Field2523));
                Class247.Method2043(object, Field325);
                if (this.Field2524.GetDifferenceTiming(1000.0)) {
                    this.Field2524.UpdateCurrentTime();
                }
            }
        } else if (!((Boolean) KonasGui.singleColumn.getValue()).booleanValue()) {
            Class247.cfontRenderer.Method863("Search features", this.Method486() + 599.0f, (int)(this.Method492() + 24.0f - Class247.cfontRenderer.Method831("Search features") / 2.0f), Field325);
        }
        for (Class213 class213 : this.Field2527) {
            class213.Method497(n, n2, f);
        }
        if (this.Field2516 != null) {
            this.Field2516.Method497(n, n2, f);
        } else {
            this.Field2528.get((Object)this.Field2514).Method497(n, n2, f);
        }
        this.Field2515.Method497(n, n2, f);
        Class247.cfontRenderer.Method863("0.10.2 for Minecraft 1.12.2", this.Method486() + 8.0f, (int)(this.Method492() + this.Method476() - 12.0f - Class247.cfontRenderer.Method831("konasclient.com") / 2.0f), Field324);
        Class247.cfontRenderer.Method863("konasclient.com", this.Method486() + this.Method489() - 8.0f - Class247.cfontRenderer.Method830("konasclient.com"), (int)(this.Method492() + this.Method476() - 12.0f - Class247.cfontRenderer.Method831("konasclient.com") / 2.0f), Field324);
    }

    @Override
    public void Method477(int n, int n2, float f) {
        super.Method477(n, n2, f);
        this.Field2515.Method477(n, n2, f);
    }
}