package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.math.Vec3d;

public class Class442 {
    public Vec3d Field654;
    public float Field655;
    public float Field656;
    public long Field657;

    public static Vec3d Method719(Class442 class442) {
        return class442.Field654;
    }

    public Class442(Vec3d vec3d, float f, float f2) {
        this.Field654 = vec3d;
        this.Field655 = f;
        this.Field656 = f2;
        this.Field657 = System.currentTimeMillis();
    }

    public static float Method720(Class442 class442) {
        return class442.Field656;
    }

    public static float Method721(Class442 class442) {
        return class442.Field655;
    }

    public static long Method722(Class442 class442) {
        return class442.Field657;
    }
}