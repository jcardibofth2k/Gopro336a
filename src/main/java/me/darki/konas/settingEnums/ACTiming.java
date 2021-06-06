package me.darki.konas.settingEnums;

public enum ACTiming {
    SEQUENTIAL("SEQUENTIAL", 0),
    ADAPTIVE("ADAPTIVE", 1);
    public static ACTiming[] Field1842;

    public static ACTiming[] Method1744() {
        return Field1842.clone();
    }

    static {
        Field1842 = new ACTiming[]{SEQUENTIAL, ADAPTIVE};
    }

    public static ACTiming Method1745(String string) {
        return Enum.valueOf(ACTiming.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACTiming(String a, int b) {
    }
}