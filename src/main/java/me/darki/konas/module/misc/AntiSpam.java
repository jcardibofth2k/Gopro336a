package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.network.play.server.SPacketChat;

public class AntiSpam
extends Module {
    public static String[] Field2065 = new String[]{"discord.gg"};
    public static String[] Field2066 = new String[]{".com", ".ru", ".net", ".in", ".ir", ".au", ".uk", ".de", ".br", ".xyz", ".org", ".co", ".cc", ".me", ".tk", ".us", ".bar", ".gq", ".nl", ".space"};
    public static String[] Field2067 = new String[]{"Looking for new anarchy servers?", "I just walked", "I just flew", "I just placed", "I just ate", "I just healed", "I just took", "I just spotted", "I walked", "I flew", "I walked", "I flew", "I placed", "I ate", "I healed", "I took", "I gained", "I mined", "I lost", "I moved"};
    public static Setting<Boolean> Field2068 = new Setting<>("Discord Invites", true);
    public static Setting<Boolean> Field2069 = new Setting<>("Domains", false);
    public static Setting<Boolean> Field2070 = new Setting<>("Announcer", true);

    public boolean Method1925(String string) {
        if (Field2068.getValue().booleanValue()) {
            for (String string2 : Field2065) {
                if (!string.contains(string2)) continue;
                return true;
            }
        }
        if (Field2070.getValue().booleanValue()) {
            for (String string2 : Field2067) {
                if (!string.contains(string2)) continue;
                return true;
            }
        }
        if (Field2069.getValue().booleanValue()) {
            for (String string2 : Field2066) {
                if (!string.contains(string2)) continue;
                return true;
            }
        }
        return false;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block2: {
            if (AntiSpam.mc.world == null || AntiSpam.mc.player == null) {
                return;
            }
            if (!(packetEvent.getPacket() instanceof SPacketChat)) {
                return;
            }
            SPacketChat sPacketChat = (SPacketChat) packetEvent.getPacket();
            if (!this.Method1925(sPacketChat.getChatComponent().getUnformattedText())) break block2;
            packetEvent.setCanceled(true);
        }
    }

    public AntiSpam() {
        super("AntiSpam", Category.MISC, "NoSpam", "NoLinks");
    }
}
