package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.command.Command;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class648;

public class ChatAppend
extends Module {
    public static String string = " \u23d0 \uff2b\uff4f\uff4e\uff41\uff53";
    public static String string1 = " : Konas";
    public Setting<Boolean> strict = new Setting<>("Strict", false);

    public ChatAppend() {
        super("ChatAppend", 0, Category.MISC, "ChatSuffix");
    }

    @Subscriber
    public void Method552(Class648 class648) {
        if (ChatAppend.mc.world == null || ChatAppend.mc.player == null) {
            return;
        }
        String string = class648.Method1201();
        if (string.startsWith("/") || string.startsWith(Command.Method190())) {
            return;
        }
        if ((string = string + ((Boolean)this.strict.getValue() != false ? string1 : ChatAppend.string)).length() >= 256) {
            string = string.substring(0, 256);
        }
        class648.Method86(string);
    }
}