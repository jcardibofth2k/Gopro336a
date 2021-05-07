package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class Class542 {
    public static Minecraft Field1025 = Minecraft.getMinecraft();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean Method1039(BlockPos blockPos, boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = Class542.Field1025.player.isSneaking();
        try {
            Vec3d vec3d;
            vec3d(Class542.Field1025.player.posX, Class542.Field1025.player.posY + (double)Class542.Field1025.player.getEyeHeight(), Class542.Field1025.player.posZ);
            Vec3d vec3d2 = vec3d;
            for (EnumFacing enumFacing : EnumFacing.values()) {
                Vec3d vec3d3;
                Vec3d vec3d4;
                BlockPos blockPos2 = blockPos.offset(enumFacing);
                EnumFacing enumFacing2 = enumFacing.getOpposite();
                if (Class542.Field1025.world.getBlockState(blockPos2).getBlock() == Blocks.AIR) continue;
                if (Class542.Field1025.world.getBlockState(blockPos2).getBlock() instanceof BlockLiquid) continue;
                vec3d4((Vec3i)blockPos2);
                vec3d3(enumFacing2.getDirectionVec());
                Vec3d vec3d5 = vec3d4.add(0.5, 0.5, 0.5).add(vec3d3.scale(0.5));
                if (vec3d2.squareDistanceTo(vec3d5) > 18.0625) continue;
                if (bl3) {
                    CPacketPlayer.Rotation rotation;
                    double d = vec3d5.x - vec3d2.x;
                    double d2 = vec3d5.y - vec3d2.y;
                    double d3 = vec3d5.z - vec3d2.z;
                    double d4 = Math.sqrt(d * d + d3 * d3);
                    float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f;
                    float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
                    float[] fArray = new float[2];
                    fArray[0] = Class542.Field1025.player.rotationYaw + MathHelper.wrapDegrees((float)(f - Class542.Field1025.player.rotationYaw));
                    fArray[1] = Class542.Field1025.player.rotationPitch + MathHelper.wrapDegrees((float)(f2 - Class542.Field1025.player.rotationPitch));
                    float[] fArray2 = fArray;
                    rotation(fArray2[0], fArray2[1], Class542.Field1025.player.onGround);
                    Class542.Field1025.player.connection.sendPacket((Packet)rotation);
                }
                if (bl) {
                    CPacketEntityAction cPacketEntityAction;
                    Class542.Field1025.player.setSneaking(true);
                    cPacketEntityAction((Entity)Class542.Field1025.player, CPacketEntityAction.Action.START_SNEAKING);
                    Class542.Field1025.player.connection.sendPacket((Packet)cPacketEntityAction);
                }
                boolean bl5 = false;
                Class542.Field1025.playerController.updateController();
                if (Class542.Field1025.playerController.processRightClickBlock(Class542.Field1025.player, Class542.Field1025.world, blockPos2, enumFacing2, vec3d5, EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
                    if (bl2) {
                        Class542.Field1025.player.swingArm(EnumHand.MAIN_HAND);
                    }
                    bl5 = true;
                }
                if (bl) {
                    CPacketEntityAction cPacketEntityAction;
                    cPacketEntityAction((Entity)Class542.Field1025.player, CPacketEntityAction.Action.STOP_SNEAKING);
                    Class542.Field1025.player.connection.sendPacket((Packet)cPacketEntityAction);
                }
                if (!bl5) continue;
                boolean bl6 = true;
                return bl6;
            }
            boolean bl7 = false;
            return bl7;
        }
        finally {
            Class542.Field1025.player.setSneaking(bl4);
        }
    }

    public static boolean Method1040(BlockPos blockPos, Block block, boolean bl) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            BlockPos blockPos2 = blockPos.offset(enumFacing);
            Block block2 = Class542.Field1025.world.getBlockState(blockPos2).getBlock();
            if (block2 == Blocks.AIR || block2 instanceof BlockLiquid || !block.canPlaceBlockAt((World)Class542.Field1025.world, blockPos)) continue;
            if (bl) {
                if (!Class542.Field1025.world.checkNoEntityCollision(new AxisAlignedBB(blockPos))) continue;
                return true;
            }
            return true;
        }
        return false;
    }

    public static boolean Method1041(BlockPos blockPos) {
        return Class542.Method1039(blockPos, true, true, true);
    }

    public static void Method1042(BlockPos blockPos, EnumFacing enumFacing) {
        Vec3d vec3d = new Vec3d(Class542.Field1025.player.posX, Class542.Field1025.player.posY + (double)Class542.Field1025.player.getEyeHeight(), Class542.Field1025.player.posZ);
        for (EnumFacing enumFacing2 : EnumFacing.values()) {
            Vec3d vec3d2;
            BlockPos blockPos2 = blockPos.offset(enumFacing2);
            EnumFacing enumFacing3 = enumFacing2.getOpposite();
            if (Class542.Field1025.world.getBlockState(blockPos2).getBlock() == Blocks.AIR || Class542.Field1025.world.getBlockState(blockPos2).getBlock() instanceof BlockLiquid || vec3d.squareDistanceTo(vec3d2 = new Vec3d((Vec3i)blockPos2).add(0.9, 0.9, 0.9).add(new Vec3d(enumFacing3.getDirectionVec()).scale(0.5))) > 18.0625) continue;
            double d = vec3d2.x - vec3d.x;
            double d2 = vec3d2.y - vec3d.y;
            double d3 = vec3d2.z - vec3d.z;
            double d4 = Math.sqrt(d * d + d3 * d3);
            float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f;
            float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
            float[] fArray = new float[]{Class542.Field1025.player.rotationYaw + MathHelper.wrapDegrees((float)(f - Class542.Field1025.player.rotationYaw)), Class542.Field1025.player.rotationPitch + MathHelper.wrapDegrees((float)(f2 - Class542.Field1025.player.rotationPitch))};
            Class542.Field1025.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(fArray[0], fArray[1], Class542.Field1025.player.onGround));
            Class542.Field1025.player.setSneaking(true);
            if (Class542.Field1025.playerController.processRightClickBlock(Class542.Field1025.player, Class542.Field1025.world, blockPos2, enumFacing, vec3d2, EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
                Class542.Field1025.player.swingArm(EnumHand.MAIN_HAND);
            }
            Class542.Field1025.player.setSneaking(false);
            return;
        }
    }
}