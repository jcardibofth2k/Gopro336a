package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.mixin.mixins.ITimer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;

public class Timer
extends Module {
    public Setting<Float> timerSpeed = new Setting<>("TimerSpeed", Float.valueOf(4.0f), Float.valueOf(50.0f), Float.valueOf(0.2f), Float.valueOf(0.5f));
    public Setting<Boolean> tpsSync = new Setting<>("TpsSync", false);
    public Setting<Boolean> switch = new Setting<>("Switch", false).visibleIf(this::Method396);
    public Setting<Integer> active = new Setting<>("Active", 5, 20, 1, 1).visibleIf(this.Field2563::getValue);
    public Setting<Integer> inactive = new Setting<>("Inactive", 5, 20, 1, 1).visibleIf(this.Field2563::getValue);
    public Setting<Float> inactiveSpeed = new Setting<>("InactiveSpeed", Float.valueOf(2.0f), Float.valueOf(50.0f), Float.valueOf(0.2f), Float.valueOf(0.5f)).visibleIf(this.Field2563::getValue);
    public int Field2567 = 0;

    @Override
    public boolean Method396() {
        return (Boolean)this.tpsSync.getValue() == false;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Timer.mc.world == null || Timer.mc.player == null) {
            return;
        }
        if (((Boolean)this.tpsSync.getValue()).booleanValue()) {
            KonasGlobals.INSTANCE.Field1134.Method748(true);
            this.Method1645((float)Math.round(50.0f / ((ITimer)((IMinecraft)mc).getTimer()).getTickLength() * 100.0f) / 100.0f + "");
        } else {
            KonasGlobals.INSTANCE.Field1134.Method748(false);
            float f = ((Float)this.timerSpeed.getValue()).floatValue();
            if (((Boolean)this.switch.getValue()).booleanValue()) {
                if (this.Field2567 > (Integer)this.active.getValue() + (Integer)this.inactive.getValue()) {
                    this.Field2567 = 0;
                }
                if (this.Field2567 > (Integer)this.active.getValue()) {
                    f = ((Float)this.inactiveSpeed.getValue()).floatValue();
                }
            }
            KonasGlobals.INSTANCE.Field1134.Method746(this, 5, f);
            this.Method1645((float)Math.round(50.0f / ((ITimer)((IMinecraft)mc).getTimer()).getTickLength() * 100.0f) / 100.0f + "");
            ++this.Field2567;
        }
    }

    public Timer() {
        super("Timer", "Changes game tick length", 0, Category.PLAYER, new String[0]);
    }

    @Override
    public void onDisable() {
        KonasGlobals.INSTANCE.Field1134.Method749(this);
        KonasGlobals.INSTANCE.Field1134.Method748(false);
    }

    @Override
    public void onEnable() {
        this.Field2567 = 0;
    }
}