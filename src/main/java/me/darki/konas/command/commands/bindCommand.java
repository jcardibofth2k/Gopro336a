package me.darki.konas.command.commands;

import me.darki.konas.Class167;
import me.darki.konas.ChatUtil;
import me.darki.konas.Class605;
import me.darki.konas.Class606;
import me.darki.konas.command.Command;
import me.darki.konas.module.Module;
import org.lwjgl.input.Keyboard;

public class bindCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        int n;
        if (stringArray.length != this.Method189().size() + 1) {
            ChatUtil.Method1034(this.Method191(), new Object[0]);
            return;
        }
        if (Class167.Method1612(stringArray[1]) == null) {
            ChatUtil.Method1034("Module not found", new Object[0]);
            return;
        }
        Module module = Class167.Method1612(stringArray[1]);
        if (Keyboard.getKeyIndex((String)stringArray[2].toUpperCase()) != 0) {
            n = Keyboard.getKeyIndex((String)stringArray[2].toUpperCase());
        } else if (stringArray[2].equalsIgnoreCase("none")) {
            n = 0;
        } else {
            ChatUtil.Method1034("Invalid key", new Object[0]);
            return;
        }
        module.setBind(n);
        ChatUtil.Method1033("Bound (h)%s(r) to (h)%s", module.getName(), Keyboard.getKeyName((int)n));
    }

    public bindCommand() {
        super("bind", "Bind key to module", new String[]{"b"}, new Class605("<Module>"), new Class606("<Bind>"));
    }
}
