package me.darki.konas.unremaped;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class215;
import me.darki.konas.module.Category;
import me.darki.konas.module.client.KonasGui;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.util.Class247;

public class Class213
extends Class215 {
    public Category Field491;
    public int Field492;
    public Class270 Field493;
    public Class270 Field494;

    public Class213(Category category, float f, float f2, float f3, float f4, float f5, float f6, int n) {
        super(category.name(), f, f2, f3, f4, f5, f6);
        this.Field491 = category;
        this.Field492 = n;
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (this.Method501(n, n2)) {
            KonasGlobals.INSTANCE.Field1131.Method1828().Method2174(this.Field491);
            return true;
        }
        return super.Method494(n, n2, n3);
    }

    @Override
    public void Method499() {
        this.Field493 = new Class270(this.Method486(), this.Method492(), this.Method489(), this.Method476(), 3.0f, 25, this.Field492);
        this.Field494 = new Class270(this.Method486(), this.Method492() - 1.0f, this.Method489(), this.Method476() + 2.0f, 3.0f, 25, 3);
    }

    @Override
    public void Method497(int n, int n2, float f) {
        boolean bl;
        super.Method497(n, n2, f);
        boolean bl2 = bl = KonasGlobals.INSTANCE.Field1131.Method1828().Method2173() == this.Field491;
        if (bl) {
            Class247.Method2043(this.Field494, this.Method501(n, n2) ? Field315.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field315);
            Class247.Method2042(this.Field494, Field317, 1.0f);
        } else {
            Class247.Method2043(this.Field493, this.Method501(n, n2) ? Field316.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field316);
        }
        String string = this.Method483();
        if (string.equals("MOVEMENT")) {
            string = "TRAVEL";
        }
        Class247.cfontRenderer.Method863(string, (int)(this.Method486() + this.Method489() / 2.0f - Class247.cfontRenderer.Method830(string) / 2.0f), (int)(this.Method492() + this.Method476() / 2.0f - Class247.cfontRenderer.Method831(this.Method483()) / 2.0f), Field324);
    }
}