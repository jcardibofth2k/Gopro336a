package me.darki.konas.unremaped;

import me.darki.konas.setting.IRunnable;

public class Class159
implements IRunnable {
    public Hud Field1820;

    public void Method630(Object object) {
        this.Method631((Boolean)object);
    }

    public void Method631(Boolean bl) {
        block1: {
            block0: {
                if (!bl.booleanValue()) break block0;
                if (Class557.Method795() == Hud.Field1405) break block1;
                Class557.Method796(Hud.Field1405);
                break block1;
            }
            if (Class557.Method795() == FontUtil.Field779) break block1;
            Class557.Method796(FontUtil.Field779);
        }
    }

    public Class159(Hud hud) {
        this.Field1820 = hud;
    }
}