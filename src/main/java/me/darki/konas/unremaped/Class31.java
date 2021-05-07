package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class Class31
extends CancelableEvent {
    public float Field168;
    public EnumHandSide Field169;
    public ItemStack Field170;

    public ItemStack Method283() {
        return this.Field170;
    }

    public float Method214() {
        return this.Field168;
    }

    public Class31(float f, EnumHandSide enumHandSide, ItemStack itemStack) {
        this.Field168 = f;
        this.Field169 = enumHandSide;
        this.Field170 = itemStack;
    }

    public EnumHandSide Method284() {
        return this.Field169;
    }
}