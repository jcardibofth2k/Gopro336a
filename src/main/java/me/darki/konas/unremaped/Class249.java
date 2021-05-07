package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Class249 {
    public BlockPos Field2206;
    public EnumFacing Field2207;
    public IBlockState Field2208;

    public EnumFacing Method2000() {
        return this.Field2207;
    }

    public IBlockState Method2001() {
        return this.Field2208;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Class249 class249 = (Class249)object;
        return this.Field2206.equals((Object)class249.Field2206);
    }

    public BlockPos Method2002() {
        return this.Field2206;
    }

    public Class249(BlockPos blockPos, EnumFacing enumFacing) {
        this.Field2206 = blockPos;
        this.Field2207 = enumFacing;
        this.Field2208 = Module.mc.world.getBlockState(blockPos);
    }
}