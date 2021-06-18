package me.darki.konas.unremaped;

import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.util.TimerUtil;

public class Class202
extends Module {
    public TimerUtil Field677 = new TimerUtil();
    public static boolean Field678 = true;
    public ListenableSettingDecorator<Boolean> Field679 = new ListenableSettingDecorator("Cute", false, new Class181(this));
    public ListenableSettingDecorator<Boolean> Field680 = new ListenableSettingDecorator("Invite", true, new Class205(this));

    @Override
    public void onDisable() {
        if (KonasGlobals.INSTANCE.Field1140 == null) {
            return;
        }
        KonasGlobals.INSTANCE.Field1140.Method594();
    }

    public static ListenableSettingDecorator Method740(Class202 class202) {
        return class202.Field679;
    }

    public static TimerUtil Method741(Class202 class202) {
        return class202.Field677;
    }

    public Class202() {
        super("RPC", "Control your discord rich presence", Category.CLIENT, "Discord", "DiscordRPC");
        this.Field677.SetCurrentTime(10000L);
    }

    @Override
    public void onEnable() {
        if (KonasGlobals.INSTANCE.Field1140 == null) {
            return;
        }
        if (!Field678 && !this.Field677.GetDifferenceTiming(10000.0)) {
            Logger.Method1118("Please wait another " + Math.abs((System.currentTimeMillis() - this.Field677.GetCurrentTime()) / 1000L - 10L) + " seconds, before you enable this module again!");
            this.toggle();
        } else {
            KonasGlobals.INSTANCE.Field1140.Method587();
            this.Field677.UpdateCurrentTime();
        }
    }

    public static ListenableSettingDecorator Method742(Class202 class202) {
        return class202.Field680;
    }
}