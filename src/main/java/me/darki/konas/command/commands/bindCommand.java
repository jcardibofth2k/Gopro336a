package me.darki.konas.command.commands;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.util.ChatUtil;
import me.darki.konas.unremaped.Class605;
import me.darki.konas.unremaped.Class606;
import me.darki.konas.command.Command;
import me.darki.konas.module.Module;
import org.lwjgl.input.Keyboard;

public class bindCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        int n;
        if (stringArray.length != this.Method189().size() + 1) {
            ChatUtil.Method1034(this.Method191());
            return;
        }
        if (ModuleManager.Method1612(stringArray[1]) == null) {
            ChatUtil.Method1034("Module not found");
            return;
        }
        Module module = ModuleManager.Method1612(stringArray[1]);
        if (Keyboard.getKeyIndex(stringArray[2].toUpperCase()) != 0) {
            n = Keyboard.getKeyIndex(stringArray[2].toUpperCase());
        } else if (stringArray[2].equalsIgnoreCase("none")) {
            n = 0;
        } else {
            ChatUtil.Method1034("Invalid key");
            return;
        }
        module.setBind(n);
        ChatUtil.Method1033("Bound (h)%s(r) to (h)%s", module.getName(), Keyboard.getKeyName(n));
    }

    public bindCommand() {
        super("bind", "Bind key to module", new String[]{"b"}, new Class605("<Module>"), new Class606("<Bind>"));
    }
}
