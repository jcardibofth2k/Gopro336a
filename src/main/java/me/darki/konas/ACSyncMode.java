package me.darki.konas;

public class ACSyncMode
extends Enum {
    public static ACSyncMode STRICT = new ACSyncMode("STRICT", 0);
    public static ACSyncMode MERGE = new ACSyncMode("MERGE", 1);
    public static ACSyncMode[] Field1818;

    public static ACSyncMode[] Method1710() {
        return (ACSyncMode[])Field1818.clone();
    }

    static {
        Field1818 = new ACSyncMode[]{STRICT, MERGE};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACSyncMode() {
        void var2_-1;
        void var1_-1;
    }

    public static ACSyncMode Method1711(String string) {
        return Enum.valueOf(ACSyncMode.class, string);
    }
}
