package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Class490 {
    public BlockPos Field2179;
    public EnumFacing Field2180;
    public Vec3d Field2181;
    public float Field2182;
    public float Field2183;

    public float Method1978() {
        return this.Field2183;
    }

    public Class490(BlockPos blockPos, EnumFacing enumFacing, Vec3d vec3d, float f, float f2) {
        this.Field2179 = blockPos;
        this.Field2180 = enumFacing;
        this.Field2181 = vec3d;
        this.Field2182 = f;
        this.Field2183 = f2;
    }

    public float Method1979() {
        return this.Field2182;
    }

    public EnumFacing Method1980() {
        return this.Field2180;
    }

    public Vec3d Method1981() {
        return this.Field2181;
    }

    public BlockPos Method1982() {
        return this.Field2179;
    }
}