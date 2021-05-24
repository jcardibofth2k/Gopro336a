package me.darki.konas.unremaped;

import java.util.ArrayList;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class215;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.util.Class247;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class Class232
extends Class215 {
    public Category Field2542;
    public ArrayList<Class217> Field2543 = new ArrayList();
    public Module Field2544 = null;
    public Class272 Field2545;

    @Override
    public void Method485(float f, float f2) {
        super.Method485(f, f2);
        this.Field2543.forEach(this::Method2181);
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        if (this.Field2544 == null) {
            this.Field2544 = this.Field2543.get(0).Method534();
            KonasGlobals.INSTANCE.Field1131.Method1828().Method2170(this.Field2544);
        }
        if (this.Method501(n, n2)) {
            if (Field327 > 0) {
                if (this.Field2543.get(0).Method495() < 0.0f) {
                    this.Field2543.forEach(Class232::Method2180);
                }
            } else if (Field327 < 0 && this.Field2543.get(this.Field2543.size() - 1).Method492() + this.Field2543.get(this.Field2543.size() - 1).Method476() > this.Method492() + this.Method476()) {
                this.Field2543.forEach(Class232::Method2182);
            }
        }
        Class247.Method2043(this.Field2545, Field319);
        GL11.glEnable((int)3089);
        RenderUtil2.Method1339(new ScaledResolution(Minecraft.getMinecraft()), this.Method486(), this.Method492(), this.Method489(), this.Method476());
        this.Field2543.forEach(arg_0 -> this.Method2183(n, n2, f, arg_0));
        GL11.glDisable((int)3089);
    }

    public void Method2171() {
        if (this.Field2544 == null) {
            this.Field2544 = this.Field2543.get(0).Method534();
        }
        KonasGlobals.INSTANCE.Field1131.Method1828().Method2170(this.Field2544);
    }

    public Module Method2179() {
        return this.Field2544;
    }

    @Override
    public void Method499() {
        super.Method499();
        this.Field2545 = new Class272(this.Method486(), this.Method492(), this.Method489(), this.Method476());
    }

    public static void Method2180(Class217 class217) {
        class217.Method484(class217.Method495() + 20.0f);
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        for (Class217 class217 : this.Field2543) {
            if (!(class217.Method492() + class217.Method476() >= this.Method492()) || !(class217.Method492() <= this.Method492() + this.Method476()) || !class217.Method494(n, n2, n3)) continue;
            return true;
        }
        return super.Method494(n, n2, n3);
    }

    public void Method2181(Class217 class217) {
        class217.Method485(this.Method486(), this.Method492());
    }

    public static void Method2182(Class217 class217) {
        class217.Method484(class217.Method495() - 20.0f);
    }

    public Class232(Category category, float f, float f2, float f3, float f4, float f5, float f6) {
        super(category.name(), f, f2, f3, f4, f5, f6);
        this.Field2542 = category;
        int n = 0;
        for (Module module : ModuleManager.getModules()) {
            if (module.getCategory() != this.Field2542) continue;
            this.Field2543.add(new Class217(module, this.Method486(), this.Method492(), 0.0f, (float)n, f5, 32.0f));
            n += 40;
        }
    }

    public void Method2170(Module module) {
        this.Field2544 = module;
        KonasGlobals.INSTANCE.Field1131.Method1828().Method2170(module);
    }

    public void Method2183(int n, int n2, float f, Class217 class217) {
        block0: {
            if (!(class217.Method492() + class217.Method476() >= this.Method492()) || !(class217.Method492() <= this.Method492() + this.Method476())) break block0;
            class217.Method497(n, n2, f);
        }
    }
}