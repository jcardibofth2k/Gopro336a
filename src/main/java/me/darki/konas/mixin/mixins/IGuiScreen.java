package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={GuiScreen.class})
public interface IGuiScreen {
    @Accessor(value="buttonList")
    public void Method2085(List<GuiButton> var1);

    @Accessor(value="buttonList")
    public List<GuiButton> Method2086();
}
