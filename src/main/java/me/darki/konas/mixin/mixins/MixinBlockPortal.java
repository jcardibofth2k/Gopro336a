package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.portalHitboxEvent;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockPortal.class})
public class MixinBlockPortal {
    private static final AxisAlignedBB Field114 = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

    @Inject(method={"getBoundingBox"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method147(IBlockState state, IBlockAccess source, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> cir) {
        portalHitboxEvent event = new portalHitboxEvent(state, source, pos);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            cir.setReturnValue(Field114);
        }
    }
}