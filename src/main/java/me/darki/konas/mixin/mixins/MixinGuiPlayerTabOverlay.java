package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import java.util.List;
import kotlin.collections.CollectionsKt;
import me.darki.konas.unremaped.Class133;
import me.darki.konas.module.render.ExtraTab;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={GuiPlayerTabOverlay.class}, priority=2147463647)
public class MixinGuiPlayerTabOverlay {
    private List<NetworkPlayerInfo> Field1806 = CollectionsKt.emptyList();

    @ModifyVariable(method={"renderPlayerlist"}, at=@At(value="STORE", ordinal=0), ordinal=0)
    public List<NetworkPlayerInfo> Method1691(List<NetworkPlayerInfo> list) {
        this.Field1806 = list;
        return list;
    }

    @ModifyVariable(method={"renderPlayerlist"}, at=@At(value="STORE", ordinal=1), ordinal=0)
    public List<NetworkPlayerInfo> Method1692(List<NetworkPlayerInfo> list) {
        return ExtraTab.Method1448(this.Field1806, list);
    }

    @Redirect(method={"renderPlayerlist"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int Method1693(FontRenderer fontRenderer, String text, float x, float y, int color) {
        Class133 event = new Class133(text);
        EventDispatcher.Companion.dispatch(event);
        return fontRenderer.drawStringWithShadow(event.Method1953(), x, y, color);
    }
}