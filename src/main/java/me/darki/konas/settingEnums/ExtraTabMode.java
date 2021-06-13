package me.darki.konas.settingEnums;

public enum ExtraTabMode {
    VANILLA("VANILLA", 0),
    PING("PING", 1),
    LENGTH("LENGTH", 2);
    public static ExtraTabMode[] Field1291;

    public static ExtraTabMode Method1312(String string) {
        return Enum.valueOf(ExtraTabMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ExtraTabMode(String a, int b) {
    }

    static {
        Field1291 = new ExtraTabMode[]{VANILLA, PING, LENGTH};
    }

    public static ExtraTabMode[] Method1313() {
        return Field1291.clone();
    }
}