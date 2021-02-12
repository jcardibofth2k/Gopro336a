package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.TrayIcon;
import java.util.concurrent.CopyOnWriteArrayList;

import me.darki.konas.TickEvent;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Class329
extends Module {
    public Setting<Boolean> Field553 = new Setting<>("OneLine", false);
    public Setting<Boolean> Field554 = new Setting<>("Leaves", true);
    public Setting<Boolean> Field555 = new Setting<>("IgnoreFriends", true);
    public Setting<Boolean> Field556 = new Setting<>("Notify", false).Method1191(this::Method1632);
    public CopyOnWriteArrayList<EntityPlayer> Field557 = new CopyOnWriteArrayList();

    public Class329() {
        super("VisualRange", Category.MISC, new String[0]);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class329.mc.world == null || Class329.mc.player == null) {
            return;
        }
        if (this.Field557 == null) {
            this.Field557 = new CopyOnWriteArrayList();
        }
        for (EntityPlayer entityPlayer : Class329.mc.world.playerEntities) {
            if (entityPlayer == Class329.mc.player || Class546.Method963((Entity)entityPlayer) || ((Boolean)this.Field555.getValue()).booleanValue() && Class492.Method1989(entityPlayer.getName()) || this.Field557.contains(entityPlayer)) continue;
            this.Field557.add(entityPlayer);
            if (((Boolean)this.Field553.getValue()).booleanValue()) {
                Logger.Method1117(entityPlayer.getName() + Command.Field122 + "a entered" + Command.Field122 + "f Visual Range!", 5555);
            } else {
                Logger.Method1118(entityPlayer.getName() + Command.Field122 + "a entered" + Command.Field122 + "f Visual Range!");
            }
            if (!((Boolean)this.Field556.getValue()).booleanValue()) continue;
            this.Method1637(entityPlayer.getName() + " has entered Visual Range!", TrayIcon.MessageType.WARNING);
        }
        for (EntityPlayer entityPlayer : this.Field557) {
            if (Class329.mc.world.playerEntities.contains(entityPlayer)) continue;
            this.Field557.remove(entityPlayer);
            if (!((Boolean)this.Field554.getValue()).booleanValue() || !((Boolean)this.Field555.getValue()).booleanValue() || Class492.Method1989(entityPlayer.getName())) continue;
            if (((Boolean)this.Field553.getValue()).booleanValue()) {
                Logger.Method1117(entityPlayer.getName() + Command.Field122 + "c left" + Command.Field122 + "f Visual Range!", 5555);
                continue;
            }
            Logger.Method1118(entityPlayer.getName() + Command.Field122 + "c left" + Command.Field122 + "f Visual Range!");
        }
    }
}
