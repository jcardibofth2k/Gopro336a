package me.darki.konas.unremaped;

import java.util.ArrayList;

import me.darki.konas.util.CrystalUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Class465 {
    public static Minecraft Field2632 = Minecraft.getMinecraft();

    public static boolean Method2283(Entity entity) {
        BlockPos blockPos = new BlockPos(entity);
        return !Class465.Method2284(blockPos).isEmpty();
    }

    public static ArrayList<BlockPos> Method2284(BlockPos blockPos) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        if (!(Class465.Field2632.world.getBlockState(blockPos).getBlock() instanceof BlockAir)) {
            return arrayList;
        }
        for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            if (Class465.Field2632.world.getBlockState(blockPos.offset(enumFacing)).getBlock() instanceof BlockAir) {
                return new ArrayList<BlockPos>();
            }
            if (!(Class465.Field2632.world.getBlockState(blockPos.offset(enumFacing)).getBlock() instanceof BlockObsidian)) continue;
            if (CrystalUtils.Method2145(blockPos.offset(enumFacing, 2).down()) && Class465.Field2632.world.getBlockState(blockPos.offset(enumFacing)).getBlock() != Blocks.AIR) {
                arrayList.add(blockPos.offset(enumFacing));
                continue;
            }
            if (!CrystalUtils.Method2145(blockPos.offset(enumFacing)) || Class465.Field2632.world.getBlockState(blockPos.offset(enumFacing)).getBlock() == Blocks.AIR || Class465.Field2632.world.getBlockState(blockPos.offset(enumFacing).down()).getBlock() != Blocks.BEDROCK && Class465.Field2632.world.getBlockState(blockPos.offset(enumFacing).down()).getBlock() != Blocks.OBSIDIAN) continue;
            arrayList.add(blockPos.offset(enumFacing));
        }
        return arrayList;
    }
}