package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class546;
import me.darki.konas.util.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;

public class BowAim
extends Module {
    public static TimerUtil Field763 = new TimerUtil();
    public static double Field764 = 0.0;
    public static double Field765 = 0.0;

    public static void Method559(float f, float f2) {
        Field764 = f;
        Field765 = f2;
    }

    public float Method808(EntityPlayer entityPlayer, double d, double d2) {
        double d3 = entityPlayer.posY + (double)(entityPlayer.getEyeHeight() / 2.0f) - (BowAim.mc.player.posY + (double) BowAim.mc.player.getEyeHeight());
        double d4 = entityPlayer.posX - BowAim.mc.player.posX;
        double d5 = entityPlayer.posZ - BowAim.mc.player.posZ;
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

    public BowAim() {
        super("BowAim", Category.COMBAT, "BowAimbot");
    }

    public static Float Method809(EntityPlayer entityPlayer) {
        return Float.valueOf(BowAim.mc.player.getDistance((Entity)entityPlayer));
    }

    @Subscriber(priority=100)
    public void Method135(UpdateEvent updateEvent) {
        double d;
        double d2;
        float f;
        if (updateEvent.isCanceled()) {
            return;
        }
        if (!(BowAim.mc.player.getActiveItemStack().getItem() instanceof ItemBow)) {
            return;
        }
        EntityPlayer entityPlayer = BowAim.mc.world.playerEntities.stream().filter(BowAim::Method132).filter(BowAim::Method128).filter(BowAim::Method138).filter(BowAim::Method122).filter(BowAim::Method126).min(Comparator.comparing(BowAim::Method809)).orElse(null);
        if (entityPlayer == null) {
            return;
        }
        float f2 = (float)(BowAim.mc.player.getActiveItemStack().getMaxItemUseDuration() - BowAim.mc.player.getItemInUseCount()) / 20.0f;
        if ((f2 = (f2 * f2 + f2 * 2.0f) / 3.0f) >= 1.0f) {
            f2 = 1.0f;
        }
        if (Float.isNaN(f = (float)(-Math.toDegrees(this.Method808(entityPlayer, d2 = (double)(f2 * 3.0f), d = (double)0.05f))))) {
            return;
        }
        double d3 = entityPlayer.posX - entityPlayer.lastTickPosX;
        double d4 = entityPlayer.posZ - entityPlayer.lastTickPosZ;
        double d5 = BowAim.mc.player.getDistance((Entity)entityPlayer);
        d5 -= d5 % 2.0;
        d3 = d5 / 2.0 * d3 * (BowAim.mc.player.isSprinting() ? 1.3 : 1.1);
        d4 = d5 / 2.0 * d4 * (BowAim.mc.player.isSprinting() ? 1.3 : 1.1);
        float f3 = (float)Math.toDegrees(Math.atan2(entityPlayer.posZ + d4 - BowAim.mc.player.posZ, entityPlayer.posX + d3 - BowAim.mc.player.posX)) - 90.0f;
        KonasGlobals.INSTANCE.Field1139.Method1937(f3, f);
        BowAim.Method559(f3, f);
        Field763.UpdateCurrentTime();
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    public static boolean Method128(EntityPlayer entityPlayer) {
        return entityPlayer != BowAim.mc.player;
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