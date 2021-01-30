package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.awt.TrayIcon;
import java.util.List;
import java.util.stream.Collectors;

import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;

public class Class298
extends Module {
    public static Setting<Class300> Field1485 = new Setting<>("Mode", Class300.HEALTH);
    public static Setting<Float> Field1486 = new Setting<>("Health", Float.valueOf(10.0f), Float.valueOf(22.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).Method1191(Class298::Method393);
    public Setting<Float> Field1487 = new Setting<>("CrystalRange", Float.valueOf(10.0f), Float.valueOf(15.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).Method1191(Class298::Method394);
    public static Setting<Boolean> Field1488 = new Setting<>("IgnoreTotem", true).Method1191(Class298::Method539);
    public Setting<Boolean> Field1489 = new Setting<>("Notify", false).Method1191(this::Method1632);

    public boolean Method519() {
        for (int i = 0; i < 36; ++i) {
            if (Class298.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
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
                        if (Class298.mc.world == null || Class298.mc.player == null) {
                            return;
                        }
                        if (Field1485.getValue() != Class300.HEALTH) break block5;
                        if (!(Class298.mc.player.getHealth() <= ((Float)Field1486.getValue()).floatValue())) break block4;
                        if (!((Boolean)Field1488.getValue()).booleanValue()) break block6;
                        this.Method134();
                        break block4;
                    }
                    if (this.Method519()) break block4;
                    this.Method134();
                    break block4;
                }
                if (Field1485.getValue() != Class300.PLAYER) break block7;
                for (EntityPlayer entityPlayer : Class298.mc.world.playerEntities) {
                    if (entityPlayer == Class298.mc.player || Class492.Method1989(entityPlayer.getName()) || Class546.Method963((Entity)entityPlayer)) continue;
                    this.Method134();
                    break block4;
                }
                break block4;
            }
            if (!((Boolean)Field1488.getValue()).booleanValue() && this.Method519()) {
                return;
            }
            float f = 0.0f;
            List list = Class298.mc.world.loadedEntityList.stream().filter(Class298::Method513).filter(this::Method386).collect(Collectors.toList());
            for (Entity entity : list) {
                f += Class475.Method2156((EntityEnderCrystal)entity, (Entity)Class298.mc.player);
            }
            if (!(Class298.mc.player.getHealth() + Class298.mc.player.getAbsorptionAmount() <= f)) break block4;
            this.Method134();
        }
    }

    public void Method134() {
        Class284 class284 = (Class284)Class167.Method1610(Class284.class);
        if (class284 != null) {
            if (class284.Method1651()) {
                class284.toggle();
            }
        }
        this.toggle();
        if (((Boolean)this.Field1489.getValue()).booleanValue()) {
            this.Method1637("You have AutoLogged!", TrayIcon.MessageType.ERROR);
        }
        Class298.mc.player.inventory.currentItem = 1000;
    }

    public Class298() {
        super("AutoLog", Category.COMBAT, "AutoDisconnect");
    }

    public static boolean Method393() {
        return Field1485.getValue() == Class300.HEALTH;
    }

    public static boolean Method539() {
        return Field1485.getValue() != Class300.PLAYER;
    }

    public static boolean Method394() {
        return Field1485.getValue() == Class300.CRYSTALDMG;
    }

    public boolean Method386(Entity entity) {
        return Class298.mc.player.getDistance(entity) <= ((Float)this.Field1487.getValue()).floatValue();
    }
}
