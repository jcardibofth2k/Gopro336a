package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Objects;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.util.math.BlockPos;

public class HoleBreakAlert
extends Module {
    public static String Method1549(BlockPos blockPos) {
        double d = Math.floor(HoleBreakAlert.mc.player.posX);
        double d2 = Math.floor(HoleBreakAlert.mc.player.posZ);
        double d3 = d - (double)blockPos.getX();
        double d4 = d2 - (double)blockPos.getZ();
        switch (HoleBreakAlert.mc.player.getHorizontalFacing()) {
            case SOUTH: {
                if (d3 == 1.0) {
                    return "right";
                }
                if (d3 == -1.0) {
                    return "left";
                }
                if (d4 == 1.0) {
                    return "back";
                }
                if (d4 != -1.0) break;
                return "front";
            }
            case WEST: {
                if (d3 == 1.0) {
                    return "front";
                }
                if (d3 == -1.0) {
                    return "back";
                }
                if (d4 == 1.0) {
                    return "right";
                }
                if (d4 != -1.0) break;
                return "left";
            }
            case NORTH: {
                if (d3 == 1.0) {
                    return "left";
                }
                if (d3 == -1.0) {
                    return "right";
                }
                if (d4 == 1.0) {
                    return "front";
                }
                if (d4 != -1.0) break;
                return "back";
            }
            case EAST: {
                if (d3 == 1.0) {
                    return "back";
                }
                if (d3 == -1.0) {
                    return "front";
                }
                if (d4 == 1.0) {
                    return "left";
                }
                if (d4 != -1.0) break;
                return "right";
            }
            default: {
                return "undetermined";
            }
        }
        return null;
    }

    public HoleBreakAlert() {
        super("HoleBreakAlert", Category.MISC, "HoleBreakNotifier");
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block0: {
            SPacketBlockBreakAnim sPacketBlockBreakAnim;
            if (!(packetEvent.getPacket() instanceof SPacketBlockBreakAnim) || !this.Method515((sPacketBlockBreakAnim = (SPacketBlockBreakAnim) packetEvent.getPacket()).getPosition())) break block0;
            Logger.Method1117("The hole block to your " + HoleBreakAlert.Method1549(sPacketBlockBreakAnim.getPosition()) + " is being broken by " + Objects.requireNonNull(HoleBreakAlert.mc.world.getEntityByID(sPacketBlockBreakAnim.getBreakerId())).getName(), 44420);
        }
    }

    public boolean Method515(BlockPos blockPos) {
        double d = Math.floor(HoleBreakAlert.mc.player.posX);
        double d2 = Math.floor(HoleBreakAlert.mc.player.posZ);
        Block block = HoleBreakAlert.mc.world.getBlockState(blockPos).getBlock();
        if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN) {
            if ((double)blockPos.getX() == d + 1.0 && blockPos.getY() == HoleBreakAlert.mc.player.getPosition().getY()) {
                return true;
            }
            if ((double)blockPos.getX() == d - 1.0 && blockPos.getY() == HoleBreakAlert.mc.player.getPosition().getY()) {
                return true;
            }
            if ((double)blockPos.getZ() == d2 + 1.0 && blockPos.getY() == HoleBreakAlert.mc.player.getPosition().getY()) {
                return true;
            }
            return (double)blockPos.getZ() == d2 - 1.0 && blockPos.getY() == HoleBreakAlert.mc.player.getPosition().getY();
        }
        return false;
    }
}