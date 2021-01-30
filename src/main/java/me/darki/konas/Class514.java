package me.darki.konas;

public class Class514
extends Enum {
    public static Class514 VANILLA = new Class514("VANILLA", 0);
    public static Class514 PING = new Class514("PING", 1);
    public static Class514 LENGTH = new Class514("LENGTH", 2);
    public static Class514[] Field1291;

    public static Class514 Method1312(String string) {
        return Enum.valueOf(Class514.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class514() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1291 = new Class514[]{VANILLA, PING, LENGTH};
    }

    public static Class514[] Method1313() {
        return (Class514[])Field1291.clone();
    }
}
