package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.concurrent.atomic.AtomicBoolean;

import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
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

public class Class405
extends Module {
    public static Setting<Boolean> Field1231 = new Setting<>("FixYaw", true);
    public static Setting<Boolean> Field1232 = new Setting<>("AntiKick", true);
    public static Setting<Boolean> Field1233 = new Setting<>("Confirm", false);
    public static Setting<Boolean> Field1234 = new Setting<>("Bypass", true);
    public static Setting<Boolean> Field1235 = new Setting<>("Semi", true);
    public static Setting<Boolean> Field1236 = new Setting<>("Constrict", false);
    public static Setting<Float> Field1237 = new Setting<>("Speed", Float.valueOf(1.0f), Float.valueOf(50.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Float> Field1238 = new Setting<>("VSpeed", Float.valueOf(0.5f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public static Setting<Integer> Field1239 = new Setting<>("SafetyFactor", 2, 10, 0, 1);
    public static Setting<Integer> Field1240 = new Setting<>("MaxSetbacks", 10, 20, 0, 1);
    public int Field1241;
    public Vec3d Field1242 = null;
    public int Field1243;
    public AtomicBoolean Field1244 = new AtomicBoolean(false);
    public int Field1245 = 0;

    /*
     * Unable to fully structure code
     */
    @Subscriber
    public void Method131(PacketEvent var1_1) {
        block14: {
            block9: {
                block10: {
                    block13: {
                        block12: {
                            block11: {
                                if (Class405.mc.player == null || Class405.mc.world == null) {
                                    this.toggle();
                                    return;
                                }
                                if (!(var1_1.getPacket() instanceof SPacketPlayerPosLook) || !Class405.mc.player.isRiding()) break block9;
                                var2_2 = (SPacketPlayerPosLook)var1_1.getPacket();
                                ((ISPacketPlayerPosLook)var2_2).Method40(Class405.mc.player.rotationYaw);
                                ((ISPacketPlayerPosLook)var2_2).Method41(Class405.mc.player.rotationPitch);
                                var2_2.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
                                var2_2.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
                                this.Field1241 = var2_2.getTeleportId();
                                if ((Integer)Class405.Field1240.getValue() <= 0) break block10;
                                if (this.Field1242 != null) break block11;
                                this.Field1242 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                                this.Field1243 = 1;
                                break block10;
                            }
                            if (!MathUtil.Method1080()) break block12;
                            v0 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                            if (!(this.Method1221(this.Field1242, v0) < (double)((Float)Class405.Field1237.getValue()).floatValue() * 0.8)) break block12;
                            this.Field1242 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                            ++this.Field1243;
                            break block10;
                        }
                        if (!Class405.mc.gameSettings.keyBindJump.isKeyDown() && !Class405.mc.gameSettings.keyBindSneak.isKeyDown()) break block13;
                        v1 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                        if (!(this.Method1223(this.Field1242, v1) < (double)((Float)Class405.Field1238.getValue()).floatValue() * 0.5)) break block13;
                        this.Field1242 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                        ++this.Field1243;
                        break block10;
                    }
                    if (Class405.mc.gameSettings.keyBindJump.isKeyDown() || Class405.mc.gameSettings.keyBindSneak.isKeyDown()) ** GOTO lbl-1000
                    if (this.Method1223(this.Field1242, new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ())) < 0.02) ** GOTO lbl-1000
                    v2 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                    if (this.Method1223(this.Field1242, v2) > 1.0) lbl-1000:
                    // 2 sources

                    {
                        this.Field1242 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                        ++this.Field1243;
                    } else lbl-1000:
                    // 2 sources

                    {
                        this.Field1242 = new Vec3d(var2_2.getX(), var2_2.getY(), var2_2.getZ());
                        this.Field1243 = 1;
                    }
                }
                if ((Integer)Class405.Field1240.getValue() > 0 && this.Field1243 > (Integer)Class405.Field1240.getValue()) {
                    return;
                }
                if (Class405.mc.player.isEntityAlive() && Class405.mc.world.isBlockLoaded(new BlockPos(Class405.mc.player.posX, Class405.mc.player.posY, Class405.mc.player.posZ)) && !(Class405.mc.currentScreen instanceof GuiDownloadTerrain)) {
                    if (this.Field1241 <= 0) {
                        this.Field1241 = var2_2.getTeleportId();
                        return;
                    }
                    if (!((Boolean)Class405.Field1233.getValue()).booleanValue()) {
                        Class405.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(var2_2.getTeleportId()));
                    }
                    var1_1.setCanceled(true);
                }
            }
            if (!(var1_1.getPacket() instanceof SPacketMoveVehicle) || !Class405.mc.player.isRiding()) break block14;
            if (((Boolean)Class405.Field1235.getValue()).booleanValue()) {
                this.Field1244.set(true);
            } else {
                var1_1.setCanceled(true);
            }
        }
    }

    public double Method1221(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d.x - vec3d2.x;
        double d2 = vec3d.z - vec3d2.z;
        return MathHelper.sqrt((double)(d * d + d2 * d2));
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block3: {
            block4: {
                block2: {
                    if (Class405.mc.player == null || Class405.mc.world == null) {
                        this.toggle();
                        return;
                    }
                    if (!((Boolean)Field1234.getValue()).booleanValue()) {
                        return;
                    }
                    if (!(class24.getPacket() instanceof CPacketVehicleMove)) break block2;
                    if (!Class405.mc.player.isRiding() || Class405.mc.player.ticksExisted % 2 != 0) break block3;
                    Class405.mc.playerController.interactWithEntity((EntityPlayer)Class405.mc.player, Class405.mc.player.getRidingEntity(), (Boolean)Field1236.getValue() != false ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    break block3;
                }
                if (!(class24.getPacket() instanceof CPacketPlayer.Rotation) || !Class405.mc.player.isRiding()) break block4;
                class24.Cancel();
                break block3;
            }
            if (!(class24.getPacket() instanceof CPacketInput) || ((Boolean)Field1235.getValue()).booleanValue() && Class405.mc.player.ticksExisted % 2 != 0) break block3;
            class24.Cancel();
        }
    }

    @Subscriber
    public void Method1222(Class26 class26) {
        block0: {
            if (!Class405.mc.gameSettings.keyBindSneak.isKeyDown()) break block0;
            class26.setCanceled(true);
        }
    }

    @Override
    public void onEnable() {
        this.Field1243 = 0;
        this.Field1242 = null;
        this.Field1241 = 0;
        if (Class405.mc.player == null || Class405.mc.world == null) {
            this.toggle();
            return;
        }
    }

    public double Method1223(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d.y - vec3d2.y;
        return MathHelper.sqrt((double)(d * d));
    }

    public Class405() {
        super("BoatFly", Category.MOVEMENT, "AirBoat", "BoatSpeed", "BoatPlus");
    }

    @Subscriber
    public void Method1224(Class21 class21) {
        block15: {
            if (Class405.mc.player == null || Class405.mc.world == null) {
                this.toggle();
                return;
            }
            if (!(Class405.mc.player.getRidingEntity() instanceof EntityBoat)) break block15;
            EntityBoat entityBoat = (EntityBoat)Class405.mc.player.getRidingEntity();
            double d = 0.0;
            double d2 = 0.0;
            double d3 = 0.0;
            if (MathUtil.Method1080()) {
                double[] dArray = MathUtil.Method1086(((Float)Field1237.getValue()).floatValue());
                d = dArray[0];
                d3 = dArray[1];
            } else {
                d = 0.0;
                d3 = 0.0;
            }
            if (Class405.mc.gameSettings.keyBindJump.isKeyDown()) {
                d2 = ((Float)Field1238.getValue()).floatValue();
                if (((Boolean)Field1232.getValue()).booleanValue() && Class405.mc.player.ticksExisted % 20 == 0) {
                    d2 = -0.04;
                }
            } else if (Class405.mc.gameSettings.keyBindSneak.isKeyDown()) {
                d2 = -((Float)Field1238.getValue()).floatValue();
            } else if (((Boolean)Field1232.getValue()).booleanValue() && Class405.mc.player.ticksExisted % 4 == 0) {
                d2 = -0.04;
            }
            if (((Boolean)Field1231.getValue()).booleanValue()) {
                entityBoat.rotationYaw = Class405.mc.player.rotationYaw;
            }
            if ((Integer)Field1239.getValue() > 0 && !Class405.mc.world.isBlockLoaded(new BlockPos(entityBoat.posX + d * (double)((Integer)Field1239.getValue()).intValue(), entityBoat.posY + d2 * (double)((Integer)Field1239.getValue()).intValue(), entityBoat.posZ + d3 * (double)((Integer)Field1239.getValue()).intValue()), false)) {
                d = 0.0;
                d3 = 0.0;
            }
            if (!((Boolean)Field1235.getValue()).booleanValue() || Class405.mc.player.ticksExisted % 2 != 0) {
                if (this.Field1244.get() && ((Boolean)Field1235.getValue()).booleanValue()) {
                    entityBoat.setVelocity(0.0, 0.0, 0.0);
                    this.Field1244.set(false);
                } else {
                    entityBoat.setVelocity(d, d2, d3);
                }
            }
            if (((Boolean)Field1233.getValue()).booleanValue()) {
                ++this.Field1241;
                Class405.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.Field1241));
            }
            class21.setCanceled(true);
        }
    }
}
