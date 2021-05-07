package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Class11
extends CancelableEvent {
    public EntityPlayer Field146;
    public World Field147;
    public BlockPos Field148;
    public EnumFacing Field149;
    public float Field150;
    public float Field151;
    public float Field152;

    public World Method256() {
        return this.Field147;
    }

    public BlockPos Method72() {
        return this.Field148;
    }

    public EnumFacing Method257() {
        return this.Field149;
    }

    public float Method258() {
        return this.Field151;
    }

    public EntityPlayer Method259() {
        return this.Field146;
    }

    public float Method213() {
        return this.Field152;
    }

    public Class11(EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float f, float f2, float f3) {
        this.Field146 = entityPlayer;
        this.Field147 = world;
        this.Field148 = blockPos;
        this.Field149 = enumFacing;
        this.Field150 = f;
        this.Field151 = f2;
        this.Field152 = f3;
    }

    public float Method260() {
        return this.Field150;
    }
}