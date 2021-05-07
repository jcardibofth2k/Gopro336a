package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Class646
extends CancelableEvent {
    public static Class646 Field1248 = new Class646();
    public BlockPos Field1249;
    public EnumFacing Field1250;
    public int Field1251;
    public float Field1252;

    public void Method1152(BlockPos blockPos) {
        this.Field1249 = blockPos;
    }

    public EnumFacing Method1232() {
        return this.Field1250;
    }

    public float Method213() {
        return this.Field1252;
    }

    public void Method294(float f) {
        this.Field1252 = f;
    }

    public void Method1151(EnumFacing enumFacing) {
        this.Field1250 = enumFacing;
    }

    public int Method450() {
        return this.Field1251;
    }

    public static Class646 Method1233(BlockPos blockPos, EnumFacing enumFacing, int n, float f) {
        Field1248.setCanceled(false);
        Class646.Field1248.Field1249 = blockPos;
        Class646.Field1248.Field1250 = enumFacing;
        Class646.Field1248.Field1251 = n;
        Class646.Field1248.Field1252 = f;
        return Field1248;
    }

    public BlockPos Method1149() {
        return this.Field1249;
    }

    public void Method451(int n) {
        this.Field1251 = n;
    }
}