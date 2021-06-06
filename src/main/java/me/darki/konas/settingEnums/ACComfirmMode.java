package me.darki.konas.settingEnums;

public enum ACComfirmMode {
    OFF("OFF", 0),
    SEMI("SEMI", 1),
    FULL("FULL", 2);
    public static ACComfirmMode[] Field1406;

    public static ACComfirmMode Method1464(String string) {
        return Enum.valueOf(ACComfirmMode.class, string);
    }

    public static ACComfirmMode[] Method1465() {
        return Field1406.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    ACComfirmMode(String a, int b) {
    }

    static {
        Field1406 = new ACComfirmMode[]{OFF, SEMI, FULL};
    }
}