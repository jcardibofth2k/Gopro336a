package me.darki.konas.unremaped;

import me.darki.konas.command.Logger;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.IRunnable;

public class Class205
implements IRunnable {
    public Class202 Field558;

    public void Method630(Object object) {
        this.Method631((Boolean)object);
    }

    public Class205(Class202 class202) {
        this.Field558 = class202;
    }

    public void Method631(Boolean bl) {
        if (KonasGlobals.INSTANCE.Field1140 == null) {
            return;
        }
        if (!Class202.Field678 && !Class202.Method741(this.Field558).GetDifferenceTiming(10000.0)) {
            Logger.Method1118("Please wait another " + Math.abs((System.currentTimeMillis() - Class202.Method741(this.Field558).GetCurrentTime()) / 1000L - 10L) + " seconds, before you enable this setting again!");
            Class202.Method742(this.Field558).cancel();
        } else {
            KonasGlobals.INSTANCE.Field1140.Method589(bl);
            Class202.Method741(this.Field558).UpdateCurrentTime();
        }
    }

    //Method added by Gopro336 to fix error
    @Override
    public void run(Object argument) {

    }
}