package me.darki.konas.unremaped;

import me.darki.konas.util.SyntaxChunk;

public class Class620
extends SyntaxChunk {
    public String Method177(String string) {
        if (string.toLowerCase().startsWith("a")) {
            return "add";
        }
        if (string.toLowerCase().startsWith("d")) {
            return "del";
        }
        if (string.toLowerCase().startsWith("l")) {
            return "list";
        }
        return string;
    }

    public Class620(String string) {
        super(string);
    }
}
