package me.darki.konas;

import me.darki.konas.command.Command;
import me.darki.konas.managers.CommandManager;
import me.darki.konas.util.ChatUtil;

public class Class574
extends Command {
    public static void Method553(Command command) {
        ChatUtil.Method1033("%s (h)%s(r)" + (command.Method187() != null ? "() -> %s" : ""), command.Method186(), command.Method191(), command.Method187());
    }

    @Override
    public void Method174(String[] stringArray) {
        ChatUtil.Method1033("(b)Commands:");
        ChatUtil.Method1031();
        CommandManager.Method207().forEach(Class574::Method553);
    }

    public Class574() {
        super("commands", "List all commands");
    }
}
