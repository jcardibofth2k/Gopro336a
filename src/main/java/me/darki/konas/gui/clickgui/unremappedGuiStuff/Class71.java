package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.awt.Color;
import java.util.ArrayList;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.Class115;
import me.darki.konas.unremaped.Class557;
import me.darki.konas.unremaped.Class84;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.client.gui.Gui;

public class
Class71
extends Gui {
    public float Field537 = Class557.Method802() + 4;
    public ArrayList<Module> Field538;
    public float Field539 = this.Field537 + 1.0f;
    public int Field540 = 57;
    public float Field541 = this.Field539;
    public float Field542 = this.Field539;
    public Class84 Field543 = Class84.CATEGORY;
    public Category Field544 = Category.COMBAT;
    public int Field545 = 0;

    public void Method613() {
        if (this.Field538.size() != 0) {
            Class84 class84 = Class84.CATEGORY;
            for (Class84 class842 : Class84.Method460()) {
                if (class842.equals((Object)this.Field543)) {
                    this.Field543 = class84;
                    break;
                }
                class84 = class842;
            }
        }
    }

    public void Method614(float f, float f2) {
        this.Field538 = this.Method623(this.Field544, f, f2);
        if (this.Field543 != Class84.CATEGORY) {
            this.Method621(this.Field538, f, f2);
        } else {
            this.Field545 = 0;
        }
    }

    public void Method615() {
        block4: {
            block5: {
                if (this.Field543 != Class84.CATEGORY) break block5;
                Category category = Category.Method1764()[Category.Method1764().length - 1];
                for (Category category2 : Category.Method1764()) {
                    if (category2.equals((Object)this.Field544)) {
                        this.Field544 = category;
                        break block4;
                    }
                    category = category2;
                }
                break block4;
            }
            if (this.Field543 != Class84.MODULES || this.Field538.size() == 0) break block4;
            int n = this.Field538.size() - 1;
            for (Module module : this.Field538) {
                if (this.Field538.indexOf(module) == this.Field545) {
                    this.Field545 = n;
                    break;
                }
                n = this.Field538.indexOf(module);
            }
        }
    }

    public int Method616() {
        if (this.Field543 == Class84.MODULES) {
            return this.Field540;
        }
        return 61;
    }

    public void Method617(float f) {
        this.Field541 = f;
    }

    public float Method618() {
        return this.Field541;
    }

    public void Method619(int n) {
        this.Field540 = n;
    }

    public void Method620() {
        if (this.Field543 == Class84.CATEGORY) {
            int n = 0;
            for (Category category : Category.Method1764()) {
                if (category.equals((Object)this.Field544)) {
                    n = 1;
                    continue;
                }
                if (n != true) continue;
                this.Field544 = category;
                n = 2;
                break;
            }
            if (n == 1) {
                this.Field544 = Category.Method1764()[0];
            }
        } else if (this.Field543 == Class84.MODULES && this.Field538.size() != 0) {
            int n = 0;
            for (Module module : this.Field538) {
                if (this.Field538.indexOf(module) == this.Field545) {
                    n = 1;
                    continue;
                }
                if (n != true) continue;
                this.Field545 = this.Field538.indexOf(module);
                n = 2;
                break;
            }
            if (n == 1) {
                this.Field545 = 0;
            }
        }
    }

    public void Method621(ArrayList<Module> arrayList, float f, float f2) {
        this.Field539 = this.Field542;
        int[] nArray = new int[]{67};
        arrayList.forEach(arg_0 -> Class71.Method622(nArray, arg_0));
        for (Module module : arrayList) {
            int n = Color.WHITE.hashCode();
            RenderUtil2.Method1338(f + 61.0f, f2 + this.Field539, nArray[0], Class557.Method802() + 3, new Color(43, 43, 43, 200).hashCode());
            int n2 = 65;
            if (this.Field545 == arrayList.indexOf(module)) {
                RenderUtil2.Method1338(f + 61.0f, f2 + this.Field539, 2.0f, Class557.Method802() + 2, ((ColorValue) Class115.selected.getValue()).Method774());
                n2 = 67;
            }
            if (module.isEnabled()) {
                n = ((ColorValue)Class115.toggled.getValue()).Method774();
            }
            Class557.Method801(module.getName(), (int)f + n2, (int)f2 + (int)(this.Field539 + 2.0f), n);
            this.Field539 += (float)(Class557.Method802() + 3);
        }
        this.Method619(61 + nArray[0]);
        this.Method617(this.Field539);
    }

    public static void Method622(int[] nArray, Module module) {
        if (Class557.Method800(module.getName()) > (float)nArray[0]) {
            nArray[0] = (int)(Class557.Method800(module.getName()) + 6.0f);
        }
    }

    public ArrayList<Module> Method623(Category category, float f, float f2) {
        this.Field539 = 1.0f;
        RenderUtil2.Method1338(f + 2.0f, f2 + this.Field539 - 1.0f, 57.0f, 1.0f, new Color(43, 43, 43, 200).hashCode());
        for (Category category2 : Category.Method1764()) {
            int n = new Color(43, 43, 43, 200).hashCode();
            int n2 = 6;
            RenderUtil2.Method1338(f + 2.0f, f2 + this.Field539, 57.0f, Class557.Method802() + 3, n);
            if (category2.equals((Object)category)) {
                RenderUtil2.Method1338(f + 2.0f, f2 + this.Field539, 2.0f, Class557.Method802() + 2, ((ColorValue)Class115.selected.getValue()).Method774());
                n2 = 8;
                this.Field542 = this.Field539;
            }
            Class557.Method801(category2.toString().substring(0, 1).toUpperCase() + category2.toString().substring(1).toLowerCase(), (int)(f + (float)n2), (int)((float)((int)f2) + (this.Field539 + 2.0f)), category2.equals((Object)this.Field544) ? ((ColorValue)Class115.selected.getValue()).Method774() : Color.white.hashCode());
            this.Field539 += (float)(Class557.Method802() + 3);
        }
        this.Method617(this.Field537 + 1.0f + (float)((Class557.Method802() + 3) * Category.Method1764().length));
        return ModuleManager.getModulesByCategory(category);
    }

    public void Method121() {
        block3: {
            int n = 0;
            if (this.Field538.size() == 0) break block3;
            for (Class84 class84 : Class84.Method460()) {
                if (class84.equals((Object)this.Field543)) {
                    n = 1;
                    continue;
                }
                if (n != true) continue;
                this.Field543 = class84;
                n = 2;
                break;
            }
            if (n == 1) {
                this.Field538.get(this.Field545).toggle();
            }
        }
    }
}