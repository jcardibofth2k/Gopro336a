package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class655;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockLiquid.class}, priority=0x7FFFFFFF)
public class MixinBlockLiquid {
    @Inject(method={"canCollideCheck"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method39(IBlockState state, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> info) {
        Class655 liquidCanCollideCheckEvent = EventDispatcher.Companion.dispatch(new Class655());
        if (liquidCanCollideCheckEvent.isCanceled()) {
            info.setReturnValue(true);
        }
    }
}