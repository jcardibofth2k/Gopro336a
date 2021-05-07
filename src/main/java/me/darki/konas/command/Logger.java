package me.darki.konas.command;

import java.util.ConcurrentModificationException;
import me.darki.konas.unremaped.Macros;
import me.darki.konas.unremaped.Class532;
import me.darki.konas.unremaped.Class533;
import me.darki.konas.unremaped.Class538;
import me.darki.konas.unremaped.Class539;
import me.darki.konas.unremaped.Class54;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;

@Deprecated
public class Logger {
    public static String Field1085 = "&7[&5Konas&7]&f ";

    public static void Method1117(String string, int n) {
        StringBuilder stringBuilder;
        Class532 class532;
        block26: {
            block25: {
                if (Minecraft.getMinecraft().world == null) break block25;
                if (Minecraft.getMinecraft().player != null) break block26;
            }
            return;
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        GuiIngame guiIngame = minecraft.ingameGUI;
        GuiNewChat guiNewChat = guiIngame.getChatGUI();
        Class532 class5322 = class532;
        Class532 class5323 = class532;
        StringBuilder stringBuilder2 = stringBuilder;
        StringBuilder stringBuilder3 = stringBuilder;
        stringBuilder2();
        String string2 = "&7[&5Konas&7]&f ";
        StringBuilder stringBuilder4 = stringBuilder3.append(string2);
        String string3 = string;
        String string4 = "\u00c2\u00a7";
        String string5 = "\u00a7";
        String string6 = string3.replaceAll(string4, string5);
        StringBuilder stringBuilder5 = stringBuilder4.append(string6);
        String string7 = stringBuilder5.toString();
        class5322(string7);
        int n2 = n;
        try {
            guiNewChat.printChatMessageWithOptionalDeletion(class5323, n2);
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            Minecraft.getMinecraft().player.sendMessage(new Class538("&7[&5Konas&7]&f " + string.replaceAll("\u00c2\u00a7", "\u00a7")));
        }
    }

    public static void Method1118(String string) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
            return;
        }
        if (Class54.Field201 && Macros.oneLine.getValue().booleanValue()) {
            Logger.Method1117(string, 4444);
            return;
        }
        Minecraft.getMinecraft().player.sendMessage(new Class539("&7[&5Konas&7]&f " + string.replaceAll("\u00c2\u00a7", "\u00a7")));
    }

    public static void Method1119(String string) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
            return;
        }
        Minecraft.getMinecraft().player.sendMessage(new Class533("&7[&5Konas&7]&f &c" + string.replaceAll("\u00c2\u00a7", "\u00a7")));
    }

    public static void Method1120(String string) {
        Logger.Method1117(string, 69420);
    }
}
