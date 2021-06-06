package me.darki.konas.settingEnums;

public enum ACTargetMode {
    ALL("ALL", 0),
    SMART("SMART", 1),
    NEAREST("NEAREST", 2);
    public static ACTargetMode[] Field1768;

    public static ACTargetMode[] Method1674() {
        return Field1768.clone();
    }

    public static ACTargetMode Method1675(String string) {
        return Enum.valueOf(ACTargetMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACTargetMode(String a, int b) {
    }

    static {
        Field1768 = new ACTargetMode[]{ALL, SMART, NEAREST};
    }
}