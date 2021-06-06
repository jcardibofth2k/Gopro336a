package me.darki.konas.settingEnums;

public enum PacketFlylimit {
    NONE("NONE", 0),
    STRONG("STRONG", 1),
    STRICT("STRICT", 2);
    public static PacketFlylimit[] Field2083;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    PacketFlylimit(String a, int b) {
    }

    public static PacketFlylimit Method1935(String string) {
        return Enum.valueOf(PacketFlylimit.class, string);
    }

    public static PacketFlylimit[] Method1936() {
        return Field2083.clone();
    }

    static {
        Field2083 = new PacketFlylimit[]{NONE, STRONG, STRICT};
    }
}