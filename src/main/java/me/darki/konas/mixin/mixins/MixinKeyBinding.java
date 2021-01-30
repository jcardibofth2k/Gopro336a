package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.Class658;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={KeyBinding.class})
public class MixinKeyBinding {
    @Shadow
    private boolean Field284;

    @Inject(method={"isKeyDown"}, at={@At(value="RETURN")}, cancellable=true)
    private void Method458(CallbackInfoReturnable<Boolean> isKeyDown) {
        Class658 event = new Class658((Boolean)isKeyDown.getReturnValue(), this.Field284);
        EventDispatcher.Companion.dispatch(event);
        isKeyDown.setReturnValue((Object)event.Method278());
    }
}
