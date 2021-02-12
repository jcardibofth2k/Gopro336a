package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

import me.darki.konas.*;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;

public class Sky
extends Module {
    public Setting<Class436> timeMode = new Setting<>("TimeMode", Class436.STATIC);
    public Setting<Integer> time = new Setting<>("Time", 6000, 24000, 0, 1).Method1191(this::Method396);
    public static Setting<Boolean> colorize = new Setting<>("Colorize", false);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-16776961)).Method1191(colorize::getValue);

    public Sky() {
        super("Sky", Category.RENDER);
    }

    @Subscriber
    public void Method872(Class85 class85) {
        block2: {
            if (this.timeMode.getValue() == Class436.NONE) break block2;
            if (this.timeMode.getValue() == Class436.STATIC) {
                class85.Method457(this.time.getValue().intValue());
            } else {
                ZonedDateTime zonedDateTime = ZonedDateTime.now();
                Instant instant = zonedDateTime.toLocalDate().atStartOfDay(zonedDateTime.getZone()).toInstant();
                Duration duration = Duration.between(instant, Instant.now());
                long l = duration.getSeconds();
                class85.Method457((int)((float)l / 86400.0f));
            }
            class85.Cancel();
        }
    }

    @Subscriber
    public void Method873(Class58 class58) {
        block0: {
            if (!colorize.getValue().booleanValue()) break block0;
            float[] fArray = Class556.Method806(color.getValue().Method774());
            class58.Method341(fArray[0]);
            class58.Method294(fArray[1]);
            class58.Method344(fArray[2]);
        }
    }

    @Subscriber
    public void Method874(Class64 class64) {
        block0: {
            if (!colorize.getValue().booleanValue()) break block0;
            class64.Method294(0.0f);
            class64.setCanceled(true);
        }
    }

    @Override
    public boolean Method396() {
        return this.timeMode.getValue() == Class436.STATIC;
    }
}
