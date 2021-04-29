package me.darki.konas.unremaped;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Class42 {
    public ItemStack Field156;
    public EntityPlayer Field157;

    public void Method266(ItemStack itemStack) {
        this.Field156 = itemStack;
    }

    public ItemStack Method267() {
        return this.Field156;
    }

    public Class42(ItemStack itemStack, EntityPlayer entityPlayer) {
        this.Field156 = itemStack;
        this.Field157 = entityPlayer;
    }

    public void Method268(EntityPlayer entityPlayer) {
        this.Field157 = entityPlayer;
    }

    public EntityPlayer Method269() {
        return this.Field157;
    }
}