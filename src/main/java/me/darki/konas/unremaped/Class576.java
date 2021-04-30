package me.darki.konas.unremaped;

import me.darki.konas.*;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class Class576
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size()) {
            if (stringArray.length != this.Method189().size() + 1) {
                Logger.Method1119(this.Method191());
                return;
            }
        }
        if (!Class589.Field2608.exists()) {
            Class589.Field2608.mkdir();
        }
        if (stringArray[1].equalsIgnoreCase("list")) {
            List<File> list = Class589.Method2220();
            ChatUtil.Method1033("(h)Configs:", new Object[0]);
            ChatUtil.Method1031();
            if (Class589.Field2610.toPath() == Class589.Field2607.toPath()) {
                ChatUtil.Method1033("(h)default", new Object[0]);
            } else {
                ChatUtil.Method1033("default", new Object[0]);
            }
            if (list != null && !list.isEmpty()) {
                for (File file : list) {
                    if (file.toPath() == Class589.Field2610.toPath()) {
                        ChatUtil.Method1033("(h)%s", Files.getNameWithoutExtension(file.getName()));
                        continue;
                    }
                    ChatUtil.Method1033("%s", Files.getNameWithoutExtension(file.getName()));
                }
            }
            return;
        }
        if (stringArray[1].equalsIgnoreCase("current")) {
            if (Class589.Field2610 == Class589.Field2607) {
                ChatUtil.Method1033("Currently selected config: (h)default", new Object[0]);
            } else {
                ChatUtil.Method1033("Currently selected config: (h)%s", Files.getNameWithoutExtension(Class589.Field2610.getName()));
            }
            return;
        }
        if (stringArray.length == 2) {
            ChatUtil.Method1034(this.Method191(), new Object[0]);
            return;
        }
        File file = new File(Class589.Field2608, stringArray[2] + ".json");
        boolean bl = stringArray[2].equalsIgnoreCase("default");
        if (bl) {
            file = Class589.Field2607;
        }
        switch (stringArray[1].toLowerCase()) {
            case "create": {
                if (file.exists()) {
                    ChatUtil.Method1034("Config (h)%s(r) already exists!", bl ? "default" : stringArray[2]);
                    return;
                }
                Class589.Method2219(Class589.Field2610);
                Class589.Method2219(file);
                Class589.Method2274(file, false);
                ChatUtil.Method1033("Created Config (h)%s", bl ? "default" : stringArray[2]);
                break;
            }
            case "save": {
                if (!file.exists()) {
                    ChatUtil.Method1034("Config (h)%s(r) doesn't exist!", bl ? "default" : stringArray[2]);
                    return;
                }
                Class589.Method2219(file);
                ChatUtil.Method1033("Saved Config (h)%s", bl ? "default" : stringArray[2]);
                break;
            }
            case "load": {
                if (!file.exists()) {
                    Object[] objectArray = new Object[1];
                    objectArray[0] = bl ? "default" : stringArray[2];
                    ChatUtil.Method1034("Config (h)%s(r) doesn't exist!", objectArray);
                    return;
                }
                Class589.Method2219(Class589.Field2610);
                Class589.Method2274(file, false);
                ChatUtil.Method1033("Loaded Config (h)%s", bl ? "default" : stringArray[2]);
                break;
            }
            case "forceload": {
                if (!file.exists()) {
                    ChatUtil.Method1034("Config (h)%s(r) doesn't exist!", bl ? "default" : stringArray[2]);
                    return;
                }
                Class589.Method2274(file, false);
                ChatUtil.Method1033("Forceloaded Config (h)%s", bl ? "default" : stringArray[2]);
                break;
            }
            case "delete": {
                if (bl) {
                    ChatUtil.Method1034("You can't delete the (h)Default(r) Config", new Object[0]);
                    return;
                }
                if (!file.exists()) {
                    ChatUtil.Method1034("Config (h)%s(r) doesn't exist!", stringArray[2]);
                    return;
                }
                if (Class589.Method2244(file)) {
                    ChatUtil.Method1033("Deleted Config (h)%s", stringArray[2]);
                    break;
                }
                ChatUtil.Method1034("Couldn't delete Config (h)%s", stringArray[2]);
                break;
            }
            default: {
                ChatUtil.Method1034(this.Method191(), new Object[0]);
                break;
            }
        }
    }

    public Class576() {
        super("config", "Load different configs", new SyntaxChunk("<create/save/load/forceload/delete/list/current>"), new SyntaxChunk("<name/default>"));
    }
}