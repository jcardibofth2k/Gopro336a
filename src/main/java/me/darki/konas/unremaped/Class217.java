package me.darki.konas.unremaped;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class215;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGui;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.util.Class247;

public class Class217
extends Class215 {
    public Module Field390;

    public Module Method534() {
        return this.Field390;
    }

    public Class217(Module module, float f, float f2, float f3, float f4, float f5, float f6) {
        super(module.getName(), f, f2, f3, f4, f5, f6);
        this.Field390 = module;
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        block2: {
            if (!this.Method501(n, n2)) break block2;
            if (n3 == 0) {
                this.Field390.toggle();
                return true;
            }
            if (n3 == 1) {
                KonasGlobals.INSTANCE.Field1131.Method1828().Method2175().get((Object)this.Field390.getCategory()).Method2170(this.Field390);
            }
        }
        return super.Method494(n, n2, n3);
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class272 class272 = new Class272(this.Method486(), this.Method492(), this.Method489(), this.Method476());
        Class272 class2722 = new Class272(this.Method486(), this.Method492(), 4.0f, this.Method476());
        if (this.Field390.isEnabled()) {
            Class247.Method2043(class272, this.Method501(n, n2) ? Field321.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field321);
            Class247.Method2042(class272, Field317, 1.0f);
        } else if (this.Method501(n, n2)) {
            Class247.Method2043(class272, Field319.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()));
        }
        if (KonasGlobals.INSTANCE.Field1131.Method1828().Method2175().get((Object)this.Field390.getCategory()).Method2179() != null && KonasGlobals.INSTANCE.Field1131.Method1828().Method2175().get((Object)this.Field390.getCategory()).Method2179().equals(this.Field390)) {
            Class247.Method2043(class2722, Field315);
            Class247.Method2042(class2722, Field317, 1.0f);
        }
        Class247.cfontRenderer.Method863(this.Method483(), this.Method486() + 12.0f, (int)(this.Method492() + this.Method476() / 2.0f - Class247.cfontRenderer.Method831(this.Method483()) / 2.0f), Field324);
    }
}