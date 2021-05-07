package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.item.ItemStack;

public class Class477 {
    public static float Method2169(ItemStack itemStack) {
        float f = itemStack.getMaxDamage() - itemStack.getItemDamage();
        return f / (float)itemStack.getMaxDamage() * 100.0f;
    }
}