package me.darki.konas;

public class ACTiming
extends Enum {
    public static ACTiming SEQUENTIAL = new ACTiming("SEQUENTIAL", 0);
    public static ACTiming ADAPTIVE = new ACTiming("ADAPTIVE", 1);
    public static ACTiming[] Field1842;

    public static ACTiming[] Method1744() {
        return (ACTiming[])Field1842.clone();
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
    public ACTiming() {
        void var2_-1;
        void var1_-1;
    }
}
