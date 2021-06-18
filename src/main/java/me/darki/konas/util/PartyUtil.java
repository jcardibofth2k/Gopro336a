package me.darki.konas.util;

import me.darki.konas.*;
import me.darki.konas.util.SyntaxChunk;

public class PartyUtil
extends SyntaxChunk {
    public String Method177(String string) {
        if (string.toLowerCase().startsWith("a")) {
            return "add";
        }
        if (string.toLowerCase().startsWith("d")) {
            return "del";
        }
        if (string.toLowerCase().startsWith("c")) {
            return "clear";
        }
        if (string.toLowerCase().startsWith("l")) {
            return "list";
        }
        return string;
    }

    public PartyUtil(String string) {
        super(string);
    }
}