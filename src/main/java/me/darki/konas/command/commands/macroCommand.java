package me.darki.konas.command.commands;

import java.util.Arrays;
import me.darki.konas.unremaped.Class152;
import me.darki.konas.unremaped.Class157;
import me.darki.konas.unremaped.Class606;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import org.lwjgl.input.Keyboard;

public class macroCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equals("list")) {
                Logger.Method1118("&bMacros:");
                Logger.Method1118(" ");
                Class157.Method1704().forEach(macroCommand::Method1366);
            } else {
                Logger.Method1118(this.Method191());
            }
        } else if (stringArray.length == 3) {
            if (stringArray[1].equals("remove")) {
                if (Class157.Method1708(stringArray[2]) != null) {
                    Class152 class152 = Class157.Method1708(stringArray[2]);
                    Class157.Method1703(class152);
                    Logger.Method1118("Removed Macro &b" + class152.Method1760());
                } else {
                    Logger.Method1119("Couldnt find Macro &b" + stringArray[2]);
                }
            } else {
                Logger.Method1118(this.Method191());
            }
        } else if (stringArray.length >= 5) {
            if (stringArray[1].equals("add")) {
                String string = stringArray[2];
                String string2 = stringArray[3].toUpperCase();
                String string3 = String.join(" ", Arrays.copyOfRange(stringArray, 4, stringArray.length));
                if (Keyboard.getKeyIndex(string2) == 0) {
                    Logger.Method1119("Please specify a valid keybind!");
                    return;
                }
                Class152 class152 = new Class152(string, string3, Keyboard.getKeyIndex(string2));
                Class157.Method1705(class152);
                Logger.Method1118("Added Macro &b" + string + " &fwith Bind &b" + Keyboard.getKeyName(class152.Method1757()));
            } else {
                Logger.Method1118(this.Method191());
            }
        } else {
            Logger.Method1118(this.Method191());
        }
    }

    public macroCommand() {
        super("macro", "Macro", new SyntaxChunk("<add/remove/list>"), new SyntaxChunk("<name>"), new Class606("[bind]"), new SyntaxChunk("[text]"));
    }

    public static void Method1366(Class152 class152) {
        Logger.Method1118(class152.Method1760() + (class152.Method1757() != 0 ? " [&b" + Keyboard.getKeyName(class152.Method1757()) + "&f]" : "") + " {&b" + class152.Method1756() + "&f}");
    }
}
