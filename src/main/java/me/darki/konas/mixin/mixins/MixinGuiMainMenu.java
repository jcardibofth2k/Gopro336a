package me.darki.konas.mixin.mixins;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import me.darki.konas.module.client.NewGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiMainMenu.class})
public abstract class MixinGuiMainMenu
extends GuiScreen {
    @Shadow
    private GuiButton Field13;
    @Shadow
    private GuiButton Field14;

    @Inject(method={"actionPerformed"}, at={@At(value="RETURN")})
    public void Method22(GuiButton button, CallbackInfo ci) {
        if (button.id == 8) {
            NewGui.INSTANCE.Field1132.Method1660(this);
            Minecraft.getMinecraft().displayGuiScreen(NewGui.INSTANCE.Field1132);
        } else if (button.id == 69420 && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/gpVZ4Y6cpq"));
            }
            catch (IOException | URISyntaxException exception) {
                // empty catch block
            }
        }
    }

    @Inject(method={"addSingleplayerMultiplayerButtons"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method23(int p_73969_1_, int p_73969_2_, CallbackInfo ci) {
        IGuiScreen screen = (IGuiScreen) this;
        List<GuiButton> buttonList = screen.Method2086();
        buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, 98, 20, I18n.format("menu.singleplayer")));
        buttonList.add(new GuiButton(69420, this.width / 2 + 2, p_73969_1_, 98, 20, "Discord"));
        buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, 98, 20, I18n.format("menu.multiplayer")));
        buttonList.add(new GuiButton(8, this.width / 2 + 2, p_73969_1_ + p_73969_2_, 98, 20, "Alt Manager"));
        this.Field13 = new GuiButton(14, this.width / 2 + 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("menu.online").replace("Minecraft", "").trim());
        buttonList.add(this.Field13);
        this.Field14 = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods"));
        buttonList.add(this.Field14);
        screen.Method2085(buttonList);
        ci.cancel();
    }
}
