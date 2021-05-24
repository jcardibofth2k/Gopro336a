package me.darki.konas.unremaped;

import me.darki.konas.managers.CommandManager;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;

public class commandsCommand
extends Command {
    public static void Method553(Command command) {
        ChatUtil.Method1033("%s (h)%s(r)" + (command.Method187() != null ? "() -> %s" : ""), command.Method186(), command.Method191(), command.Method187());
    }

    @Override
    public void Method174(String[] stringArray) {
        ChatUtil.Method1033("(b)Commands:", new Object[0]);
        ChatUtil.Method1031();
        CommandManager.Method207().forEach(commandsCommand::Method553);
    }

    public commandsCommand() {
        super("commands", "List all commands", new SyntaxChunk[0]);
    }
}