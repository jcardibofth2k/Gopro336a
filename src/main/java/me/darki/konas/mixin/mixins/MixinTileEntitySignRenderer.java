package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class137;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TileEntitySignRenderer.class})
public class MixinTileEntitySignRenderer {
    public ITextComponent[] Field116 = null;

    @Inject(method={"render"}, at={@At(value="HEAD")})
    public void Method181(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
        Class137 event = new Class137();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            this.Field116 = new ITextComponent[te.signText.length];
            for (int i = 0; i < te.signText.length; ++i) {
                this.Field116[i] = te.signText[i];
                te.signText[i] = null;
            }
        }
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    public void Method182(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
        Class137 event = new Class137();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            System.arraycopy(this.Field116, 0, te.signText, 0, te.signText.length);
            this.Field116 = null;
        }
    }
}