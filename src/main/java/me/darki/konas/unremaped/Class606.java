package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.util.SyntaxChunk;
import org.lwjgl.input.Keyboard;

public class Class606
extends SyntaxChunk {
    public String Method177(String string) {
        for (int i = 0; i < 84; ++i) {
            int n = i;
            String string2 = Keyboard.getKeyName((int)n);
            String string3 = string2.toLowerCase();
            String string4 = string;
            String string5 = string4.toLowerCase();
            boolean bl = string3.startsWith(string5);
            if (!bl) continue;
            int n2 = i;
            try {
                return Keyboard.getKeyName((int)n2);
            }
            catch (NullPointerException nullPointerException) {
                nullPointerException.printStackTrace();
            }
        }
        return string;
    }

    public Class606(String string) {
        super(string);
    }
}