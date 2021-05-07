package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import java.awt.TrayIcon;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Class235
extends Module {
    public Setting<Boolean> notSelf = new Setting<>("NotSelf", false);
    public Setting<Boolean> notify = new Setting<>("Notify", false).visibleIf(this::Method1632);

    @Subscriber(priority=20)
    public void Method462(TickEvent tickEvent) {
        if (Class235.mc.world == null || Class235.mc.player == null) {
            return;
        }
        for (EntityPlayer entityPlayer : Class235.mc.world.playerEntities) {
            if (((Boolean)this.notSelf.getValue()).booleanValue() && entityPlayer == Class235.mc.player || Class546.Method963((Entity)entityPlayer) || entityPlayer.getHealth() > 0.0f || !Class87.Field262.containsKey(entityPlayer.getName())) continue;
            ChatUtil.Method1035(entityPlayer.getEntityId(), "(h)%s(r) died after popping (h)%s(r) totem%s!", entityPlayer.getName(), Class87.Field262.get(entityPlayer.getName()), Class87.Field262.get(entityPlayer.getName()) > 1 ? "s" : "");
            if (!((Boolean)this.notify.getValue()).booleanValue()) continue;
            this.Method1637(entityPlayer.getName() + " died after popping " + Class87.Field262.get(entityPlayer.getName()) + " totems!", TrayIcon.MessageType.INFO);
        }
    }

    public Class235() {
        super("TotemPopCounter", Category.COMBAT, "PopCounter", "TotemCounter", "TotemPops");
        this.Method1633(315, 1000);
    }

    @Subscriber
    public void Method1500(Class43 class43) {
        block1: {
            if (((Boolean)this.notSelf.getValue()).booleanValue() && class43.Method265() == Class235.mc.player) {
                return;
            }
            ChatUtil.Method1035(class43.Method265().getEntityId(), "(h)%s(r) popped (h)%s(r) totem%s!", class43.Method265().getName(), class43.Method264(), class43.Method264() > 1 ? "s" : "");
            if (!((Boolean)this.notify.getValue()).booleanValue()) break block1;
            this.Method1637(class43.Method265().getName() + " popped " + class43.Method264() + " totem" + (class43.Method264() > 1 ? "s" : "") + "!", TrayIcon.MessageType.NONE);
        }
    }
}