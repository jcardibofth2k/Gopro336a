package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={GuiScreen.class})
public interface IGuiScreen {
    @Accessor(value="buttonList")
    void setButtonList(List <GuiButton> var1);

    @Accessor(value="buttonList")
    List<GuiButton> getButtonList();
}