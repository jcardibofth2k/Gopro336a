package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class EntityControl
extends Module {
    public Setting<Boolean> onlyTamed = new Setting<>("OnlyTamed", false);

    @Subscriber
    public void Method2065(Class60 class60) {
        block0: {
            if (!class60.Method322().isTame() && ((Boolean)this.onlyTamed.getValue()).booleanValue()) break block0;
            class60.Cancel();
        }
    }

    public EntityControl() {
        super("EntityControl", Category.EXPLOIT, "FakeSaddle");
    }

    @Subscriber
    public void Method2066(Class72 class72) {
        block0: {
            if (!class72.Method322().isTame() && ((Boolean)this.onlyTamed.getValue()).booleanValue()) break block0;
            class72.Cancel();
        }
    }
}