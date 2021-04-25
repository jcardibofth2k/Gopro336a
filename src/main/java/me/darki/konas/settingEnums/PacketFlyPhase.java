package me.darki.konas.settingEnums;

public class PacketFlyPhase
extends Enum {
    public static PacketFlyPhase NONE = new PacketFlyPhase("NONE", 0);
    public static PacketFlyPhase SLOW = new PacketFlyPhase("SLOW", 1);
    public static PacketFlyPhase FAST = new PacketFlyPhase("FAST", 2);
    public static PacketFlyPhase[] Field2074;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public PacketFlyPhase() {
        void var2_-1;
        void var1_-1;
    }

    public static PacketFlyPhase Method1929(String string) {
        return Enum.valueOf(PacketFlyPhase.class, string);
    }

    public static PacketFlyPhase[] Method1930() {
        return Field2074.clone();
    }

    static {
        Field2074 = new PacketFlyPhase[]{NONE, SLOW, FAST};
    }
}
