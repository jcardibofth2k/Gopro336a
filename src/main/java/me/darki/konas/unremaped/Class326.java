package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Class326
extends Enum {
    public static Class326 TOTEM = new Class326("TOTEM", 0, Items.TOTEM_OF_UNDYING);
    public static Class326 CRYSTAL = new Class326("CRYSTAL", 1, Items.END_CRYSTAL);
    public static Class326 GAPPLE = new Class326("GAPPLE", 2, Items.GOLDEN_APPLE);
    public static Class326 AIR = new Class326("AIR", 3, Items.AIR);
    public static Class326[] Field560;
    public Item Field559;

    public static Class326 Method632(String string) {
        return Enum.valueOf(Class326.class, string);
    }

    static {
        Field560 = new Class326[]{TOTEM, CRYSTAL, GAPPLE, AIR};
    }

    public static Class326[] Method633() {
        return (Class326[])Field560.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public Class326() {
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.Field559 = var3_1;
    }
}