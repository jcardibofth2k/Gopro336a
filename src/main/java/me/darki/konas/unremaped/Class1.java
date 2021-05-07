package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.*;import me.darki.konas.event.CancelableEvent;
import net.minecraft.util.math.BlockPos;

public class Class1
extends CancelableEvent {
    public BlockPos Field36;

    public BlockPos Method72() {
        return this.Field36;
    }

    public Class1(BlockPos blockPos) {
        this.Field36 = blockPos;
    }
}