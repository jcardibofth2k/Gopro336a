package me.darki.konas.settingEnums;

public class PacketFlyMode
extends Enum {
    public static PacketFlyMode UP = new PacketFlyMode("UP", 0);
    public static PacketFlyMode PRESERVE = new PacketFlyMode("PRESERVE", 1);
    public static PacketFlyMode DOWN = new PacketFlyMode("DOWN", 2);
    public static PacketFlyMode LIMITJITTER = new PacketFlyMode("LIMITJITTER", 3);
    public static PacketFlyMode BYPASS = new PacketFlyMode("BYPASS", 4);
    public static PacketFlyMode OBSCURE = new PacketFlyMode("OBSCURE", 5);
    public static PacketFlyMode[] Field2050;

    static {
        Field2050 = new PacketFlyMode[]{UP, PRESERVE, DOWN, LIMITJITTER, BYPASS, OBSCURE};
    }

    public static PacketFlyMode[] Method1883() {
        return Field2050.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public PacketFlyMode() {
        void var2_-1;
        void var1_-1;
    }

    public static PacketFlyMode Method1884(String string) {
        return Enum.valueOf(PacketFlyMode.class, string);
    }
}
