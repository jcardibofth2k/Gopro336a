package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class348
extends Enum {
    public static Class348 NONE = new Class348("NONE", 0);
    public static Class348 WHITELIST = new Class348("WHITELIST", 1);
    public static Class348 BLACKLIST = new Class348("BLACKLIST", 2);
    public static Class348[] Field2572;

    public static Class348[] Method2203() {
        return (Class348[])Field2572.clone();
    }

    public static Class348 Method2204(String string) {
        return Enum.valueOf(Class348.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class348() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field2572 = new Class348[]{NONE, WHITELIST, BLACKLIST};
    }
}