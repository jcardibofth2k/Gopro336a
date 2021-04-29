package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketChatMessage.class})
public interface ICPacketChatMessage {
    @Accessor(value="message")
    void setMessage(String var1);
}