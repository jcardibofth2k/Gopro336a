package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.unremaped.Class353;
import me.darki.konas.unremaped.Class364;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.util.rotation.Rotation;
import me.darki.konas.util.RotationUtil;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class AntiAim
extends Module {
    public static Setting<Class364> mode = new Setting<>("Mode", Class364.SPIN);
    public static Setting<Class353> pitch = new Setting<>("Pitch", Class353.JITTER);
    public static Setting<Integer> speed = new Setting<>("Speed", 10, 55, 1, 1);
    public static Setting<Integer> yawAdd = new Setting<>("YawAdd", 0, 180, -180, 10);
    public float Field2533 = 0.0f;
    public float Field2534 = 0.0f;

    public static Float Method520(EntityPlayer entityPlayer) {
        return Float.valueOf(AntiAim.mc.player.getDistance((Entity)entityPlayer));
    }

    public EntityPlayer Method1054() {
        return AntiAim.mc.world.playerEntities.stream().filter(AntiAim::Method132).filter(AntiAim::Method141).filter(AntiAim::Method138).min(Comparator.comparing(AntiAim::Method520)).orElse(null);
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class492.Method1989(entityPlayer.getName());
    }

    public AntiAim() {
        super("AntiAim", "Breaks motion prediction in bad clients", Category.PLAYER, "SpinBot");
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return entityPlayer.getDistance((Entity) AntiAim.mc.player) < 10.0f;
    }

    @Subscriber(priority=0)
    public void Method135(UpdateEvent updateEvent) {
        EntityPlayer entityPlayer;
        if (updateEvent.isCanceled() || !Rotation.Method1966()) {
            return;
        }
        this.Field2533 = mode.getValue() == Class364.SPIN ? (this.Field2533 += (float)((Integer)speed.getValue()).intValue()) : (mode.getValue() == Class364.JITTER ? (Math.random() > 0.5 ? (float)((double)this.Field2533 + (double)((Integer)speed.getValue()).intValue() * Math.random()) : (float)((double)this.Field2533 - (double)((Integer)speed.getValue()).intValue() * Math.random())) : ((entityPlayer = this.Method1054()) != null ? RotationUtil.Method1946(AntiAim.mc.player.getPositionEyes(1.0f), entityPlayer.getPositionEyes(1.0f))[0] - 180.0f : AntiAim.mc.player.rotationYaw));
        this.Field2533 += (float)((Integer)yawAdd.getValue()).intValue();
        this.Field2533 = MathHelper.wrapDegrees((int)((int)this.Field2533));
        this.Field2534 = pitch.getValue() == Class353.NONE ? AntiAim.mc.player.rotationPitch : (pitch.getValue() == Class353.JITTER ? (Math.random() > 0.5 ? (float)((double)this.Field2534 + (double)((Integer)speed.getValue()).intValue() * Math.random()) : (float)((double)this.Field2534 - (double)((Integer)speed.getValue()).intValue() * Math.random())) : (pitch.getValue() == Class353.STARE ? ((entityPlayer = this.Method1054()) != null ? RotationUtil.Method1946(AntiAim.mc.player.getPositionEyes(1.0f), entityPlayer.getPositionEyes(1.0f))[1] : AntiAim.mc.player.rotationPitch) : 90.0f));
        if (this.Field2534 > 89.0f) {
            this.Field2534 = 89.0f;
        } else if (this.Field2534 < -89.0f) {
            this.Field2534 = -89.0f;
        }
        KonasGlobals.INSTANCE.Field1139.Method1937(this.Field2533, this.Field2534);
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return entityPlayer != AntiAim.mc.player;
    }
}