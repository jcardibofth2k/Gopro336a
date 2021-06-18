package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.ParticalEffectAddEvent;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ParticleManager.class})
public class MixinParticleManager {
    @Inject(method={"addEffect"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method447(Particle effect, CallbackInfo ci) {
        ParticalEffectAddEvent event = new ParticalEffectAddEvent(effect);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}