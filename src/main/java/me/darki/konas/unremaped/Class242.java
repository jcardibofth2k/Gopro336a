package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class242
extends Module {
    public Setting<Boolean> Field2361 = new Setting<>("OnlyTamed", false);

    @Subscriber
    public void Method2065(Class60 class60) {
        block0: {
            if (!class60.Method322().isTame() && this.Field2361.getValue().booleanValue()) break block0;
            class60.Cancel();
        }
    }

    public Class242() {
        super("EntityControl", Category.EXPLOIT, "FakeSaddle");
    }

    @Subscriber
    public void Method2066(Class72 class72) {
        block0: {
            if (!class72.Method322().isTame() && this.Field2361.getValue().booleanValue()) break block0;
            class72.Cancel();
        }
    }
}
