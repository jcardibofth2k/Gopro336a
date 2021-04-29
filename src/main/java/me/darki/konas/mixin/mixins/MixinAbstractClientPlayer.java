package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import javax.annotation.Nullable;
import me.darki.konas.unremaped.Class8;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer {
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo Method966();

    @Inject(method={"getLocationCape"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method967(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        NetworkPlayerInfo info = this.Method966();
        if (info != null) {
            Class8 event = new Class8(info.getGameProfile().getName());
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                callbackInfoReturnable.setReturnValue(event.Method83());
            }
        }
    }
}