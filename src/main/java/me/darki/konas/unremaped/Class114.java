package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class114
extends Enum {
    public static Class114 TEXTURE = new Class114("TEXTURE", 0);
    public static Class114 OUTLINE = new Class114("OUTLINE", 1);
    public static Class114 OFF = new Class114("OFF", 2);
    public static Class114[] Field2510;

    public static Class114[] Method2167() {
        return (Class114[])Field2510.clone();
    }

    public static Class114 Method2168(String string) {
        return Enum.valueOf(Class114.class, string);
    }

    static {
        Field2510 = new Class114[]{TEXTURE, OUTLINE, OFF};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class114() {
        void var2_-1;
        void var1_-1;
    }
}