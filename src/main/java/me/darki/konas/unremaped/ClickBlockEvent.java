package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ClickBlockEvent
extends CancelableEvent {
    public EntityPlayer Field1144;
    public BlockPos Field1145;
    public EnumFacing Field1146;
    public Vec3d Field1147;

    public ClickBlockEvent(EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing, Vec3d vec3d) {
        this.Field1144 = entityPlayer;
        this.Field1145 = blockPos;
        this.Field1146 = enumFacing;
        this.Field1147 = vec3d;
    }

    public Vec3d Method1148() {
        return this.Field1147;
    }

    public BlockPos Method1149() {
        return this.Field1145;
    }

    public EnumFacing Method250() {
        return this.Field1146;
    }

    public void Method1150(Vec3d vec3d) {
        this.Field1147 = vec3d;
    }

    public void Method1151(EnumFacing enumFacing) {
        this.Field1146 = enumFacing;
    }

    public EntityPlayer Method597() {
        return this.Field1144;
    }

    public void Method1152(BlockPos blockPos) {
        this.Field1145 = blockPos;
    }
}