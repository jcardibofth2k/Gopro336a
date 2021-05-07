package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class rightClickBlockEvent
extends CancelableEvent {
    public EntityPlayerSP Field140;
    public WorldClient Field141;
    public BlockPos Field142;
    public EnumFacing Field143;
    public Vec3d Field144;
    public EnumHand Field145;

    public rightClickBlockEvent(EntityPlayerSP entityPlayerSP, WorldClient worldClient, BlockPos blockPos, EnumFacing enumFacing, Vec3d vec3d, EnumHand enumHand) {
        this.Field140 = entityPlayerSP;
        this.Field141 = worldClient;
        this.Field142 = blockPos;
        this.Field143 = enumFacing;
        this.Field144 = vec3d;
        this.Field145 = enumHand;
    }

    public EnumFacing Method250() {
        return this.Field143;
    }

    public BlockPos Method251() {
        return this.Field142;
    }

    public Vec3d Method252() {
        return this.Field144;
    }

    public WorldClient Method253() {
        return this.Field141;
    }

    public EntityPlayerSP Method254() {
        return this.Field140;
    }

    public EnumHand Method255() {
        return this.Field145;
    }
}