package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.item.ItemStack;

public class Class116
extends CancelableEvent {
    public ItemStack Field2417;
    public int Field2418;
    public int Field2419;

    public Class116(ItemStack itemStack, int n, int n2) {
        this.Field2417 = itemStack;
        this.Field2418 = n;
        this.Field2419 = n2;
    }

    public int Method2101() {
        return this.Field2419;
    }

    public ItemStack Method2102() {
        return this.Field2417;
    }

    public int Method2103() {
        return this.Field2418;
    }
}