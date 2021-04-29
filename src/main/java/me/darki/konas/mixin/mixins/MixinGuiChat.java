package me.darki.konas.mixin.mixins;

import me.darki.konas.gui.screen.KonasGuiChat;
import me.darki.konas.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiChat.class})
public class MixinGuiChat {
    @Shadow
    protected GuiTextField Field17;

    @Inject(method={"keyTyped"}, at={@At(value="RETURN")})
    public void Method42(char charTyped, int keyCode, CallbackInfo ci) {
        if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat) || Minecraft.getMinecraft().currentScreen instanceof KonasGuiChat) {
            return;
        }
        if (this.Field17.getText().replaceAll(" ", "").startsWith(Command.Method190())) {
            Minecraft.getMinecraft().displayGuiScreen(new KonasGuiChat(this.Field17.getText()));
        }
    }
}