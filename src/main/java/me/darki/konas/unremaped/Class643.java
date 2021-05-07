package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Class643 {
    public static Class643 Field1262 = new Class643();
    public Block Field1263;
    public BlockPos Field1264;
    public AxisAlignedBB Field1265;
    public List<AxisAlignedBB> Field1266;
    public Entity Field1267;

    public void Method1239(AxisAlignedBB axisAlignedBB) {
        this.Field1265 = axisAlignedBB;
    }

    public static Class643 Method1240(Block block, BlockPos blockPos, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity) {
        Class643.Field1262.Field1263 = block;
        Class643.Field1262.Field1264 = blockPos;
        Class643.Field1262.Field1265 = axisAlignedBB;
        Class643.Field1262.Field1266 = list;
        Class643.Field1262.Field1267 = entity;
        return Field1262;
    }

    public List<AxisAlignedBB> Method1241() {
        return this.Field1266;
    }

    public AxisAlignedBB Method1242() {
        return this.Field1265;
    }

    public Entity Method1243() {
        return this.Field1267;
    }

    public BlockPos Method1244() {
        return this.Field1264;
    }

    public Block Method1245() {
        return this.Field1263;
    }
}