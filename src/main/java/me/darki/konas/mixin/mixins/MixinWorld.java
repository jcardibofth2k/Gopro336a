package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class79;
import me.darki.konas.unremaped.Class85;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={World.class})
public class MixinWorld {
    @Inject(method={"getWorldTime"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1983(CallbackInfoReturnable<Long> cir) {
        Class85 event = EventDispatcher.Companion.dispatch(new Class85());
        if (event.isCanceled()) {
            cir.setReturnValue(event.Method456());
        }
    }

    @Inject(method={"getRainStrength"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1984(float delta, CallbackInfoReturnable<Float> ci) {
        Class79 event = Class79.Method609();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
            ci.setReturnValue(Float.valueOf(0.0f));
        }
    }
}