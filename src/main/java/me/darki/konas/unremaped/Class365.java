package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.EnumFacing;

public class Class365 {
    public static int[] Field2251 = new int[EnumFacing.values().length];

    static {
        try {
            Class365.Field2251[EnumFacing.EAST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class365.Field2251[EnumFacing.NORTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class365.Field2251[EnumFacing.WEST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class365.Field2251[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}