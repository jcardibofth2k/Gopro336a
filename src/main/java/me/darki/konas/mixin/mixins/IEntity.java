package me.darki.konas.mixin.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={Entity.class}, priority=0x7FFFFFFF)
public interface IEntity {
    @Invoker(value="setFlag")
    void Method43(int var1, boolean var2);

    @Accessor(value="inPortal")
    void Method44(boolean var1);

    @Accessor(value="isInWeb")
    void Method45(boolean var1);
}