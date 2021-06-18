package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.ICPacketChatMessage;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.unremaped.SendPacketEvent;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;

public class SmartWhisper
extends Module {
    public String Field803;
    public boolean Field804;
    public boolean Field805;

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block2: {
            if (SmartWhisper.mc.world == null || SmartWhisper.mc.player == null) {
                return;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketChatMessage)) break block2;
            CPacketChatMessage cPacketChatMessage = (CPacketChatMessage) sendPacketEvent.getPacket();
            String string = cPacketChatMessage.getMessage();
            if (this.Field803 != null && this.Field804 && string.split(" ")[0].equalsIgnoreCase("/r") && this.Field805) {
                ((ICPacketChatMessage)cPacketChatMessage).setMessage("/msg " + this.Field803 + " " + string.substring(3));
            }
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        String string;
        if (SmartWhisper.mc.world == null || SmartWhisper.mc.player == null) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketChat && (string = ((SPacketChat) packetEvent.getPacket()).getChatComponent().getUnformattedText()).contains("whispers: ")) {
            if (SmartWhisper.mc.ingameGUI.getChatGUI().getChatOpen() && this.Field804) {
                this.Field805 = true;
            } else {
                this.Field803 = string.split(" ")[0];
                this.Field804 = true;
            }
        }
    }

    public SmartWhisper() {
        super("SmartWhisper", Category.MISC, "SmartReply");
    }
}