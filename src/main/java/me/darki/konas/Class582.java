package me.darki.konas;

import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;

public class Class582
extends Command {
    public static boolean Field230 = false;

    public Class582() {
        super("seizure", "Gives you a seizure", new SyntaxChunk[0]);
    }

    @Override
    public void Method174(String[] stringArray) {
        Field230 = true;
    }
}
