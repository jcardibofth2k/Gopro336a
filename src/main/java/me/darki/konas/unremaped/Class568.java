package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class Class568
extends CancelableEvent {
    public Block Field562;
    public BlockPos Field563;

    public Class568(Block block, BlockPos blockPos) {
        this.Field562 = block;
        this.Field563 = blockPos;
    }

    public Block Method636() {
        return this.Field562;
    }

    public BlockPos Method72() {
        return this.Field563;
    }
}