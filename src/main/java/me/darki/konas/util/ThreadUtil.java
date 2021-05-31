package me.darki.konas.util;

import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;

public class ThreadUtil
implements Runnable {
    public static ThreadUtil Field659;
    public AutoCrystal CA;

    public static ThreadUtil updateThreadCA(AutoCrystal autoCrystal) {
        return ThreadUtil.createThreadCA(autoCrystal);
    }

    public static ThreadUtil createThreadCA(AutoCrystal autoCrystal) {
        if (Field659 == null) {
            Field659 = new ThreadUtil();
            ThreadUtil.Field659.CA = autoCrystal;
        }
        return Field659;
    }

    @Override
    public void run() {
        if (!AutoCrystal.isPlacing(this.CA).get()) return;
        Setting<Float> setting = AutoCrystal.offset;
        float f2 = setting.getValue();
        long l = (long)(f2 * 40.0f);
        try {
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
            AutoCrystal.isUsingThreading(this.CA).interrupt();
        }
        if (!AutoCrystal.isPlacing(this.CA).get()) {
            return;
        }
        AutoCrystal.isPlacing(this.CA).set(false);
        if (AutoCrystal.getTickPhase(this.CA).get()) {
            return;
        }
        AutoCrystal.startPlacingStatic(this.CA);
    }
}