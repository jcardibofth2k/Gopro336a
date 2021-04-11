package me.darki.konas.util;

public class MuteCommandOptions
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

    public
    MuteCommandOptions(String string) {
        super(string);
    }
}