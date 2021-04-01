package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class32;
import me.darki.konas.unremaped.Class74;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityLivingBase.class}, priority=0x7FFFFFFF)
public abstract class MixinEntityLivingBase
extends MixinEntity {
    @Inject(method={"travel"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1880(float strafe, float vertical, float forward, CallbackInfo ci) {
        Class32 event = Class32.Method281((EntityLivingBase)this);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"handleJumpWater"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1881(CallbackInfo ci) {
        Class74 event = new Class74();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"handleJumpLava"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1882(CallbackInfo ci) {
        Class74 event = new Class74();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}