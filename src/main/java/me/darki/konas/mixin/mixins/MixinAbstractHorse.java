package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class60;
import me.darki.konas.unremaped.Class72;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractHorse.class})
public class MixinAbstractHorse {
    @Inject(method={"canBeSteered"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method645(CallbackInfoReturnable<Boolean> ci) {
        Class60 entitySteerEvent = Class60.Method323((AbstractHorse)this);
        EventDispatcher.Companion.dispatch(entitySteerEvent);
        if (entitySteerEvent.isCanceled()) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method={"isHorseSaddled"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method646(CallbackInfoReturnable<Boolean> ci) {
        Class72 horseSaddledEvent = new Class72((AbstractHorse)this);
        EventDispatcher.Companion.dispatch(horseSaddledEvent);
        if (horseSaddledEvent.isCanceled()) {
            ci.setReturnValue(true);
        }
    }
}