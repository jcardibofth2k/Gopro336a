package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.util.ArrayList;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.KonasGui;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class Class236
extends Class215 {
    public Module Field2505;
    public ArrayList<Class234> Field2506 = new ArrayList();

    @Override
    public void Method481(int n, int n2, int n3) {
        super.Method481(n, n2, n3);
        for (Class234 class234 : this.Field2506) {
            class234.Method481(n, n2, n3);
        }
    }

    public void Method2159(Class234 class234) {
        class234.Method485(this.Method486(), this.Method492());
    }

    @Override
    public void Method477(int n, int n2, float f) {
        super.Method477(n, n2, f);
        this.Field2506.forEach(arg_0 -> Class236.Method2160(n, n2, f, arg_0));
    }

    @Override
    public void Method485(float f, float f2) {
        super.Method485(f, f2);
        this.Field2506.forEach(this::Method2159);
    }

    public Class236(Module module, float f, float f2, float f3, float f4, float f5, float f6) {
        super(module.getName(), f, f2, f3, f4, f5, f6);
        this.Field2505 = module;
        boolean bl = (Boolean) KonasGui.Field109.getValue() == false;
        float f7 = 16.0f;
        float f8 = 16.0f;
        this.Field2506.add(new Class234("General", module, null, this.Method486(), this.Method492(), 16.0f, f7, 296.0f, false));
        f7 += 16.0f + this.Field2506.get(0).Method476();
        for (Setting setting : ModuleManager.Method1615(module)) {
            if (!(setting.getValue() instanceof ParentSetting)) continue;
            if (bl && f7 < f8) {
                bl = false;
            }
            Class234 class234 = new Class234(setting.Method1183(), module, (ParentSetting)setting.getValue(), this.Method486(), this.Method492(), bl ? 328.0f : 16.0f, bl ? f8 : f7, 296.0f, bl);
            this.Field2506.add(class234);
            if (bl) {
                f8 += 16.0f + class234.Method476();
            } else {
                f7 += 16.0f + class234.Method476();
            }
            if (((Boolean) KonasGui.Field109.getValue()).booleanValue()) {
                bl = false;
                continue;
            }
            bl = !bl;
        }
    }

    public static void Method2160(int n, int n2, float f, Class234 class234) {
        class234.Method477(n, n2, f);
    }

    @Override
    public boolean Method491(int n, int n2, int n3, long l) {
        if (super.Method491(n, n2, n3, l)) {
            return true;
        }
        for (Class234 class234 : this.Field2506) {
            if (!class234.Method491(n, n2, n3, l)) continue;
            return true;
        }
        return false;
    }

    public static void Method2161(char c, int n, Class234 class234) {
        class234.Method479(c, n);
    }

    public static void Method2162(Class234 class234) {
        class234.Method484(class234.Method495() - 40.0f);
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (super.Method494(n, n2, n3)) {
            return true;
        }
        for (Class234 class234 : this.Field2506) {
            if (!class234.Method494(n, n2, n3)) continue;
            return true;
        }
        return false;
    }

    public static void Method2163(Class234 class234) {
        class234.Method484(class234.Method495() + 40.0f);
    }

    @Override
    public void Method479(char c, int n) {
        super.Method479(c, n);
        this.Field2506.forEach(arg_0 -> Class236.Method2161(c, n, arg_0));
    }

    @Override
    public void Method497(int n, int n2, float f) {
        block0: {
            block1: {
                super.Method497(n, n2, f);
                GL11.glEnable((int)3089);
                RenderUtil2.Method1339(new ScaledResolution(Minecraft.getMinecraft()), this.Method486(), this.Method492(), this.Method489(), this.Method476());
                this.Field2506.forEach(arg_0 -> Class236.Method2164(n, n2, f, arg_0));
                GL11.glDisable((int)3089);
                if (!this.Method501(n, n2)) break block0;
                if (Field327 <= 0) break block1;
                if (!(this.Field2506.get(0).Method495() < 0.0f)) break block0;
                this.Field2506.forEach(Class236::Method2163);
                break block0;
            }
            if (Field327 >= 0 || !(this.Field2506.get(this.Field2506.size() - 1).Method492() + this.Field2506.get(this.Field2506.size() - 1).Method476() > this.Method492() + this.Method476()) && (this.Field2506.size() <= 1 || !(this.Field2506.get(this.Field2506.size() - 2).Method492() + this.Field2506.get(this.Field2506.size() - 2).Method476() > this.Method492() + this.Method476()))) break block0;
            this.Field2506.forEach(Class236::Method2162);
        }
    }

    public static void Method2164(int n, int n2, float f, Class234 class234) {
        class234.Method497(n, n2, f);
    }
}