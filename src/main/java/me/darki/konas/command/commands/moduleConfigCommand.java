package me.darki.konas.command.commands;

import java.io.File;
import me.darki.konas.Class167;
import me.darki.konas.ChatUtil;
import me.darki.konas.Class589;
import me.darki.konas.Class605;
import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;

public class moduleConfigCommand
extends Command {
    public moduleConfigCommand() {
        super("moduleconfig", "Load or save specific modules", new String[]{"mc"}, new SyntaxChunk("<load/save>"), new Class605("<module/friends>"), new SyntaxChunk("<config/default>"));
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            ChatUtil.Method1034(this.Method191(), new Object[0]);
            return;
        }
        File file = new File(Class589.Field2608, stringArray[3] + ".json");
        if (stringArray[3].equalsIgnoreCase("default")) {
            file = Class589.Field2607;
        }
        switch (stringArray[1].toLowerCase()) {
            case "save": {
                if (!file.exists()) {
                    ChatUtil.Method1034("Config (h)%s(r) doesn't exist!", stringArray[3]);
                    return;
                }
                if (stringArray[2].equalsIgnoreCase("friends")) {
                    Class589.Method2279(file);
                    ChatUtil.Method1033("Saved Friends to Config (h)%s", stringArray[3]);
                    break;
                }
                if (Class167.Method1612(stringArray[2]) == null) {
                    ChatUtil.Method1034("Module (h)%s(r) is invalid!", stringArray[2]);
                }
                Class589.Method2241(Class167.Method1612(stringArray[2]), file);
                ChatUtil.Method1033("Saved Module (h)%s(r) to Config (h)%s", stringArray[2], stringArray[3]);
                break;
            }
            case "load": {
                if (!file.exists()) {
                    ChatUtil.Method1034("Config (h)%s(r) doesn't exist!", stringArray[3]);
                    return;
                }
                if (stringArray[2].equalsIgnoreCase("friends")) {
                    Class589.Method2247(file);
                    ChatUtil.Method1033("Loaded Friends from Config (h)%s", stringArray[3]);
                    break;
                }
                if (Class167.Method1612(stringArray[2]) == null) {
                    ChatUtil.Method1034("Module (h)%s(r) is invalid", stringArray[2]);
                }
                Class589.Method2265(Class167.Method1612(stringArray[2]), file);
                ChatUtil.Method1033("Loaded Module (h)%s(r) from Config (h)%s", stringArray[2], stringArray[3]);
                break;
            }
            default: {
                ChatUtil.Method1034(this.Method191(), new Object[0]);
                break;
            }
        }
    }
}
