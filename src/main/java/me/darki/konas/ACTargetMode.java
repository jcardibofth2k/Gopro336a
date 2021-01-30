package me.darki.konas;

public class ACTargetMode
extends Enum {
    public static ACTargetMode ALL = new ACTargetMode("ALL", 0);
    public static ACTargetMode SMART = new ACTargetMode("SMART", 1);
    public static ACTargetMode NEAREST = new ACTargetMode("NEAREST", 2);
    public static ACTargetMode[] Field1768;

    public static ACTargetMode[] Method1674() {
        return (ACTargetMode[])Field1768.clone();
    }

    public static ACTargetMode Method1675(String string) {
        return Enum.valueOf(ACTargetMode.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACTargetMode() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1768 = new ACTargetMode[]{ALL, SMART, NEAREST};
    }
}
