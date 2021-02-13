package me.darki.konas.unremaped;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;

public class Class574
extends Command {
    public static void Method553(Command command) {
        ChatUtil.Method1033("%s (h)%s(r)" + (command.Method187() != null ? "() -> %s" : ""), command.Method186(), command.Method191(), command.Method187());
    }

    @Override
    public void Method174(String[] stringArray) {
        ChatUtil.Method1033("(b)Commands:", new Object[0]);
        ChatUtil.Method1031();
        Class603.Method207().forEach(Class574::Method553);
    }

    public Class574() {
        super("commands", "List all commands", new SyntaxChunk[0]);
    }
}
