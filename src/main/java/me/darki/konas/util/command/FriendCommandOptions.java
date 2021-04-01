package me.darki.konas.util.command;

import me.darki.konas.util.SyntaxChunk;

public class FriendCommandOptions
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

    public
    FriendCommandOptions(String string) {
        super(string);
    }
}