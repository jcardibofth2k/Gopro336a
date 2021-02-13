package me.darki.konas.unremaped;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.module.Module;

public class Class605
extends SyntaxChunk {
    public Class605(String string) {
        super(string);
    }

    public String Method177(String string) {
        for (Module module : Class167.Method1619()) {
            if (module.getName().toLowerCase().startsWith(string.toLowerCase())) {
                return module.getName();
            }
            for (String string2 : module.Method1638()) {
                if (!string2.toLowerCase().startsWith(string.toLowerCase())) continue;
                return string2;
            }
        }
        return string;
    }
}
