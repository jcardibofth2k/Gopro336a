package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class94;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TileEntityEnchantmentTableRenderer.class})
public class MixinTileEntityEnchantmentTableRenderer {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1888(TileEntityEnchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
        Class94 event = new Class94();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}