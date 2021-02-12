package me.darki.konas.settingEnums;

public class ACYawstepMode
extends Enum {
    public static ACYawstepMode OFF = new ACYawstepMode("OFF", 0);
    public static ACYawstepMode BREAK = new ACYawstepMode("BREAK", 1);
    public static ACYawstepMode FULL = new ACYawstepMode("FULL", 2);
    public static ACYawstepMode[] Field1466;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACYawstepMode() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1466 = new ACYawstepMode[]{OFF, BREAK, FULL};
    }

    public static ACYawstepMode Method1502(String string) {
        return Enum.valueOf(ACYawstepMode.class, string);
    }

    public static ACYawstepMode[] Method1503() {
        return Field1466.clone();
    }
}
