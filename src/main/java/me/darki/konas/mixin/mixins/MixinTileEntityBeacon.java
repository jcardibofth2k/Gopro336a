package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class106;
import net.minecraft.tileentity.TileEntityBeacon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={TileEntityBeacon.class})
public class MixinTileEntityBeacon {
    @Inject(method={"shouldBeamRender"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method909(CallbackInfoReturnable<Float> cir) {
        Class106 event = new Class106();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            cir.setReturnValue(Float.valueOf(0.0f));
        }
    }
}