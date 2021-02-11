package me.darki.konas;

public class StrafeMode
extends Enum {
    public static StrafeMode STRAFE = new StrafeMode("STRAFE", 0);
    public static StrafeMode STRAFESTRICT = new StrafeMode("STRAFESTRICT", 1);
    public static StrafeMode ONGROUND = new StrafeMode("ONGROUND", 2);
    public static StrafeMode LOWHOP = new StrafeMode("LOWHOP", 3);
    public static StrafeMode SMALLHOP = new StrafeMode("SMALLHOP", 4);
    public static StrafeMode TP = new StrafeMode("TP", 5);
    public static StrafeMode STRAFEOLD = new StrafeMode("STRAFEOLD", 6);
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
    public StrafeMode() {
        void var2_-1;
        void var1_-1;
    }

    public static StrafeMode[] Method378() {
        return Field228.clone();
    }
}
