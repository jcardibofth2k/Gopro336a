package me.darki.konas.util;

import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;

public class ThreadUtil
implements Runnable {
    public static ThreadUtil Field659;
    public AutoCrystal CA;

    public static ThreadUtil Method727(AutoCrystal autoCrystal) {
        return ThreadUtil.Method728(autoCrystal);
    }

    public static ThreadUtil Method728(AutoCrystal autoCrystal) {
        if (Field659 == null) {
            Field659 = new ThreadUtil();
            ThreadUtil.Field659.CA = autoCrystal;
        }
        return Field659;
    }

    @Override
    public void run() {
        if (!AutoCrystal.Method1579(this.CA).get()) return;
        Setting<Float> setting = AutoCrystal.offset;
        float f2 = setting.getValue();
        long l = (long)(f2 * 40.0f);
        try {
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
            AutoCrystal.isUsingThreading(this.CA).interrupt();
        }
        if (!AutoCrystal.Method1579(this.CA).get()) {
            return;
        }
        AutoCrystal.Method1579(this.CA).set(false);
        if (AutoCrystal.Method1570(this.CA).get()) {
            return;
        }
        AutoCrystal.Method1563(this.CA);
    }
}