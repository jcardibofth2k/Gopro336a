package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.TickEvent;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.mixin.mixins.ITimer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;

public class Class470
extends Module {
    public Setting<Float> Field2561 = new Setting<>("TimerSpeed", Float.valueOf(4.0f), Float.valueOf(50.0f), Float.valueOf(0.2f), Float.valueOf(0.5f));
    public Setting<Boolean> Field2562 = new Setting<>("TpsSync", false);
    public Setting<Boolean> Field2563 = new Setting<>("Switch", false).Method1191(this::Method396);
    public Setting<Integer> Field2564 = new Setting<>("Active", 5, 20, 1, 1).Method1191(this.Field2563::getValue);
    public Setting<Integer> Field2565 = new Setting<>("Inactive", 5, 20, 1, 1).Method1191(this.Field2563::getValue);
    public Setting<Float> Field2566 = new Setting<>("InactiveSpeed", Float.valueOf(2.0f), Float.valueOf(50.0f), Float.valueOf(0.2f), Float.valueOf(0.5f)).Method1191(this.Field2563::getValue);
    public int Field2567 = 0;

    @Override
    public boolean Method396() {
        return (Boolean)this.Field2562.getValue() == false;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class470.mc.world == null || Class470.mc.player == null) {
            return;
        }
        if (((Boolean)this.Field2562.getValue()).booleanValue()) {
            NewGui.INSTANCE.Field1134.Method748(true);
            this.Method1645((float)Math.round(50.0f / ((ITimer)((IMinecraft)mc).Method56()).Method96() * 100.0f) / 100.0f + "");
        } else {
            NewGui.INSTANCE.Field1134.Method748(false);
            float f = ((Float)this.Field2561.getValue()).floatValue();
            if (((Boolean)this.Field2563.getValue()).booleanValue()) {
                if (this.Field2567 > (Integer)this.Field2564.getValue() + (Integer)this.Field2565.getValue()) {
                    this.Field2567 = 0;
                }
                if (this.Field2567 > (Integer)this.Field2564.getValue()) {
                    f = ((Float)this.Field2566.getValue()).floatValue();
                }
            }
            NewGui.INSTANCE.Field1134.Method746(this, 5, f);
            this.Method1645((float)Math.round(50.0f / ((ITimer)((IMinecraft)mc).Method56()).Method96() * 100.0f) / 100.0f + "");
            ++this.Field2567;
        }
    }

    public Class470() {
        super("Timer", "Changes game tick length", 0, Category.PLAYER, new String[0]);
    }

    @Override
    public void onDisable() {
        NewGui.INSTANCE.Field1134.Method749(this);
        NewGui.INSTANCE.Field1134.Method748(false);
    }

    @Override
    public void onEnable() {
        this.Field2567 = 0;
    }
}
