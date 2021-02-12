package me.darki.konas.unremaped;

import me.darki.konas.command.Logger;
import me.darki.konas.module.client.NewGui;

public class Class181
implements Class529 {
    public Class202 Field1407;

    public Class181(Class202 class202) {
        this.Field1407 = class202;
    }

    public void Method630(Object object) {
        this.Method631((Boolean)object);
    }

    public void Method631(Boolean bl) {
        if (NewGui.INSTANCE.Field1140 == null) {
            return;
        }
        if (!Class202.Field678 && !Class202.Method741(this.Field1407).Method737(10000.0)) {
            Logger.Method1118("Please wait another " + Math.abs((System.currentTimeMillis() - Class202.Method741(this.Field1407).Method736()) / 1000L - 10L) + " seconds, before you enable this setting again!");
            Class202.Method740(this.Field1407).Method1153();
        } else {
            NewGui.INSTANCE.Field1140.Method591(bl);
            Class202.Method741(this.Field1407).Method739();
        }
    }
}
