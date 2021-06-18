package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.awt.TrayIcon;
import java.util.List;
import java.util.stream.Collectors;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.misc.AutoReconnect;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class300;
import me.darki.konas.util.CrystalUtils;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class546;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;

public class AutoLog
extends Module {
    public static Setting<Class300> mode = new Setting<>("Mode", Class300.HEALTH);
    public static Setting<Float> health = new Setting<>("Health", 10.0f, 22.0f, 0.0f, 0.1f).visibleIf(AutoLog::Method393);
    public Setting<Float> crystalRange = new Setting<>("CrystalRange", 10.0f, 15.0f, 1.0f, 1.0f).visibleIf(AutoLog::Method394);
    public static Setting<Boolean> ignoreTotem = new Setting<>("IgnoreTotem", true).visibleIf(AutoLog::Method539);
    public Setting<Boolean> notify = new Setting<>("Notify", false).visibleIf(this::Method1632);

    public boolean Method519() {
        for (int i = 0; i < 36; ++i) {
            if (AutoLog.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
            return true;
        }
        return false;
    }

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block4: {
            block7: {
                block5: {
                    block6: {
                        if (AutoLog.mc.world == null || AutoLog.mc.player == null) {
                            return;
                        }
                        if (mode.getValue() != Class300.HEALTH) break block5;
                        if (!(AutoLog.mc.player.getHealth() <= ((Float)health.getValue()).floatValue())) break block4;
                        if (!((Boolean)ignoreTotem.getValue()).booleanValue()) break block6;
                        this.Method134();
                        break block4;
                    }
                    if (this.Method519()) break block4;
                    this.Method134();
                    break block4;
                }
                if (mode.getValue() != Class300.PLAYER) break block7;
                for (EntityPlayer entityPlayer : AutoLog.mc.world.playerEntities) {
                    if (entityPlayer == AutoLog.mc.player || Class492.Method1989(entityPlayer.getName()) || Class546.Method963((Entity)entityPlayer)) continue;
                    this.Method134();
                    break block4;
                }
                break block4;
            }
            if (!((Boolean)ignoreTotem.getValue()).booleanValue() && this.Method519()) {
                return;
            }
            float f = 0.0f;
            List<Entity> list = AutoLog.mc.world.loadedEntityList.stream().filter(AutoLog::Method513).filter(this::Method386).collect(Collectors.toList());
            for (Entity entity : list) {
                f += CrystalUtils.CalculateDamageEndCrystal((EntityEnderCrystal)entity, (Entity) AutoLog.mc.player);
            }
            if (!(AutoLog.mc.player.getHealth() + AutoLog.mc.player.getAbsorptionAmount() <= f)) break block4;
            this.Method134();
        }
    }

    public void Method134() {
        AutoReconnect autoReconnect = (AutoReconnect) ModuleManager.getModuleByClass(AutoReconnect.class);
        if (autoReconnect != null) {
            if (autoReconnect.isEnabled()) {
                autoReconnect.toggle();
            }
        }
        this.toggle();
        if (((Boolean)this.notify.getValue()).booleanValue()) {
            this.Method1637("You have AutoLogged!", TrayIcon.MessageType.ERROR);
        }
        AutoLog.mc.player.inventory.currentItem = 1000;
    }

    public AutoLog() {
        super("AutoLog", Category.COMBAT, "AutoDisconnect");
    }

    public static boolean Method393() {
        return mode.getValue() == Class300.HEALTH;
    }

    public static boolean Method539() {
        return mode.getValue() != Class300.PLAYER;
    }

    public static boolean Method394() {
        return mode.getValue() == Class300.CRYSTALDMG;
    }

    public boolean Method386(Entity entity) {
        return AutoLog.mc.player.getDistance(entity) <= ((Float)this.crystalRange.getValue()).floatValue();
    }
}