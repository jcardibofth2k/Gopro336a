package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.misc.ChatAppend;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.mixin.mixins.IChatLine;
import me.darki.konas.mixin.mixins.IGuiNewChat;
import me.darki.konas.mixin.mixins.ISPacketChat;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatEvent;

public class KonasChat
extends Module {
    public TimerUtil Field644;
    public static boolean Field645 = true;
    public Socket Field646;
    public DataInputStream Field647;
    public DataOutputStream Field648;
    public String Field649;
    public ListenableSettingDecorator<Class324> Field650;
    public Setting<Boolean> Field651;
    public String Field652 = "kcr";
    public ConcurrentHashMap<String, String> Field653;

    public void Method714(String string) {
        if (this.Field648 == null) {
            return;
        }
        DataOutputStream dataOutputStream = this.Field648;
        String string2 = "MESSAGE";
        dataOutputStream.writeUTF(string2);
        DataOutputStream dataOutputStream2 = this.Field648;
        String string3 = string;
        dataOutputStream2.writeUTF(string3);
        DataOutputStream dataOutputStream3 = this.Field648;
        try {
            dataOutputStream3.flush();
        }
        catch (IOException iOException) {
            System.err.println("Sending message failed");
        }
    }

    public static Optional<Map.Entry<String, String>> Method715(String string) {
        Matcher matcher = Pattern.compile("^<([a-zA-Z0-9_]{3,16})> (.+)$").matcher(string);
        String string2 = null;
        String string3 = null;
        while (matcher.find()) {
            string2 = matcher.group(1);
            string3 = matcher.group(2);
        }
        if (string2 == null || string2.isEmpty()) {
            return Optional.empty();
        }
        if (string3 == null || string3.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new AbstractMap.SimpleEntry<String, String>(string2, string3));
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (KonasChat.mc.player == null || KonasChat.mc.world == null) {
            return;
        }
        if (ModuleManager.getModuleByClass(ChatAppend.class).isEnabled()) {
            ModuleManager.getModuleByClass(ChatAppend.class).Method1647(false);
        }
        ArrayList<ChatLine> arrayList = new ArrayList<ChatLine>(((IGuiNewChat) KonasChat.mc.ingameGUI.getChatGUI()).getDrawnChatLines());
        for (ChatLine chatLine : arrayList) {
            for (Map.Entry<String, String> entry : this.Field653.entrySet()) {
                String string = entry.getKey();
                String string2 = entry.getValue();
                if (!chatLine.getChatComponent().getUnformattedText().contains(string2)) continue;
                ((IChatLine)chatLine).setLineString((ITextComponent)new Class532(Command.Field122 + "bKonasChat:" + Command.Field122 + "r " + chatLine.getChatComponent().getFormattedText().replace(string2, string)));
                this.Field653.remove(string);
            }
        }
        ((IGuiNewChat) KonasChat.mc.ingameGUI.getChatGUI()).setDrawnChatLines(arrayList);
    }

    @Override
    public void onDisable() {
        this.Method124();
    }

    @Subscriber
    public void Method716(ClientChatEvent clientChatEvent) {
        String string;
        block7: {
            block6: {
                string = clientChatEvent.getMessage();
                if (string.startsWith("/") || string.startsWith(Command.Method190())) break block6;
                if (((Boolean)this.Field651.getValue()).booleanValue()) break block7;
            }
            return;
        }
        switch (Class318.Field746[((Class324)((Object)this.Field650.getValue())).ordinal()]) {
            case 1: {
                String string2 = "kcr" + this.Method717(string);
                this.Field653.put(string, string2);
                clientChatEvent.setMessage(string2);
                break;
            }
            case 2: {
                this.Method714(string);
                clientChatEvent.setCanceled(true);
                break;
            }
        }
    }

    public String Method717(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= 'a' && c <= 'm') {
                c = (char)(c + 13);
            } else if (c >= 'A' && c <= 'M') {
                c = (char)(c + 13);
            } else if (c >= 'n' && c <= 'z') {
                c = (char)(c - 13);
            } else if (c >= 'N' && c <= 'Z') {
                c = (char)(c - 13);
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void Method134() {
        block43: {
            Class315 class315;
            Class315 class3152;
            DataOutputStream dataOutputStream;
            DataInputStream dataInputStream;
            Socket socket;
            block42: {
                block41: {
                    boolean bl = Class540.Method1096();
                    if (bl) break block41;
                    String string = "Please log in to use Konas Chat";
                    Logger.Method1119(string);
                    return;
                }
                if (this.Field646 == null) break block42;
                Socket socket2 = this.Field646;
                socket2.close();
            }
            KonasChat konasChat = this;
            Socket socket3 = socket;
            Socket socket4 = socket;
            String string = "auth.konasclient.com";
            int n = 21122;
            socket3(string, n);
            konasChat.Field646 = socket4;
            KonasChat class3212 = this;
            DataInputStream dataInputStream2 = dataInputStream;
            DataInputStream dataInputStream3 = dataInputStream;
            Socket socket5 = this.Field646;
            InputStream inputStream = socket5.getInputStream();
            dataInputStream2(inputStream);
            class3212.Field647 = dataInputStream3;
            KonasChat class3213 = this;
            DataOutputStream dataOutputStream2 = dataOutputStream;
            DataOutputStream dataOutputStream3 = dataOutputStream;
            Socket socket6 = this.Field646;
            OutputStream outputStream = socket6.getOutputStream();
            dataOutputStream2(outputStream);
            class3213.Field648 = dataOutputStream3;
            Class315 class3153 = class3152;
            Class315 class3154 = class3152;
            Socket socket7 = this.Field646;
            DataInputStream dataInputStream4 = this.Field647;
            class3153(socket7, dataInputStream4);
            Class315 class3155 = class315 = class3154;
            class3155.start();
            DataOutputStream dataOutputStream4 = this.Field648;
            String string2 = "CONNECT";
            dataOutputStream4.writeUTF(string2);
            DataOutputStream dataOutputStream5 = this.Field648;
            String string3 = this.Field649;
            dataOutputStream5.writeUTF(string3);
            if (this.Field646 != null) break block43;
            String string4 = "Couldn't authenticate with Chat Server, please restart your client!";
            Logger.Method1119(string4);
            return;
        }
        DataOutputStream dataOutputStream = this.Field648;
        EntityPlayerSP entityPlayerSP = KonasChat.mc.player;
        String string = entityPlayerSP.getName();
        dataOutputStream.writeUTF(string);
        DataOutputStream dataOutputStream6 = this.Field648;
        try {
            dataOutputStream6.flush();
        }
        catch (IOException iOException) {
            System.err.println("Couldn't connect to Chat Server");
        }
    }

    public KonasChat() {
        super("KonasChat", "Encrypt chat messages among Konas Users", Category.MISC, new String[0]);
        this.Field644 = new TimerUtil();
        this.Field649 = null;
        this.Field650 = new ListenableSettingDecorator("Mode", Class324.ROT13, this::Method718);
        this.Field651 = new Setting<>("Encrypt", true);
        this.Field652 = "kcr";
        this.Field653 = new ConcurrentHashMap();
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block0: {
            Map.Entry<String, String> entry;
            Optional<Map.Entry<String, String>> optional;
            if (!(packetEvent.getPacket() instanceof SPacketChat) || !(optional = KonasChat.Method715(((SPacketChat) packetEvent.getPacket()).getChatComponent().getUnformattedText())).isPresent() || !(entry = optional.get()).getValue().startsWith("kcr")) break block0;
            String string = this.Method717(entry.getValue().substring("kcr".length()));
            ((ISPacketChat) packetEvent.getPacket()).setChatComponent((ITextComponent)new Class532(Command.Field122 + "bKonasChat: " + Command.Field122 + "f<" + entry.getKey() + "> " + Command.Field122 + "r" + string));
        }
    }

    public void Method718(Class324 class324) {
        if (class324 == Class324.PROTOCOL) {
            if (!Field645 && !this.Field644.GetDifferenceTiming(10000.0)) {
                Logger.Method1118("Please wait another " + Math.abs((System.currentTimeMillis() - this.Field644.GetCurrentTime()) / 1000L - 10L) + " seconds, before you enable this setting again!");
                this.Field650.Cancel();
            } else {
                if (this.Field649 == null) {
                    this.Field649 = Class509.Method1340();
                }
                this.Method134();
                this.Field644.UpdateCurrentTime();
            }
        } else {
            this.Method124();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onEnable() {
        if (this.Field649 == null) {
            this.Field649 = Class509.Method1340();
        }
        if (this.Field650.getValue() != Class324.PROTOCOL) return;
        if (!Field645 && !this.Field644.GetDifferenceTiming(10000.0)) {
            Logger.Method1118("Please wait another " + Math.abs((System.currentTimeMillis() - this.Field644.GetCurrentTime()) / 1000L - 10L) + " seconds, before you enable this module again!");
            this.toggle();
            return;
        }
        this.Method134();
        this.Field644.UpdateCurrentTime();
    }

    public void Method124() {
        block4: {
            if (this.Field646 == null) break block4;
            Socket socket = this.Field646;
            socket.close();
        }
        try {
            this.Field646 = null;
            this.Field647 = null;
            this.Field648 = null;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}