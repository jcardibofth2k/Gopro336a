package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemRenderer.class}, priority=0x7FFFFFFF)
public interface IItemRenderer {
    @Accessor(value="equippedProgressMainHand")
    public void Method2285(float var1);

    @Accessor(value="equippedProgressMainHand")
    public float Method2286();

    @Accessor(value="equippedProgressOffHand")
    public void Method2287(float var1);

    @Accessor(value="equippedProgressOffHand")
    public float Method2288();

    @Accessor(value="prevEquippedProgressMainHand")
    public void Method2289(float var1);

    @Accessor(value="prevEquippedProgressMainHand")
    public float Method2290();

    @Accessor(value="prevEquippedProgressOffHand")
    public void Method2291(float var1);

    @Accessor(value="prevEquippedProgressOffHand")
    public float Method2292();

    @Accessor(value="itemStackMainHand")
    public void Method2293(ItemStack var1);

    @Accessor(value="itemStackMainHand")
    public ItemStack Method2294();

    @Accessor(value="itemStackOffHand")
    public void Method2295(ItemStack var1);

    @Accessor(value="itemStackOffHand")
    public ItemStack Method2296();
}
