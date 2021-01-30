package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={GuiNewChat.class})
public interface IGuiNewChat {
    @Accessor(value="drawnChatLines")
    public List<ChatLine> Method244();

    @Accessor(value="drawnChatLines")
    public void Method245(List<ChatLine> var1);

    @Accessor(value="scrollPos")
    public int Method246();

    @Accessor(value="scrollPos")
    public void Method247(int var1);

    @Accessor(value="isScrolled")
    public boolean Method248();

    @Accessor(value="isScrolled")
    public void Method249(boolean var1);
}
