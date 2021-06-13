package me.darki.konas.settingEnums;

public enum ACSyncMode {
    STRICT("STRICT", 0),
    MERGE("MERGE", 1);
    public static ACSyncMode[] Field1818;

    public static ACSyncMode[] Method1710() {
        return Field1818.clone();
    }

    static {
        Field1818 = new ACSyncMode[]{STRICT, MERGE};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACSyncMode(String a, int b) {
    }

    public static ACSyncMode Method1711(String string) {
        return Enum.valueOf(ACSyncMode.class, string);
    }
}