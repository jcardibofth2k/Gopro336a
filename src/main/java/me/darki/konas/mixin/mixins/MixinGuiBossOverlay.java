package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class642;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiBossOverlay.class})
public class MixinGuiBossOverlay {
    @Inject(method={"renderBossHealth"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method64(CallbackInfo ci) {
        Class642 bossBarEvent = Class642.Method1251();
        EventDispatcher.Companion.dispatch(bossBarEvent);
        if (bossBarEvent.isCanceled()) {
            ci.cancel();
        }
    }
}