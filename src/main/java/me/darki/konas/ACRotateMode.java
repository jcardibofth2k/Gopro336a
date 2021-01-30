package me.darki.konas;

public class ACRotateMode
extends Enum {
    public static ACRotateMode OFF = new ACRotateMode("OFF", 0);
    public static ACRotateMode TRACK = new ACRotateMode("TRACK", 1);
    public static ACRotateMode INTERACT = new ACRotateMode("INTERACT", 2);
    public static ACRotateMode[] Field536;

    public static ACRotateMode Method611(String string) {
        return Enum.valueOf(ACRotateMode.class, string);
    }

    public static ACRotateMode[] Method612() {
        return (ACRotateMode[])Field536.clone();
    }

    static {
        Field536 = new ACRotateMode[]{OFF, TRACK, INTERACT};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACRotateMode() {
        void var2_-1;
        void var1_-1;
    }
}
