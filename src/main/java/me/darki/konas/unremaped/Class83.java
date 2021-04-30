package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.block.Block;

public class Class83
extends CancelableEvent {
    public Block Field275;

    public Class83(Block block) {
        this.Field275 = block;
    }

    public Block Method449() {
        return this.Field275;
    }
}