package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class428
extends Enum {
    public static Class428 CANCEL = new Class428("CANCEL", 0);
    public static Class428 NORMAL = new Class428("NORMAL", 1);
    public static Class428 BOOST = new Class428("BOOST", 2);
    public static Class428[] Field865;

    static {
        Field865 = new Class428[]{CANCEL, NORMAL, BOOST};
    }

    public static Class428[] Method928() {
        return (Class428[])Field865.clone();
    }

    public static Class428 Method929(String string) {
        return Enum.valueOf(Class428.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class428() {
        void var2_-1;
        void var1_-1;
    }
}