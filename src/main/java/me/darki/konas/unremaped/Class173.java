package me.darki.konas.unremaped;

import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.IRunnable;

public class Class173
implements IRunnable {
    public ClickGUIModule Field1547;

    public void Method631(Boolean bl) {
        block1: {
            block0: {
                if (!bl.booleanValue()) break block0;
                if (Class548.Method1017() == ClickGUIModule.Field1536) break block1;
                Class548.Method1018(ClickGUIModule.Field1536);
                break block1;
            }
            if (Class548.Method1017() == FontUtil.Field779) break block1;
            Class548.Method1018(FontUtil.Field779);
        }
    }

    public void Method630(Object object) {
        this.Method631((Boolean)object);
    }

    public Class173(ClickGUIModule clickGUIModule) {
        this.Field1547 = clickGUIModule;
    }
}