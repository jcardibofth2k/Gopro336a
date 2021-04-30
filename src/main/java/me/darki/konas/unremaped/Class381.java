package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.EnumHand;

public class Class381 {
    public static int[] Field2051 = new int[EnumHand.values().length];

    static {
        try {
            Class381.Field2051[EnumHand.MAIN_HAND.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class381.Field2051[EnumHand.OFF_HAND.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}