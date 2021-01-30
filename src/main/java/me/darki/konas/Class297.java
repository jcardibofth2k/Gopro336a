package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.command.Command;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class297
extends Module {
    public static String Field1514 = " \u23d0 \uff2b\uff4f\uff4e\uff41\uff53";
    public static String Field1515 = " : Konas";
    public Setting<Boolean> Field1516 = new Setting<>("Strict", false);

    public Class297() {
        super("ChatAppend", 0, Category.MISC, "ChatSuffix");
    }

    @Subscriber
    public void Method552(Class648 class648) {
        if (Class297.mc.world == null || Class297.mc.player == null) {
            return;
        }
        String string = class648.Method1201();
        if (string.startsWith("/") || string.startsWith(Command.Method190())) {
            return;
        }
        if ((string = string + ((Boolean)this.Field1516.getValue() != false ? Field1515 : Field1514)).length() >= 256) {
            string = string.substring(0, 256);
        }
        class648.Method86(string);
    }
}
