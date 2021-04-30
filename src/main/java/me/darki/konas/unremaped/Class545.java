package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.darki.konas.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Class545 {
    public static Minecraft Field1012 = Minecraft.getMinecraft();

    public static BlockPos Method995(BlockPos blockPos) {
        if (!(Class545.Field1012.world.getBlockState(blockPos.down()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.down()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.west()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.west()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.south()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.south()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.north()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.north()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.up()).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.up(2)).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.east().down()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.east().down()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.east(2)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.east(2)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.east().south()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.east().south()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.east().north()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.east().north()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.east()).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.east().up()).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.east().up(2)).getMaterial() != Material.AIR)) {
            return Class545.Method1007(blockPos) == null ? new BlockPos(1, 0, 0) : null;
        }
        if (!(Class545.Field1012.world.getBlockState(blockPos.down()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.down()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.west()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.west()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.east()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.east()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.north()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.north()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.up()).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.up(2)).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.south().down()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.south().down()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.south(2)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.south(2)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.south().east()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.south().east()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.south().west()).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.south().west()).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.south()).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.south().up()).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.south().up(2)).getMaterial() != Material.AIR)) {
            return Class545.Method1007(blockPos) == null ? new BlockPos(0, 0, 1) : null;
        }
        return null;
    }

    public static void Method996(BlockPos blockPos, Vec3d vec3d, EnumHand enumHand, EnumFacing enumFacing, boolean bl) {
        if (bl) {
            float f = (float)(vec3d.x - (double)blockPos.getX());
            float f2 = (float)(vec3d.y - (double)blockPos.getY());
            float f3 = (float)(vec3d.z - (double)blockPos.getZ());
            Class545.Field1012.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, f, f2, f3));
            Class545.Field1012.player.connection.sendPacket((Packet)new CPacketAnimation(enumHand));
        } else {
            Class545.Field1012.playerController.processRightClickBlock(Class545.Field1012.player, Class545.Field1012.world, blockPos, enumFacing, vec3d, enumHand);
            Class545.Field1012.player.swingArm(enumHand);
        }
    }

    public static Optional<Class534> Method997(BlockPos blockPos) {
        return Class545.Method1006(blockPos, false, false);
    }

    public static Optional<Class534> Method998(BlockPos blockPos, boolean bl) {
        return Class545.Method1006(blockPos, bl, false);
    }

    public static double[] Method999(BlockPos blockPos, EnumFacing enumFacing, EntityPlayer entityPlayer) {
        return PlayerUtil.Method1088((double)blockPos.getX() + 0.5 + (double)enumFacing.getDirectionVec().getX() * 0.5, (double)blockPos.getY() + 0.5 + (double)enumFacing.getDirectionVec().getY() * 0.5, (double)blockPos.getZ() + 0.5 + (double)enumFacing.getDirectionVec().getZ() * 0.5, entityPlayer);
    }

    public static BlockPos Method1000(Vec3d vec3d) {
        return new BlockPos(vec3d);
    }

    public static boolean Method1001(BlockPos blockPos) {
        return Class545.Field1012.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() == Material.AIR;
    }

    public static boolean Method1002(BlockPos blockPos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            BlockPos blockPos2 = blockPos.offset(enumFacing);
            if (Class545.Field1012.world.getBlockState(blockPos2).getMaterial().isReplaceable()) continue;
            return true;
        }
        return false;
    }

    public static List<BlockPos> Method1003(BlockPos blockPos, int n) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        int n2 = blockPos.getX();
        int n3 = blockPos.getY();
        int n4 = blockPos.getZ();
        for (int i = n2 - n; i <= n2 + n; ++i) {
            for (int j = n4 - n; j <= n4 + n; ++j) {
                for (int k = n3 - n; k < n3 + n; ++k) {
                    double d = (n2 - i) * (n2 - i) + (n4 - j) * (n4 - j) + (n3 - k) * (n3 - k);
                    if (!(d < (double)(n * n))) continue;
                    arrayList.add(new BlockPos(i, k, j));
                }
            }
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean Method1004(BlockPos blockPos) {
        Block block = Class545.Field1012.world.getBlockState(blockPos).getBlock();
        TileEntity tileEntity = null;
        for (TileEntity tileEntity2 : Class545.Field1012.world.loadedTileEntityList) {
            if (!tileEntity2.getPos().equals((Object)blockPos)) continue;
            tileEntity = tileEntity2;
            break;
        }
        if (tileEntity != null) return true;
        if (block instanceof BlockBed) return true;
        if (block instanceof BlockContainer) return true;
        if (block instanceof BlockDoor) return true;
        if (block instanceof BlockTrapDoor) return true;
        if (block instanceof BlockFenceGate) return true;
        if (block instanceof BlockButton) return true;
        if (block instanceof BlockAnvil) return true;
        if (block instanceof BlockWorkbench) return true;
        if (block instanceof BlockCake) return true;
        if (!(block instanceof BlockRedstoneDiode)) return false;
        return true;
    }

    public static Vec3d Method1005(BlockPos blockPos) {
        return new Vec3d((Vec3i)blockPos);
    }

    public static Optional<Class534> Method1006(BlockPos blockPos, boolean bl, boolean bl2) {
        return Class545.Method1011(blockPos, bl, false, false);
    }

    public static BlockPos Method1007(BlockPos blockPos) {
        if (Class545.Field1012.world.getBlockState(blockPos.down()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.west()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.south()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.north()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.up()).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.up(2)).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.east().down()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.east(2)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.east().south()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.east().north()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.east()).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.east().up()).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.east().up(2)).getMaterial() == Material.AIR) {
            return new BlockPos(1, 0, 0);
        }
        if (Class545.Field1012.world.getBlockState(blockPos.down()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.west()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.east()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.north()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.up()).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.up(2)).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.south().down()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.south(2)).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.south().east()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.south().west()).getBlock() == Blocks.BEDROCK && Class545.Field1012.world.getBlockState(blockPos.south()).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.south().up()).getMaterial() == Material.AIR && Class545.Field1012.world.getBlockState(blockPos.south().up(2)).getMaterial() == Material.AIR) {
            return new BlockPos(0, 0, 1);
        }
        return null;
    }

    public static double[] Method1008(double d, double d2, double d3, EnumFacing enumFacing, EntityPlayer entityPlayer) {
        return PlayerUtil.Method1088(d + 0.5 + (double)enumFacing.getDirectionVec().getX() * 0.5, d2 + 0.5 + (double)enumFacing.getDirectionVec().getY() * 0.5, d3 + 0.5 + (double)enumFacing.getDirectionVec().getZ() * 0.5, entityPlayer);
    }

    public static boolean Method1009(BlockPos blockPos) {
        return Class545.Method1010(blockPos) || Class545.Method1001(blockPos);
    }

    public static boolean Method1010(BlockPos blockPos) {
        return !(Class545.Method1001(blockPos) || Class545.Field1012.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() != Blocks.OBSIDIAN && Class545.Field1012.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() != Blocks.BEDROCK || Class545.Field1012.world.getBlockState(blockPos).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() != Material.AIR || Class545.Field1012.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() != Material.AIR);
    }

    public static Optional<Class534> Method1011(BlockPos blockPos, boolean bl, boolean bl2, boolean bl3) {
        BlockPos blockPos22;
        Block block = Class545.Field1012.world.getBlockState(blockPos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return Optional.empty();
        }
        if (!bl) {
            for (BlockPos blockPos22 : Class545.Field1012.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))) {
                if (bl3 && blockPos22 instanceof EntityEnderCrystal || blockPos22 instanceof EntityItem || blockPos22 instanceof EntityXPOrb || blockPos22 instanceof EntityArrow) continue;
                return Optional.empty();
            }
        }
        EnumFacing enumFacing = null;
        for (EnumFacing enumFacing2 : EnumFacing.values()) {
            IBlockState iBlockState;
            BlockPos blockPos3 = blockPos.offset(enumFacing2);
            if (bl2 && Class545.Field1012.world.getBlockState(blockPos3).getBlock() == Blocks.PISTON || !Class545.Field1012.world.getBlockState(blockPos3).getBlock().canCollideCheck(Class545.Field1012.world.getBlockState(blockPos3), false) || (iBlockState = Class545.Field1012.world.getBlockState(blockPos3)).getMaterial().isReplaceable()) continue;
            enumFacing = enumFacing2;
            break;
        }
        if (enumFacing == null) {
            return Optional.empty();
        }
        blockPos22 = blockPos.offset(enumFacing);
        EnumFacing enumFacing3 = enumFacing.getOpposite();
        if (!Class545.Field1012.world.getBlockState(blockPos22).getBlock().canCollideCheck(Class545.Field1012.world.getBlockState(blockPos22), false)) {
            return Optional.empty();
        }
        return Optional.of(new Class534(blockPos22, enumFacing3));
    }
}