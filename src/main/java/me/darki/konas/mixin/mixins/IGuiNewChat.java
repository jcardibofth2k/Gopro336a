package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={GuiNewChat.class})
public interface IGuiNewChat {
    @Accessor(value="drawnChatLines")
    List<ChatLine> getDrawnChatLines();

    @Accessor(value="drawnChatLines")
    void setDrawnChatLines(List <ChatLine> var1);

    @Accessor(value="scrollPos")
    int getScrollPos();

    @Accessor(value="scrollPos")
    void setScrollPos(int var1);

    @Accessor(value="isScrolled")
    boolean isScrolled();

    @Accessor(value="isScrolled")
    void setIsScrolled(boolean var1);
}