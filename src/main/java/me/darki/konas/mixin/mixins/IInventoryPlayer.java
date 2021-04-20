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
    void setArmorInventory(NonNullList <ItemStack> var1);

    @Accessor(value="mainInventory")
    void setMainInventory(NonNullList <ItemStack> var1);

    @Accessor(value="allInventories")
    List<NonNullList<ItemStack>> getAllInventories();
}