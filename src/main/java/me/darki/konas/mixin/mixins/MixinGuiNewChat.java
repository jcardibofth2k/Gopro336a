package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class55;
import me.darki.konas.unremaped.Class647;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GuiNewChat.class}, priority=100005)
public class MixinGuiNewChat {
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void Method1582(int left, int top, int right, int bottom, int color) {
        Class647 event = new Class647();
        EventDispatcher.Companion.dispatch(event);
        if (!event.isCanceled()) {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }

    @Inject(method={"calculateChatboxHeight"}, at={@At(value="HEAD")}, cancellable=true)
    private static void Method1583(float scale, CallbackInfoReturnable<Integer> cir) {
        Class55 event = new Class55(MathHelper.floor(scale * 160.0f + 20.0f));
        EventDispatcher.Companion.dispatch(event);
        cir.setReturnValue(event.Method342());
    }
}