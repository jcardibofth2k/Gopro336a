package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.List;

import me.darki.konas.event.CancelableEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Class570
extends CancelableEvent {
    public static Class570 Field504 = new Class570();
    public Block Field505;
    public IBlockState Field506;
    public World Field507;
    public BlockPos Field508;
    public AxisAlignedBB Field509;
    public List<AxisAlignedBB> Field510;
    public Entity Field511;
    public boolean Field512;

    public BlockPos Method251() {
        return this.Field508;
    }

    public boolean Method574() {
        return this.Field512;
    }

    public World Method575() {
        return this.Field507;
    }

    public static Class570 Method576(Block block, IBlockState iBlockState, World world, BlockPos blockPos, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity, boolean bl) {
        Field504.setCanceled(false);
        Class570.Field504.Field505 = block;
        Class570.Field504.Field506 = iBlockState;
        Class570.Field504.Field507 = world;
        Class570.Field504.Field508 = blockPos;
        Class570.Field504.Field509 = axisAlignedBB;
        Class570.Field504.Field510 = list;
        Class570.Field504.Field511 = entity;
        Class570.Field504.Field512 = bl;
        return Field504;
    }

    public void Method577(AxisAlignedBB axisAlignedBB) {
        this.Field509 = axisAlignedBB;
    }

    public AxisAlignedBB Method578() {
        return this.Field509;
    }

    public Entity Method579() {
        return this.Field511;
    }

    public List<AxisAlignedBB> Method580() {
        return this.Field510;
    }

    public Block Method581() {
        return this.Field505;
    }

    public IBlockState Method582() {
        return this.Field506;
    }
}