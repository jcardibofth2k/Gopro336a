package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import java.util.concurrent.atomic.AtomicBoolean;

import me.darki.konas.unremaped.Class26;
import me.darki.konas.unremaped.EntityTravelEvent;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BoatFly extends Module {
    
    public static Setting<Boolean> fixYaw = new Setting<>("FixYaw", true);
    public static Setting<Boolean> antiKick = new Setting<>("AntiKick", true);
    public static Setting<Boolean> confirm = new Setting<>("Confirm", false);
    public static Setting<Boolean> bypass = new Setting<>("Bypass", true);
    public static Setting<Boolean> semi = new Setting<>("Semi", true);
    public static Setting<Boolean> constrict = new Setting<>("Constrict", false);
    public static Setting<Float> speed = new Setting<>("Speed", 1.0f, 50.0f, 0.1f, 0.1f);
    public static Setting<Float> vSpeed = new Setting<>("VSpeed", 0.5f, 10.0f, 0.0f, 0.1f);
    public static Setting<Integer> safetyFactor = new Setting<>("SafetyFactor", 2, 10, 0, 1);
    public static Setting<Integer> maxSetbacks = new Setting<>("maxSetbacks", 10, 20, 0, 1);
    public int Field1241;
    public Vec3d vec = null;
    public int Field1243;
    public AtomicBoolean Field1244 = new AtomicBoolean(false);
    public int Field1245 = 0;

    @Subscriber
    public void onPacket(PacketEvent packetEvent) {
        if (mc.player == null || mc.world == null) {
            this.toggle();
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook && mc.player.isRiding()) {
            SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)packetEvent.getPacket();
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(mc.player.rotationPitch);
            sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
            this.Field1241 = sPacketPlayerPosLook.getTeleportId();
            if ((int)maxSetbacks.getValue() > 0) {
                if (this.vec == null) {
                    this.vec = new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                    this.Field1243 = 1;
                }
                else if (PlayerUtil.Method1080() && this.Method1221(this.vec, new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ())) < (float)speed.getValue() * 0.8) {
                    this.vec = new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                    ++this.Field1243;
                }
                else if ((mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) && this.Method1223(this.vec, new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ())) < (float)vSpeed.getValue() * 0.5) {
                    this.vec = new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                    ++this.Field1243;
                }
                else if (!mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown() && (this.Method1223(this.vec, new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ())) < 0.02 || this.Method1223(this.vec, new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ())) > 1.0)) {
                    this.vec = new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                    ++this.Field1243;
                }
                else {
                    this.vec = new Vec3d(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                    this.Field1243 = 1;
                }
            }
            if (maxSetbacks.getValue() > 0 && this.Field1243 > maxSetbacks.getValue()) {
                return;
            }
            if (mc.player.isEntityAlive() && mc.world.isBlockLoaded(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)) && !(mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.Field1241 <= 0) {
                    this.Field1241 = sPacketPlayerPosLook.getTeleportId();
                    return;
                }
                if (!confirm.getValue()) {
                    mc.player.connection.sendPacket(new CPacketConfirmTeleport(sPacketPlayerPosLook.getTeleportId()));
                }
                packetEvent.setCanceled(true);
            }
        }
        if (packetEvent.getPacket() instanceof SPacketMoveVehicle && mc.player.isRiding()) {
            if (semi.getValue()) {
                this.Field1244.set(true);
            }
            else {
                packetEvent.setCanceled(true);
            }
        }
    }

    public double Method1221(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d.x - vec3d2.x;
        double d2 = vec3d.z - vec3d2.z;
        return MathHelper.sqrt((double)(d * d + d2 * d2));
    }

    @Subscriber
    public void onSendPacket(SendPacketEvent sendPacketEvent) {
        if (mc.player == null || mc.world == null) {
            this.toggle();
            return;
        }
        if (!bypass.getValue()) {
            return;
        }
        if (sendPacketEvent.getPacket() instanceof CPacketVehicleMove) {
            if (mc.player.isRiding() && mc.player.ticksExisted % 2 == 0) {
                mc.playerController.interactWithEntity((EntityPlayer)mc.player, mc.player.getRidingEntity(), constrict.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            }
        }
        else if (sendPacketEvent.getPacket() instanceof CPacketPlayer.Rotation && mc.player.isRiding()) {
            sendPacketEvent.Cancel();
        }
        else if (sendPacketEvent.getPacket() instanceof CPacketInput && (!semi.getValue() || mc.player.ticksExisted % 2 == 0)) {
            sendPacketEvent.Cancel();
        }
    }

    @Subscriber
    public void Method1222(Class26 class26) {
        if (!mc.gameSettings.keyBindSneak.isKeyDown()) return;
        class26.setCanceled(true);
    }

    @Override
    public void onEnable() {
        this.Field1243 = 0;
        this.vec = null;
        this.Field1241 = 0;
        if (mc.player == null || mc.world == null) {
            this.toggle();
        }
    }

    public double Method1223(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d.y - vec3d2.y;
        return MathHelper.sqrt((double)(d * d));
    }

    public BoatFly() {
        super("BoatFly", Category.MOVEMENT, "AirBoat", "BoatSpeed", "BoatPlus");
    }

    @Subscriber
    public void onEntityTravel(EntityTravelEvent entityTravelEvent) {
        block15: {
            if (mc.player == null || mc.world == null) {
                this.toggle();
                return;
            }
            if (!(mc.player.getRidingEntity() instanceof EntityBoat)) break block15;
            EntityBoat entityBoat = (EntityBoat) mc.player.getRidingEntity();
            double d = 0.0;
            double d2 = 0.0;
            double d3 = 0.0;
            if (PlayerUtil.Method1080()) {
                double[] dArray = PlayerUtil.Method1086(speed.getValue());
                d = dArray[0];
                d3 = dArray[1];
            } else {
                d = 0.0;
                d3 = 0.0;
            }
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                d2 = vSpeed.getValue();
                if (antiKick.getValue() && mc.player.ticksExisted % 20 == 0) {
                    d2 = -0.04;
                }
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                d2 = -vSpeed.getValue();
            } else if (antiKick.getValue() && mc.player.ticksExisted % 4 == 0) {
                d2 = -0.04;
            }
            if (fixYaw.getValue()) {
                entityBoat.rotationYaw = mc.player.rotationYaw;
            }
            if (safetyFactor.getValue() > 0 && !mc.world.isBlockLoaded(new BlockPos(entityBoat.posX + d * (double)safetyFactor.getValue(), entityBoat.posY + d2 * (double)safetyFactor.getValue(), entityBoat.posZ + d3 * (double)safetyFactor.getValue()), false)) {
                d = 0.0;
                d3 = 0.0;
            }
            if (!semi.getValue() || mc.player.ticksExisted % 2 != 0) {
                if (this.Field1244.get() && semi.getValue()) {
                    entityBoat.setVelocity(0.0, 0.0, 0.0);
                    this.Field1244.set(false);
                } else {
                    entityBoat.setVelocity(d, d2, d3);
                }
            }
            if (confirm.getValue()) {
                ++this.Field1241;
                mc.player.connection.sendPacket(new CPacketConfirmTeleport(this.Field1241));
            }
            entityTravelEvent.setCanceled(true);
        }
    }
}