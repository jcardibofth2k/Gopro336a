package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class portalHitboxEvent
extends CancelableEvent {
    public IBlockState Field218;
    public IBlockAccess Field219;
    public BlockPos Field220;

    public IBlockAccess Method347() {
        return this.Field219;
    }

    public BlockPos Method72() {
        return this.Field220;
    }

    public portalHitboxEvent(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.Field218 = iBlockState;
        this.Field219 = iBlockAccess;
        this.Field220 = blockPos;
    }

    public IBlockState Method348() {
        return this.Field218;
    }
}