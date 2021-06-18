package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class AutoMount
extends Module {
    public static Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static Setting<Boolean> bypass = new Setting<>("Bypass", false);
    public static Setting<Integer> range = new Setting<>("Range", 4, 10, 1, 1);
    public static Setting<Float> delay = new Setting<>("Delay", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public static Setting<Boolean> boats = new Setting<>("Boats", false);
    public static Setting<Boolean> horses = new Setting<>("Horses", false);
    public static Setting<Boolean> skeletonHorses = new Setting<>("SkeletonHorses", false);
    public static Setting<Boolean> donkeys = new Setting<>("Donkeys", true);
    public static Setting<Boolean> pigs = new Setting<>("Pigs", false);
    public static Setting<Boolean> llamas = new Setting<>("Llamas", false);
    public TimerUtil Field1889 = new TimerUtil();
    public TimerUtil Field1890 = new TimerUtil();
    public float Field1891 = 0.0f;
    public float Field1892 = 0.0f;
    public Entity Field1893 = null;

    @Subscriber
    public void Method123(Class50 class50) {
        if (this.Field1893 != null) {
            AutoMount.mc.playerController.interactWithEntity(AutoMount.mc.player, this.Field1893, EnumHand.MAIN_HAND);
            this.Field1893 = null;
        }
    }

    public boolean Method386(Entity entity) {
        AbstractHorse abstractHorse;
        if (entity.getDistance(AutoMount.mc.player) > (float) range.getValue().intValue()) {
            return false;
        }
        if (entity instanceof AbstractHorse && (abstractHorse = (AbstractHorse)entity).isChild()) {
            return false;
        }
        if (entity instanceof EntityBoat && boats.getValue().booleanValue()) {
            return true;
        }
        if (entity instanceof EntitySkeletonHorse && skeletonHorses.getValue().booleanValue()) {
            return true;
        }
        if (entity instanceof EntityHorse && horses.getValue().booleanValue()) {
            return true;
        }
        if (entity instanceof EntityDonkey && donkeys.getValue().booleanValue()) {
            return true;
        }
        if (entity instanceof EntityPig && pigs.getValue().booleanValue()) {
            return ((EntityPig)entity).getSaddled();
        }
        if (entity instanceof EntityLlama && llamas.getValue().booleanValue()) {
            abstractHorse = (EntityLlama)entity;
            return !abstractHorse.isChild();
        }
        return false;
    }

    @Subscriber(priority=10)
    public void Method135(UpdateEvent updateEvent) {
        block2: {
            if (AutoMount.mc.player.isRiding()) {
                return;
            }
            if (!this.Field1889.GetDifferenceTiming(delay.getValue().floatValue() * 1000.0f)) {
                return;
            }
            this.Field1889.UpdateCurrentTime();
            this.Field1893 = AutoMount.mc.world.loadedEntityList.stream().filter(this::Method384).min(Comparator.comparing(AutoMount::Method389)).orElse(null);
            if (!rotate.getValue().booleanValue() || this.Field1893 == null) break block2;
            double[] dArray = PlayerUtil.Method1088(this.Field1893.posX, this.Field1893.posY, this.Field1893.posZ, AutoMount.mc.player);
            this.Field1891 = (float)dArray[0];
            this.Field1892 = (float)dArray[1];
            this.Field1890.UpdateCurrentTime();
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block4: {
            CPacketPlayer cPacketPlayer;
            CPacketUseEntity useentity;
            if (AutoMount.mc.world == null || AutoMount.mc.player == null) {
                return;
            }
            if (sendPacketEvent.getPacket() instanceof CPacketPlayer && !this.Field1890.GetDifferenceTiming(350.0)) {
                cPacketPlayer = (CPacketPlayer) sendPacketEvent.getPacket();
                if (sendPacketEvent.getPacket() instanceof CPacketPlayer.Position) {
                    sendPacketEvent.setCanceled(true);
                    AutoMount.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(cPacketPlayer.getX(AutoMount.mc.player.posX), cPacketPlayer.getY(AutoMount.mc.player.posY), cPacketPlayer.getZ(AutoMount.mc.player.posZ), this.Field1891, this.Field1892, cPacketPlayer.isOnGround()));
                } else {
                    ((ICPacketPlayer)cPacketPlayer).setYaw(this.Field1891);
                    ((ICPacketPlayer)cPacketPlayer).setPitch(this.Field1892);
                }
            }
            if (!bypass.getValue().booleanValue() || !(sendPacketEvent.getPacket() instanceof CPacketUseEntity) || !((useentity = (CPacketUseEntity) sendPacketEvent.getPacket()).getEntityFromWorld((World) AutoMount.mc.world) instanceof AbstractChestHorse) || useentity.getAction() != CPacketUseEntity.Action.INTERACT_AT) break block4;
            sendPacketEvent.Cancel();
        }
    }

    public AutoMount() {
        super("AutoMount", Category.MISC);
    }

    public boolean Method384(Entity entity) {
        return this.Method386(entity);
    }

    public static Float Method389(Entity entity) {
        return Float.valueOf(AutoMount.mc.player.getDistance(entity));
    }
}