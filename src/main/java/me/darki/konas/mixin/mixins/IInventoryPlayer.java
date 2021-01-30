package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={InventoryPlayer.class})
public interface IInventoryPlayer {
    @Accessor(value="armorInventory")
    public void Method25(NonNullList<ItemStack> var1);

    @Accessor(value="mainInventory")
    public void Method26(NonNullList<ItemStack> var1);

    @Accessor(value="allInventories")
    public List<NonNullList<ItemStack>> Method27();
}
