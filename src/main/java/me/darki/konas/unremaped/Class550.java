package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class Class550 {
    public static Minecraft Field833 = Minecraft.getMinecraft();

    public static void Method883(float f, float f2) {
        block17: {
            boolean bl;
            boolean bl2;
            boolean bl3 = Class550.Field833.player.isSprinting();
            if (bl3 != ((IEntityPlayerSP)Class550.Field833.player).getServerSprintState()) {
                if (bl3) {
                    Class550.Field833.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class550.Field833.player, CPacketEntityAction.Action.START_SPRINTING));
                } else {
                    Class550.Field833.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class550.Field833.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                ((IEntityPlayerSP)Class550.Field833.player).setServerSprintState(bl3);
            }
            if ((bl2 = Class550.Field833.player.isSneaking()) != ((IEntityPlayerSP)Class550.Field833.player).getServerSneakState()) {
                if (bl2) {
                    Class550.Field833.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class550.Field833.player, CPacketEntityAction.Action.START_SNEAKING));
                } else {
                    Class550.Field833.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class550.Field833.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                ((IEntityPlayerSP)Class550.Field833.player).setServerSneakState(bl2);
            }
            if (Class550.Field833.player != Field833.getRenderViewEntity()) break block17;
            AxisAlignedBB axisAlignedBB = Class550.Field833.player.getEntityBoundingBox();
            double d = Class550.Field833.player.posX - ((IEntityPlayerSP)Class550.Field833.player).getLastReportedPosX();
            double d2 = axisAlignedBB.minY - ((IEntityPlayerSP)Class550.Field833.player).getLastReportedPosY();
            double d3 = Class550.Field833.player.posZ - ((IEntityPlayerSP)Class550.Field833.player).getLastReportedPosZ();
            double d4 = f - ((IEntityPlayerSP)Class550.Field833.player).getLastReportedYaw();
            double d5 = f2 - ((IEntityPlayerSP)Class550.Field833.player).getLastReportedPitch();
            ((IEntityPlayerSP)Class550.Field833.player).setPositionUpdateTicks(((IEntityPlayerSP)Class550.Field833.player).getPositionUpdateTicks() + 1);
            boolean bl4 = d * d + d2 * d2 + d3 * d3 > 9.0E-4 || ((IEntityPlayerSP)Class550.Field833.player).getPositionUpdateTicks() >= 20;
            boolean bl5 = bl = d4 != 0.0 || d5 != 0.0;
            if (Class550.Field833.player.isRiding()) {
                Class550.Field833.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Class550.Field833.player.motionX, -999.0, Class550.Field833.player.motionZ, f, f2, Class550.Field833.player.onGround));
                bl4 = false;
            } else if (bl4 && bl) {
                Class550.Field833.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Class550.Field833.player.posX, axisAlignedBB.minY, Class550.Field833.player.posZ, f, f2, Class550.Field833.player.onGround));
            } else if (bl4) {
                Class550.Field833.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Class550.Field833.player.posX, axisAlignedBB.minY, Class550.Field833.player.posZ, Class550.Field833.player.onGround));
            } else if (bl) {
                Class550.Field833.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(f, f2, Class550.Field833.player.onGround));
            } else if (((IEntityPlayerSP)Class550.Field833.player).wasPrevOnGround() != Class550.Field833.player.onGround) {
                Class550.Field833.player.connection.sendPacket((Packet)new CPacketPlayer(Class550.Field833.player.onGround));
            }
            if (bl4) {
                ((IEntityPlayerSP)Class550.Field833.player).setLastReportedPosX(Class550.Field833.player.posX);
                ((IEntityPlayerSP)Class550.Field833.player).setLastReportedPosY(axisAlignedBB.minY);
                ((IEntityPlayerSP)Class550.Field833.player).setLastReportedPosZ(Class550.Field833.player.posZ);
                ((IEntityPlayerSP)Class550.Field833.player).setPositionUpdateTicks(0);
            }
            if (bl) {
                ((IEntityPlayerSP)Class550.Field833.player).setLastReportedYaw(f);
                ((IEntityPlayerSP)Class550.Field833.player).setLastReportedPitch(f2);
            }
            ((IEntityPlayerSP)Class550.Field833.player).setPrevOnGround(Class550.Field833.player.onGround);
            ((IEntityPlayerSP)Class550.Field833.player).setAutoJumpEnabled(Class550.Field833.gameSettings.autoJump);
        }
    }
}