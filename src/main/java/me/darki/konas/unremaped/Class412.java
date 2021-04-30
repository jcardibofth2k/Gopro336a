package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class412
extends Enum {
    public static Class412 BEDROCK = new Class412("BEDROCK", 0);
    public static Class412 BOTH = new Class412("BOTH", 1);
    public static Class412[] Field1151;

    static {
        Field1151 = new Class412[]{BEDROCK, BOTH};
    }

    public static Class412[] Method1158() {
        return (Class412[])Field1151.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class412() {
        void var2_-1;
        void var1_-1;
    }

    public static Class412 Method1159(String string) {
        return Enum.valueOf(Class412.class, string);
    }
}