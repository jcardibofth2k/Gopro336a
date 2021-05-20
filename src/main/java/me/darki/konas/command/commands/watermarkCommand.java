package me.darki.konas.command.commands;

import me.darki.konas.KonasMod;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class watermarkCommand
extends Command {
    public watermarkCommand() {
        super("watermark", "Change the watermark", new SyntaxChunk("<watermark>"));
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        KonasMod.NAME = stringArray[1];
        Logger.Method1118("Set Watermark to &b" + stringArray[1]);
    }
}