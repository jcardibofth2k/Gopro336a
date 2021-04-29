package me.darki.konas.util.command;

import me.darki.konas.util.SyntaxChunk;

public class friendSyncclients
extends SyntaxChunk {
    public String Method177(String string) {
        if (string.toLowerCase().startsWith("f")) {
            return "future";
        }
        if (string.toLowerCase().startsWith("p")) {
            return "pyro";
        }
        if (string.toLowerCase().startsWith("r")) {
            return "rusherhack";
        }
        return string;
    }

    public
    friendSyncclients(String string) {
        super(string);
    }
}