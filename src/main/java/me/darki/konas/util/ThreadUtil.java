package me.darki.konas.util;

import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;

public class ThreadUtil
implements Runnable {
    public static ThreadUtil Field659;
    public AutoCrystal Field660;

    public static ThreadUtil Method727(AutoCrystal autoCrystal) {
        return ThreadUtil.Method728(autoCrystal);
    }

    public static ThreadUtil Method728(AutoCrystal autoCrystal) {
        if (Field659 == null) {
            Field659 = new ThreadUtil();
            ThreadUtil.Field659.Field660 = autoCrystal;
        }
        return Field659;
    }

    @Override
    public void run() {
        block9: {
            if (!AutoCrystal.Method1579(this.Field660).get()) break block9;
            Setting<Float> setting = AutoCrystal.offset;
            Object t = setting.getValue();
            Float f = (Float)t;
            float f2 = f.floatValue();
            long l = (long)(f2 * 40.0f);
            try {
                Thread.sleep(l);
            }
            catch (InterruptedException interruptedException) {
                AutoCrystal.Method1562(this.Field660).interrupt();
            }
            if (!AutoCrystal.Method1579(this.Field660).get()) {
                return;
            }
            AutoCrystal.Method1579(this.Field660).set(false);
            if (AutoCrystal.Method1570(this.Field660).get()) {
                return;
            }
            AutoCrystal.Method1563(this.Field660);
        }
    }
}