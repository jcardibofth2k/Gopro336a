package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.util.SyntaxChunk;

public class Class624
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

    public Class624(String string) {
        super(string);
    }
}