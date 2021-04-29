package me.darki.konas.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.darki.konas.unremaped.Class544;
import net.minecraft.client.Minecraft;

public class ChatUtil {
    public static Minecraft Field1020 = Minecraft.getMinecraft();
    public static String Field1021 = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_PURPLE + "Konas" + ChatFormatting.GRAY + "] " + ChatFormatting.RESET;

    public static void Method1028(String string, Object ... objectArray) {
        ChatUtil.Method1032(0, ChatFormatting.YELLOW, string, objectArray);
    }

    public static String Method1029(String string, ChatFormatting chatFormatting, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        string2 = string2.replaceAll("\\(h\\)", ChatFormatting.AQUA.toString());
        string2 = string2.replaceAll("\\(b\\)", ChatFormatting.BOLD.toString());
        string2 = string2.replaceAll("\\(ul\\)", ChatFormatting.UNDERLINE.toString());
        string2 = string2.replaceAll("\\(r\\)", chatFormatting.toString());
        return string2;
    }

    public static void Method1030(ChatFormatting chatFormatting, String string, Object ... objectArray) {
        ChatUtil.Method1036(0, chatFormatting, string, objectArray);
    }

    public static void Method1031() {
        ChatUtil.Method1032(0, ChatFormatting.WHITE, "", new Object[0]);
    }

    public static void U(int n, ChatFormatting chatFormatting, String string, Object ... objectArray) {
        if (ChatUtil.Field1020.world == null) {
            return;
        }
        String string2 = Field1021;
        if (chatFormatting != null) {
            string2 = string2 + chatFormatting.toString();
        }
        string2 = string2 + ChatUtil.Method1029(string, chatFormatting, objectArray);
        ChatUtil.Field1020.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new Class544(string2), n);
    }

    public static void Method1033(String string, Object ... objectArray) {
        ChatUtil.Method1032(0, ChatFormatting.WHITE, string, objectArray);
    }

    public static void Method1034(String string, Object ... objectArray) {
        ChatUtil.Method1032(0, ChatFormatting.RED, string, objectArray);
    }

    public static void Method1035(int n, String string, Object ... objectArray) {
        ChatUtil.Method1036(n, ChatFormatting.WHITE, string, objectArray);
    }

    public static void Method1036(int n, ChatFormatting chatFormatting, String string, Object ... objectArray) {
        ChatUtil.Method1032(n, chatFormatting, string, objectArray);
    }
}