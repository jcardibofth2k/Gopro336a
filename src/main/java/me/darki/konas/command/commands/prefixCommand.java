package me.darki.konas.command.commands;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class prefixCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        Command.Method196(stringArray[1]);
        Logger.Method1118("Set Prefix to &b" + prefixCommand.Method190());
    }

    public prefixCommand() {
        super("prefix", "Set the command prefix", new SyntaxChunk("<prefix>"));
    }
}
