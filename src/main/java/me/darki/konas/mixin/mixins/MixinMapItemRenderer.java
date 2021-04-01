package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class142;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.world.storage.MapData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MapItemRenderer.class})
public class MixinMapItemRenderer {
    @Inject(method={"renderMap"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1814(MapData mapdataIn, boolean noOverlayRendering, CallbackInfo ci) {
        Class142 event = new Class142();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}