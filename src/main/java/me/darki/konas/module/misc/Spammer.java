package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Random;

import me.darki.konas.command.Logger;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class526;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.config.Config;

public class Spammer
extends Module {
    //whoever renamed these fields and didn't refactor is a bitch on god
    public Setting<Float> delay = new Setting<>("Delay", 5.0f, 20.0f, 0.1f, 0.1f);
    public static Setting<Class526> spamFileList = new Setting<>("SpamFileList", new Class526());
    public Setting<Boolean> random = new Setting<>("Random", true);
    public Setting<Boolean> antiAntiSpam = new Setting<>("AntiAntiSpam", false);
    public TimerUtil Field794 = new TimerUtil();
    public static ArrayList<String> arrayList = new ArrayList();
    public static int Field796 = 0;
    public static ArrayList<Integer> arrayList1 = new ArrayList();
    public static int Field798 = 0;

    @Override
    public void onEnable() {
        if (Spammer.mc.player == null || Spammer.mc.world == null) {
            return;
        }
        this.Method124();
        Field798 = 0;
        Field796 = 0;
        arrayList1.clear();
    }

    @Subscriber
    public void Method462(TickEvent class63) {
        block8: {
            String string;
            if (Spammer.mc.player == null || Spammer.mc.world == null) {
                return;
            }
            if (!this.Field794.GetDifferenceTiming(this.delay.getValue().floatValue() * 1000.0f)) break block8;
            if (arrayList.isEmpty()) {
                this.Method124();
                Logger.Method1119("Caution: Spammer file '" + spamFileList.getValue().Method1225().get(Field796) + "' is empty!");
                this.Field794.UpdateCurrentTime();
                return;
            }
            if (this.random.getValue().booleanValue()) {
                if (arrayList1.size() == arrayList.size()) {
                    ++Field796;
                    this.Method124();
                    arrayList1.clear();
                    return;
                }
                int n = new Random().nextInt(arrayList.size());
                while (arrayList1.contains(n)) {
                    n = new Random().nextInt(arrayList.size());
                }
                arrayList1.add(n);
                string = arrayList.get(n);
            } else {
                if (Field798 == arrayList.size()) {
                    this.Method124();
                    ++Field796;
                    Field798 = 0;
                }
                string = arrayList.get(Field798);
                ++Field798;
            }
            if (this.antiAntiSpam.getValue().booleanValue()) {
                string = string + " " + new Random().nextInt(69420);
            }
            Spammer.mc.player.sendChatMessage(string);
            this.Field794.UpdateCurrentTime();
        }
    }

    public void Method124() {
        if (spamFileList.getValue().Method1225().size() == 0) {
            Logger.Method1119("You don't have any Spammer Files loaded. Load some with the Spammer Command");
            this.toggle();
            return;
        }
        if (Field796 >= spamFileList.getValue().Method1225().size()) {
            Field796 = 0;
        }
        Config.Method2277(spamFileList.getValue().Method1225().get(Field796), true);
    }

    public Spammer() {
        super("Spammer", Category.MISC);
    }
}