package me.darki.konas.unremaped;

import me.darki.konas.command.Logger;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.IRunnable;

public class Class181
implements IRunnable {
    public Class202 Field1407;

    public Class181(Class202 class202) {
        this.Field1407 = class202;
    }

    public void Method630(Object object) {
        this.Method631((Boolean)object);
    }

    public void Method631(Boolean bl) {
        if (KonasGlobals.INSTANCE.Field1140 == null) {
            return;
        }
        if (!Class202.Field678 && !Class202.Method741(this.Field1407).GetDifferenceTiming(10000.0)) {
            Logger.Method1118("Please wait another " + Math.abs((System.currentTimeMillis() - Class202.Method741(this.Field1407).GetCurrentTime()) / 1000L - 10L) + " seconds, before you enable this setting again!");
            Class202.Method740(this.Field1407).Cancel();
        } else {
            KonasGlobals.INSTANCE.Field1140.Method591(bl);
            Class202.Method741(this.Field1407).UpdateCurrentTime();
        }
    }
}