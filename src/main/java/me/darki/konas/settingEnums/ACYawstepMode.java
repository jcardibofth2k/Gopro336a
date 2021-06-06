package me.darki.konas.settingEnums;

public enum ACYawstepMode {
    OFF("OFF", 0),
    BREAK("BREAK", 1),
    FULL("FULL", 2);
    public static ACYawstepMode[] Field1466;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACYawstepMode(String a, int b) {
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