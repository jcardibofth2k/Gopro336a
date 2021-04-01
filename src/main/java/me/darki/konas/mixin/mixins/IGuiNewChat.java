package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={GuiNewChat.class})
public interface IGuiNewChat {
    @Accessor(value="drawnChatLines")
    List<ChatLine> Method244();

    @Accessor(value="drawnChatLines")
    void Method245(List <ChatLine> var1);

    @Accessor(value="scrollPos")
    int Method246();

    @Accessor(value="scrollPos")
    void Method247(int var1);

    @Accessor(value="isScrolled")
    boolean Method248();

    @Accessor(value="isScrolled")
    void Method249(boolean var1);
}