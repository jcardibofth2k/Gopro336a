package me.darki.konas.settingEnums;

public enum ACSwapMode {
    OFF("OFF", 0),
    NORMAL("NORMAL", 1),
    SILENT("SILENT", 2);
    public static ACSwapMode[] Field1791;

    public static ACSwapMode Method1689(String string) {
        return Enum.valueOf(ACSwapMode.class, string);
    }

    static {
        Field1791 = new ACSwapMode[]{OFF, NORMAL, SILENT};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACSwapMode(String a, int b) {
    }

    public static ACSwapMode[] Method1690() {
        return Field1791.clone();
    }
}