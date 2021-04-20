package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemRenderer.class}, priority=0x7FFFFFFF)
public interface IItemRenderer {
    @Accessor(value="equippedProgressMainHand")
    void setEquippedProgressMainHand(float var1);

    @Accessor(value="equippedProgressMainHand")
    float getEquippedProgressMainHand();

    @Accessor(value="equippedProgressOffHand")
    void setEquippedProgressOffHand(float var1);

    @Accessor(value="equippedProgressOffHand")
    float getEquippedProgressOffHand();

    @Accessor(value="prevEquippedProgressMainHand")
    void setPrevEquippedProgressMainHand(float var1);

    @Accessor(value="prevEquippedProgressMainHand")
    float getPrevEquippedProgressMainHand();

    @Accessor(value="prevEquippedProgressOffHand")
    void setPrevEquippedProgressOffHand(float var1);

    @Accessor(value="prevEquippedProgressOffHand")
    float getPrevEquippedProgressOffHand();

    @Accessor(value="itemStackMainHand")
    void setItemStackMainHand(ItemStack var1);

    @Accessor(value="itemStackMainHand")
    ItemStack getItemStackMainHand();

    @Accessor(value="itemStackOffHand")
    void setItemStackOffHand(ItemStack var1);

    @Accessor(value="itemStackOffHand")
    ItemStack getItemStackOffHand();
}