package me.darki.konas.settingEnums;

public enum StrafeMode {
    STRAFE("STRAFE", 0),
    STRAFESTRICT("STRAFESTRICT", 1),
    ONGROUND("ONGROUND", 2),
    LOWHOP("LOWHOP", 3),
    SMALLHOP("SMALLHOP", 4),
    TP("TP", 5),
    STRAFEOLD("STRAFEOLD", 6);
    public static StrafeMode[] Field228;

    static {
        Field228 = new StrafeMode[]{STRAFE, STRAFESTRICT, ONGROUND, LOWHOP, SMALLHOP, TP, STRAFEOLD};
    }

    public static StrafeMode Method377(String string) {
        return Enum.valueOf(StrafeMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    StrafeMode(String a, int b) {
    }

    public static StrafeMode[] Method378() {
        return Field228.clone();
    }
}