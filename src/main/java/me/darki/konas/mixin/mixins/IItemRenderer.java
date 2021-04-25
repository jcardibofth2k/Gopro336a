package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemRenderer.class}, priority=0x7FFFFFFF)
public interface IItemRenderer {
    @Accessor(value="equippedProgressMainHand")
    void Method2285(float var1);

    @Accessor(value="equippedProgressMainHand")
    float Method2286();

    @Accessor(value="equippedProgressOffHand")
    void Method2287(float var1);

    @Accessor(value="equippedProgressOffHand")
    float Method2288();

    @Accessor(value="prevEquippedProgressMainHand")
    void Method2289(float var1);

    @Accessor(value="prevEquippedProgressMainHand")
    float Method2290();

    @Accessor(value="prevEquippedProgressOffHand")
    void Method2291(float var1);

    @Accessor(value="prevEquippedProgressOffHand")
    float Method2292();

    @Accessor(value="itemStackMainHand")
    void Method2293(ItemStack var1);

    @Accessor(value="itemStackMainHand")
    ItemStack Method2294();

    @Accessor(value="itemStackOffHand")
    void Method2295(ItemStack var1);

    @Accessor(value="itemStackOffHand")
    ItemStack Method2296();
}
