package me.darki.konas;

public class ACComfirmMode
extends Enum {
    public static ACComfirmMode OFF = new ACComfirmMode("OFF", 0);
    public static ACComfirmMode SEMI = new ACComfirmMode("SEMI", 1);
    public static ACComfirmMode FULL = new ACComfirmMode("FULL", 2);
    public static ACComfirmMode[] Field1406;

    public static ACComfirmMode Method1464(String string) {
        return Enum.valueOf(ACComfirmMode.class, string);
    }

    public static ACComfirmMode[] Method1465() {
        return (ACComfirmMode[])Field1406.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ACComfirmMode() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field1406 = new ACComfirmMode[]{OFF, SEMI, FULL};
    }
}
