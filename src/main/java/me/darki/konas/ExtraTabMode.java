package me.darki.konas;

public class ExtraTabMode
extends Enum {
    public static ExtraTabMode VANILLA = new ExtraTabMode("VANILLA", 0);
    public static ExtraTabMode PING = new ExtraTabMode("PING", 1);
    public static ExtraTabMode LENGTH = new ExtraTabMode("LENGTH", 2);
    public static ExtraTabMode[] Field1291;

    public static ExtraTabMode Method1312(String string) {
        return Enum.valueOf(ExtraTabMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ExtraTabMode() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1291 = new ExtraTabMode[]{VANILLA, PING, LENGTH};
    }

    public static ExtraTabMode[] Method1313() {
        return (ExtraTabMode[])Field1291.clone();
    }
}
