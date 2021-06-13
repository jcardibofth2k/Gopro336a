package me.darki.konas.settingEnums;

public enum Auto32kMode {
    TICK("TICK", 0),
    ALWAYS("ALWAYS", 1);
    public static Auto32kMode[] Field1438;

    static {
        Field1438 = new Auto32kMode[]{TICK, ALWAYS};
    }

    public static Auto32kMode[] Method1496() {
        return Field1438.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    Auto32kMode(String a, int b) {
    }

    public static Auto32kMode Method1497(String string) {
        return Enum.valueOf(Auto32kMode.class, string);
    }
}