package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.ICPacketChatMessage;
import me.darki.konas.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;

public class Class313
extends Module {
    public String Field803;
    public boolean Field804;
    public boolean Field805;

    @Subscriber
    public void Method536(Class24 class24) {
        block2: {
            if (Class313.mc.world == null || Class313.mc.player == null) {
                return;
            }
            if (!(class24.getPacket() instanceof CPacketChatMessage)) break block2;
            CPacketChatMessage cPacketChatMessage = (CPacketChatMessage)class24.getPacket();
            String string = cPacketChatMessage.getMessage();
            if (this.Field803 != null && this.Field804 && string.split(" ")[0].equalsIgnoreCase("/r") && this.Field805) {
                ((ICPacketChatMessage)cPacketChatMessage).Method0("/msg " + this.Field803 + " " + string.substring(3));
            }
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        String string;
        if (Class313.mc.world == null || Class313.mc.player == null) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketChat && (string = ((SPacketChat) packetEvent.getPacket()).getChatComponent().getUnformattedText()).contains("whispers: ")) {
            if (Class313.mc.ingameGUI.getChatGUI().getChatOpen() && this.Field804) {
                this.Field805 = true;
            } else {
                this.Field803 = string.split(" ")[0];
                this.Field804 = true;
            }
        }
    }

    public Class313() {
        super("SmartWhisper", Category.MISC, "SmartReply");
    }
}
