package me.darki.konas;

import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;

public class SeisureCommand
extends Command {
    public static boolean Field230 = false;

    public SeisureCommand() {
        super("seizure", "Gives you a seizure");
    }

    @Override
    public void Method174(String[] stringArray) {
        Field230 = true;
    }
}
