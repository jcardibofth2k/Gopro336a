package me.darki.konas.command;

import java.util.ArrayList;
import java.util.Collections;
import me.darki.konas.util.SyntaxChunk;
import net.minecraft.client.Minecraft;

public abstract class Command {
    public String Field117;
    public String Field118;
    public String[] Field119;
    public ArrayList<SyntaxChunk> Field120 = new ArrayList();
    public static String prefix = ".";
    public static char Field122 = (char)167;
    public static Minecraft Field123 = Minecraft.getMinecraft();

    public String Method186() {
        return this.Field117;
    }

    public String Method187() {
        return this.Field118;
    }

    public boolean Method188(String string) {
        if (this.Field117.equalsIgnoreCase(string)) {
            return true;
        }
        for (String string2 : this.Field119) {
            if (!string2.equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public Command(String string, String string2, SyntaxChunk ... syntaxChunkArray) {
        this.Field117 = string;
        this.Field118 = string2;
        this.Field119 = new String[0];
        Collections.addAll(this.Field120, syntaxChunkArray);
    }

    public Command(String string, String string2, String[] stringArray, SyntaxChunk ... syntaxChunkArray) {
        this.Field117 = string;
        this.Field118 = string2;
        this.Field119 = stringArray;
        Collections.addAll(this.Field120, syntaxChunkArray);
    }

    public ArrayList<SyntaxChunk> Method189() {
        return this.Field120;
    }

    public static String Method190() {
        return prefix;
    }

    public String Method191() {
        StringBuilder stringBuilder = new StringBuilder();
        this.Field120.forEach(arg_0 -> Command.Method193(stringBuilder, arg_0));
        return stringBuilder.toString();
    }

    public void Method174(String[] stringArray) {
    }

    public String[] Method192() {
        return this.Field119;
    }

    public static void Method193(StringBuilder stringBuilder, SyntaxChunk syntaxChunk) {
        stringBuilder.append(syntaxChunk.Method941() + " ");
    }

    public void Method194(String[] stringArray) {
        this.Field119 = stringArray;
    }

    public void Method195(String string) {
        this.Field118 = string;
    }

    public static void Method196(String string) {
        prefix = string;
    }

    public void Method197(String string) {
        this.Field117 = string;
    }

    public String Method198(String string) {
        if (this.Field117.toLowerCase().startsWith(string)) {
            return this.Field117;
        }
        for (String string2 : this.Field119) {
            if (!string2.toLowerCase().startsWith(string)) continue;
            return string2;
        }
        return null;
    }
}
