package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;

public class Class317
extends Module {
    public static Class566 Field763 = new Class566();
    public static double Field764 = 0.0;
    public static double Field765 = 0.0;

    public static void Method559(float f, float f2) {
        Field764 = f;
        Field765 = f2;
    }

    public float Method808(EntityPlayer entityPlayer, double d, double d2) {
        double d3 = entityPlayer.posY + (double)(entityPlayer.getEyeHeight() / 2.0f) - (Class317.mc.player.posY + (double)Class317.mc.player.getEyeHeight());
        double d4 = entityPlayer.posX - Class317.mc.player.posX;
        double d5 = entityPlayer.posZ - Class317.mc.player.posZ;
        double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        return this.Method810(d, d2, d6, d3);
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return !entityPlayer.isDead;
    }

    public static boolean Method126(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() > 0.0f;
    }

    public static boolean Method122(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public Class317() {
        super("BowAim", Category.COMBAT, "BowAimbot");
    }

    public static Float Method809(EntityPlayer entityPlayer) {
        return Float.valueOf(Class317.mc.player.getDistance((Entity)entityPlayer));
    }

    @Subscriber(priority=100)
    public void Method135(UpdateEvent updateEvent) {
        double d;
        double d2;
        float f;
        if (updateEvent.isCanceled()) {
            return;
        }
        if (!(Class317.mc.player.getActiveItemStack().getItem() instanceof ItemBow)) {
            return;
        }
        EntityPlayer entityPlayer = Class317.mc.world.playerEntities.stream().filter(Class317::Method132).filter(Class317::Method128).filter(Class317::Method138).filter(Class317::Method122).filter(Class317::Method126).min(Comparator.comparing(Class317::Method809)).orElse(null);
        if (entityPlayer == null) {
            return;
        }
        float f2 = (float)(Class317.mc.player.getActiveItemStack().getMaxItemUseDuration() - Class317.mc.player.getItemInUseCount()) / 20.0f;
        if ((f2 = (f2 * f2 + f2 * 2.0f) / 3.0f) >= 1.0f) {
            f2 = 1.0f;
        }
        if (Float.isNaN(f = (float)(-Math.toDegrees(this.Method808(entityPlayer, d2 = (double)(f2 * 3.0f), d = (double)0.05f))))) {
            return;
        }
        double d3 = entityPlayer.posX - entityPlayer.lastTickPosX;
        double d4 = entityPlayer.posZ - entityPlayer.lastTickPosZ;
        double d5 = Class317.mc.player.getDistance((Entity)entityPlayer);
        d5 -= d5 % 2.0;
        d3 = d5 / 2.0 * d3 * (Class317.mc.player.isSprinting() ? 1.3 : 1.1);
        d4 = d5 / 2.0 * d4 * (Class317.mc.player.isSprinting() ? 1.3 : 1.1);
        float f3 = (float)Math.toDegrees(Math.atan2(entityPlayer.posZ + d4 - Class317.mc.player.posZ, entityPlayer.posX + d3 - Class317.mc.player.posX)) - 90.0f;
        NewGui.INSTANCE.Field1139.Method1937(f3, f);
        Class317.Method559(f3, f);
        Field763.Method739();
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    public static boolean Method128(EntityPlayer entityPlayer) {
        return entityPlayer != Class317.mc.player;
    }

    public float Method810(double d, double d2, double d3, double d4) {
        double d5 = d2 * (d3 * d3);
        d4 = 2.0 * d4 * (d * d);
        d4 = d2 * (d5 + d4);
        d4 = Math.sqrt(d * d * d * d - d4);
        d = d * d - d4;
        d4 = Math.atan2(d * d + d4, d2 * d3);
        d = Math.atan2(d, d2 * d3);
        return (float)Math.min(d4, d);
    }
}
