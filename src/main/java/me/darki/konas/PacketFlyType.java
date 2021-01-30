package me.darki.konas;

public class PacketFlyType
extends Enum {
    public static PacketFlyType FACTOR = new PacketFlyType("FACTOR", 0);
    public static PacketFlyType SETBACK = new PacketFlyType("SETBACK", 1);
    public static PacketFlyType FAST = new PacketFlyType("FAST", 2);
    public static PacketFlyType SLOW = new PacketFlyType("SLOW", 3);
    public static PacketFlyType DESYNC = new PacketFlyType("DESYNC", 4);
    public static PacketFlyType[] Field2351;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public PacketFlyType() {
        void var2_-1;
        void var1_-1;
    }

    static {
        Field2351 = new PacketFlyType[]{FACTOR, SETBACK, FAST, SLOW, DESYNC};
    }

    public static PacketFlyType[] Method2061() {
        return (PacketFlyType[])Field2351.clone();
    }

    public static PacketFlyType Method2062(String string) {
        return Enum.valueOf(PacketFlyType.class, string);
    }
}
