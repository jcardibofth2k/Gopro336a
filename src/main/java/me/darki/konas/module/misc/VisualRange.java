package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.awt.TrayIcon;
import java.util.concurrent.CopyOnWriteArrayList;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class546;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class VisualRange
extends Module {
    public Setting<Boolean> oneLine = new Setting<>("OneLine", false);
    public Setting<Boolean> leaves = new Setting<>("Leaves", true);
    public Setting<Boolean> ignoreFriends = new Setting<>("IgnoreFriends", true);
    public Setting<Boolean> notify = new Setting<>("Notify", false).visibleIf(this::Method1632);
    public CopyOnWriteArrayList<EntityPlayer> Field557 = new CopyOnWriteArrayList();

    public VisualRange() {
        super("VisualRange", Category.MISC, new String[0]);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (VisualRange.mc.world == null || VisualRange.mc.player == null) {
            return;
        }
        if (this.Field557 == null) {
            this.Field557 = new CopyOnWriteArrayList();
        }
        for (EntityPlayer entityPlayer : VisualRange.mc.world.playerEntities) {
            if (entityPlayer == VisualRange.mc.player || Class546.Method963((Entity)entityPlayer) || ((Boolean)this.ignoreFriends.getValue()).booleanValue() && Class492.Method1989(entityPlayer.getName()) || this.Field557.contains(entityPlayer)) continue;
            this.Field557.add(entityPlayer);
            if (((Boolean)this.oneLine.getValue()).booleanValue()) {
                Logger.Method1117(entityPlayer.getName() + Command.Field122 + "a entered" + Command.Field122 + "f Visual Range!", 5555);
            } else {
                Logger.Method1118(entityPlayer.getName() + Command.Field122 + "a entered" + Command.Field122 + "f Visual Range!");
            }
            if (!((Boolean)this.notify.getValue()).booleanValue()) continue;
            this.Method1637(entityPlayer.getName() + " has entered Visual Range!", TrayIcon.MessageType.WARNING);
        }
        for (EntityPlayer entityPlayer : this.Field557) {
            if (VisualRange.mc.world.playerEntities.contains(entityPlayer)) continue;
            this.Field557.remove(entityPlayer);
            if (!((Boolean)this.leaves.getValue()).booleanValue() || !((Boolean)this.ignoreFriends.getValue()).booleanValue() || Class492.Method1989(entityPlayer.getName())) continue;
            if (((Boolean)this.oneLine.getValue()).booleanValue()) {
                Logger.Method1117(entityPlayer.getName() + Command.Field122 + "c left" + Command.Field122 + "f Visual Range!", 5555);
                continue;
            }
            Logger.Method1118(entityPlayer.getName() + Command.Field122 + "c left" + Command.Field122 + "f Visual Range!");
        }
    }
}