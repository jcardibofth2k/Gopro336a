package me.darki.konas.unremaped;

import java.util.ArrayList;
import java.util.List;

import me.darki.konas.RotationUtil;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.module.client.NewGui;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Class496 {
    public static Minecraft Field2166 = Minecraft.getMinecraft();

    public static void Method1957(Class490 class490, EnumHand enumHand, boolean bl) {
        Class496.Method1969(class490.Method1982(), class490.Method1981(), enumHand, class490.Method1980(), bl, true);
    }

    public static void Method1958(Class490 class490, EnumHand enumHand, boolean bl) {
        block3: {
            boolean bl2 = Class496.Field2166.player.isSprinting();
            boolean bl3 = Class545.Method1004(class490.Method1982());
            if (bl2) {
                Class496.Field2166.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class496.Field2166.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (bl3) {
                Class496.Field2166.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class496.Field2166.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            Class496.Method1957(class490, enumHand, bl);
            if (bl3) {
                Class496.Field2166.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class496.Field2166.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (!bl2) break block3;
            Class496.Field2166.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class496.Field2166.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }

    public static boolean Method1959(boolean bl) {
        if (!bl) {
            return true;
        }
        return !NewGui.INSTANCE.Field1139.Method1940();
    }

    public static boolean Method1960(BlockPos blockPos) {
        return Class496.Field2166.world.getBlockState(blockPos).getBlock().canCollideCheck(Class496.Field2166.world.getBlockState(blockPos), false) || Class288.Field1711.containsKey(blockPos);
    }

    public static Class490 Method1961(BlockPos blockPos, boolean bl, boolean bl2) {
        return Class496.Method1968(blockPos, bl, bl2, false);
    }

    public static Class490 Method1962(BlockPos blockPos, boolean bl) {
        return Class496.Method1961(blockPos, bl, false);
    }

    public static List<EnumFacing> Method1963(BlockPos blockPos, boolean bl, boolean bl2) {
        ArrayList<EnumFacing> arrayList = new ArrayList<EnumFacing>();
        for (EnumFacing enumFacing : EnumFacing.values()) {
            IBlockState iBlockState;
            BlockPos blockPos2;
            if (bl2) {
                blockPos2 = new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
                iBlockState = Class496.Field2166.world.rayTraceBlocks(Class496.Field2166.player.getPositionEyes(1.0f), (Vec3d)blockPos2);
                if (iBlockState != null && iBlockState.typeOfHit != RayTraceResult.Type.MISS) {
                    System.out.println("weary");
                    continue;
                }
            }
            blockPos2 = blockPos.offset(enumFacing);
            if (bl) {
                iBlockState = Class496.Field2166.player.getPositionEyes(1.0f);
                Vec3d vec3d = new Vec3d((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5);
                IBlockState iBlockState2 = Class496.Field2166.world.getBlockState(blockPos2);
                boolean bl3 = iBlockState2.getBlock() == Blocks.AIR || iBlockState2.isFullBlock();
                ArrayList<EnumFacing> arrayList2 = new ArrayList<EnumFacing>();
                arrayList2.addAll(Class496.Method1971(iBlockState.x - vec3d.x, EnumFacing.WEST, EnumFacing.EAST, !bl3));
                arrayList2.addAll(Class496.Method1971(iBlockState.y - vec3d.y, EnumFacing.DOWN, EnumFacing.UP, true));
                arrayList2.addAll(Class496.Method1971(iBlockState.z - vec3d.z, EnumFacing.NORTH, EnumFacing.SOUTH, !bl3));
                if (!arrayList2.contains(enumFacing.getOpposite())) continue;
            }
            if (((iBlockState = Class496.Field2166.world.getBlockState(blockPos2)) == null || !iBlockState.getBlock().canCollideCheck(iBlockState, false) || iBlockState.getMaterial().isReplaceable()) && !Class288.Field1711.containsKey(blockPos2)) continue;
            arrayList.add(enumFacing);
        }
        return arrayList;
    }

    public static Class490 Method1964(BlockPos blockPos, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        BlockPos blockPos2;
        Object object;
        EnumFacing enumFacing3;
        Vec3d vec3d;
        block4: {
            EnumFacing enumFacing2 = null;
            vec3d = null;
            double d = 69420.0;
            for (EnumFacing enumFacing3 : Class496.Method1963(blockPos, bl3, bl4)) {
                object = blockPos.offset(enumFacing3);
                Vec3d vec3d2 = new Vec3d((Vec3i)object).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing3.getDirectionVec()).scale(0.5));
                if (!(Class496.Field2166.player.getPositionVector().add(0.0, (double)Class496.Field2166.player.getEyeHeight(), 0.0).distanceTo(vec3d2) < d)) continue;
                enumFacing2 = enumFacing3;
                vec3d = vec3d2;
            }
            if (enumFacing2 == null) {
                return null;
            }
            blockPos2 = blockPos.offset(enumFacing2);
            enumFacing3 = enumFacing2.getOpposite();
            object = RotationUtil.Method1946(Class496.Field2166.player.getPositionEyes(Field2166.getRenderPartialTicks()), vec3d);
            if (!bl) break block4;
            if (bl2) {
                Class496.Field2166.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)object[0], (float)object[1], Class496.Field2166.player.onGround));
                ((IEntityPlayerSP)Class496.Field2166.player).Method237((float)object[0]);
                ((IEntityPlayerSP)Class496.Field2166.player).Method239((float)object[1]);
            } else {
                NewGui.INSTANCE.Field1139.Method1937((float)object[0], (float)object[1]);
            }
        }
        return new Class490(blockPos2, enumFacing3, vec3d, (float)object[0], (float)object[1]);
    }

    public static boolean Method1965(BlockPos blockPos, boolean bl, boolean bl2) {
        return Class496.Method1970(blockPos, bl, false, bl2);
    }

    public static boolean Method1966() {
        return !NewGui.INSTANCE.Field1139.Method1940();
    }

    public static boolean Method1967(BlockPos blockPos, boolean bl) {
        return Class496.Method1965(blockPos, bl, true);
    }

    public static Class490 Method1968(BlockPos blockPos, boolean bl, boolean bl2, boolean bl3) {
        return Class496.Method1964(blockPos, bl, bl2, bl3, false);
    }

    public static void Method1969(BlockPos blockPos, Vec3d vec3d, EnumHand enumHand, EnumFacing enumFacing, boolean bl, boolean bl2) {
        if (bl) {
            float f = (float)(vec3d.x - (double)blockPos.getX());
            float f2 = (float)(vec3d.y - (double)blockPos.getY());
            float f3 = (float)(vec3d.z - (double)blockPos.getZ());
            Class496.Field2166.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, f, f2, f3));
        } else {
            Class496.Field2166.playerController.processRightClickBlock(Class496.Field2166.player, Class496.Field2166.world, blockPos, enumFacing, vec3d, enumHand);
        }
        if (bl2) {
            Class496.Field2166.player.connection.sendPacket((Packet)new CPacketAnimation(enumHand));
        }
        ((IMinecraft)Field2166).Method57(4);
    }

    public static boolean Method1970(BlockPos blockPos, boolean bl, boolean bl2, boolean bl3) {
        Block block = Class496.Field2166.world.getBlockState(blockPos).getBlock();
        if (!(block instanceof BlockAir)) {
            if (!(block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow)) {
                return false;
            }
        }
        if (bl3) {
            for (EnumFacing enumFacing : Class496.Field2166.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos))) {
                if (enumFacing instanceof EntityItem || enumFacing instanceof EntityXPOrb) continue;
                return false;
            }
        }
        for (EnumFacing enumFacing : Class496.Method1963(blockPos, bl, bl2)) {
            if (!Class496.Method1960(blockPos.offset(enumFacing))) continue;
            return true;
        }
        return false;
    }

    public static ArrayList<EnumFacing> Method1971(double d, EnumFacing enumFacing, EnumFacing enumFacing2, boolean bl) {
        ArrayList<EnumFacing> arrayList = new ArrayList<EnumFacing>();
        if (d < -0.5) {
            arrayList.add(enumFacing);
        }
        if (d > 0.5) {
            arrayList.add(enumFacing2);
        }
        if (bl) {
            if (!arrayList.contains(enumFacing)) {
                arrayList.add(enumFacing);
            }
            if (!arrayList.contains(enumFacing2)) {
                arrayList.add(enumFacing2);
            }
        }
        return arrayList;
    }

    public static boolean Method1972(BlockPos blockPos) {
        return Class496.Field2166.world.rayTraceBlocks(new Vec3d(Class496.Field2166.player.posX, Class496.Field2166.player.posY + (double)Class496.Field2166.player.getEyeHeight(), Class496.Field2166.player.posZ), new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), false, true, false) == null;
    }
}
