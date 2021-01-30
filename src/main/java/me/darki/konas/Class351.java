package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class Class351
extends Module {
    public static Setting<Class364> Field2529 = new Setting<>("Mode", Class364.SPIN);
    public static Setting<Class353> Field2530 = new Setting<>("Pitch", Class353.JITTER);
    public static Setting<Integer> Field2531 = new Setting<>("Speed", 10, 55, 1, 1);
    public static Setting<Integer> Field2532 = new Setting<>("YawAdd", 0, 180, -180, 10);
    public float Field2533 = 0.0f;
    public float Field2534 = 0.0f;

    public static Float Method520(EntityPlayer entityPlayer) {
        return Float.valueOf(Class351.mc.player.getDistance((Entity)entityPlayer));
    }

    public EntityPlayer Method1054() {
        return Class351.mc.world.playerEntities.stream().filter(Class351::Method132).filter(Class351::Method141).filter(Class351::Method138).min(Comparator.comparing(Class351::Method520)).orElse(null);
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class492.Method1989(entityPlayer.getName());
    }

    public Class351() {
        super("AntiAim", "Breaks motion prediction in bad clients", Category.PLAYER, "SpinBot");
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return entityPlayer.getDistance((Entity)Class351.mc.player) < 10.0f;
    }

    @Subscriber(priority=0)
    public void Method135(UpdateEvent updateEvent) {
        EntityPlayer entityPlayer;
        if (updateEvent.isCanceled() || !Class496.Method1966()) {
            return;
        }
        this.Field2533 = Field2529.getValue() == Class364.SPIN ? (this.Field2533 += (float)((Integer)Field2531.getValue()).intValue()) : (Field2529.getValue() == Class364.JITTER ? (Math.random() > 0.5 ? (float)((double)this.Field2533 + (double)((Integer)Field2531.getValue()).intValue() * Math.random()) : (float)((double)this.Field2533 - (double)((Integer)Field2531.getValue()).intValue() * Math.random())) : ((entityPlayer = this.Method1054()) != null ? RotationUtil.Method1946(Class351.mc.player.getPositionEyes(1.0f), entityPlayer.getPositionEyes(1.0f))[0] - 180.0f : Class351.mc.player.rotationYaw));
        this.Field2533 += (float)((Integer)Field2532.getValue()).intValue();
        this.Field2533 = MathHelper.wrapDegrees((int)((int)this.Field2533));
        this.Field2534 = Field2530.getValue() == Class353.NONE ? Class351.mc.player.rotationPitch : (Field2530.getValue() == Class353.JITTER ? (Math.random() > 0.5 ? (float)((double)this.Field2534 + (double)((Integer)Field2531.getValue()).intValue() * Math.random()) : (float)((double)this.Field2534 - (double)((Integer)Field2531.getValue()).intValue() * Math.random())) : (Field2530.getValue() == Class353.STARE ? ((entityPlayer = this.Method1054()) != null ? RotationUtil.Method1946(Class351.mc.player.getPositionEyes(1.0f), entityPlayer.getPositionEyes(1.0f))[1] : Class351.mc.player.rotationPitch) : 90.0f));
        if (this.Field2534 > 89.0f) {
            this.Field2534 = 89.0f;
        } else if (this.Field2534 < -89.0f) {
            this.Field2534 = -89.0f;
        }
        NewGui.INSTANCE.Field1139.Method1937(this.Field2533, this.Field2534);
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return entityPlayer != Class351.mc.player;
    }
}
