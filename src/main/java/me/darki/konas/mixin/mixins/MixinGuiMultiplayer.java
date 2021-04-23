package me.darki.konas.mixin.mixins;

import java.io.File;
import java.util.List;
import me.darki.konas.gui.protocol.GuiProtocolSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiMultiplayer.class})
public abstract class MixinGuiMultiplayer
extends GuiScreen {
    @Inject(method={"createButtons"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method279(CallbackInfo ci) {
        IGuiScreen screen = (IGuiScreen) this;
        List<GuiButton> buttonList = screen.getButtonList();
        if (!new File(Minecraft.getMinecraft().mcDataDir, "novia").exists()) {
            buttonList.add(new GuiProtocolSlider(1200, this.width / 2 + 4 + 76 + 76, this.height - 28, 105, 20));
            screen.setButtonList(buttonList);
        }
    }
}