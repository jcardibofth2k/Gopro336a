package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class322
extends Enum {
    public static Class322 NONE = new Class322("NONE", 0);
    public static Class322 WHITELIST = new Class322("WHITELIST", 1);
    public static Class322 BLACKLIST = new Class322("BLACKLIST", 2);
    public static Class322[] Field623;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class322() {
        void var2_-1;
        void var1_-1;
    }

    public static Class322[] Method675() {
        return (Class322[])Field623.clone();
    }

    static {
        Field623 = new Class322[]{NONE, WHITELIST, BLACKLIST};
    }

    public static Class322 Method676(String string) {
        return Enum.valueOf(Class322.class, string);
    }
}