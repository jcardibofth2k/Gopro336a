package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.EnumFacing;

public class Class464 {
    public static int[] Field2637 = new int[EnumFacing.values().length];

    static {
        try {
            Class464.Field2637[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class464.Field2637[EnumFacing.UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class464.Field2637[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class464.Field2637[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class464.Field2637[EnumFacing.EAST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class464.Field2637[EnumFacing.WEST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}