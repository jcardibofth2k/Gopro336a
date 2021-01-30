package me.darki.konas.module;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Random;

import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Spammer
extends Module {
    public Setting<Float> Field790 = new Setting<>("Delay", Float.valueOf(5.0f), Float.valueOf(20.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Class526> Field791 = new Setting<>("SpamFileList", new Class526());
    public Setting<Boolean> Field792 = new Setting<>("Random", true);
    public Setting<Boolean> Field793 = new Setting<>("AntiAntiSpam", false);
    public Class566 Field794 = new Class566();
    public static ArrayList<String> Field795 = new ArrayList();
    public static int Field796 = 0;
    public static ArrayList<Integer> Field797 = new ArrayList();
    public static int Field798 = 0;

    @Override
    public void onEnable() {
        if (Spammer.mc.player == null || Spammer.mc.world == null) {
            return;
        }
        this.Method124();
        Field798 = 0;
        Field796 = 0;
        Field797.clear();
    }

    @Subscriber
    public void Method462(Class63 class63) {
        block8: {
            String string;
            if (Spammer.mc.player == null || Spammer.mc.world == null) {
                return;
            }
            if (!this.Field794.Method737(((Float)this.Field790.getValue()).floatValue() * 1000.0f)) break block8;
            if (Field795.isEmpty()) {
                this.Method124();
                Logger.Method1119("Caution: Spammer file '" + ((Class526)Field791.getValue()).Method1225().get(Field796) + "' is empty!");
                this.Field794.Method739();
                return;
            }
            if (((Boolean)this.Field792.getValue()).booleanValue()) {
                if (Field797.size() == Field795.size()) {
                    ++Field796;
                    this.Method124();
                    Field797.clear();
                    return;
                }
                int n = new Random().nextInt(Field795.size());
                while (Field797.contains(n)) {
                    n = new Random().nextInt(Field795.size());
                }
                Field797.add(n);
                string = Field795.get(n);
            } else {
                if (Field798 == Field795.size()) {
                    this.Method124();
                    ++Field796;
                    Field798 = 0;
                }
                string = Field795.get(Field798);
                ++Field798;
            }
            if (((Boolean)this.Field793.getValue()).booleanValue()) {
                string = string + " " + new Random().nextInt(69420);
            }
            Spammer.mc.player.sendChatMessage(string);
            this.Field794.Method739();
        }
    }

    public void Method124() {
        if (((Class526)Field791.getValue()).Method1225().size() == 0) {
            Logger.Method1119("You don't have any Spammer Files loaded. Load some with the Spammer Command");
            this.toggle();
            return;
        }
        if (Field796 >= ((Class526)Field791.getValue()).Method1225().size()) {
            Field796 = 0;
        }
        Class589.Method2277(((Class526)Field791.getValue()).Method1225().get(Field796), true);
    }

    public Spammer() {
        super("Spammer", Category.MISC, new String[0]);
    }
}
