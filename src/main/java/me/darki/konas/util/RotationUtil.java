package me.darki.konas.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    public Minecraft mc = Minecraft.getMinecraft();
    public float Field2090;
    public float Field2091;
    public boolean Field2092 = false;

    public void Method1937(float f, float f2) {
        this.Field2090 = f;
        this.Field2091 = f2;
        this.Field2092 = true;
    }

    public void Method1938() {
        this.Field2090 = this.mc.player.rotationYaw;
        this.Field2091 = this.mc.player.rotationPitch;
        this.Field2092 = false;
    }

    public boolean Method1939(float f, float f2) {
        if (this.Field2092) {
            return false;
        }
        this.Method1937(f, f2);
        return true;
    }

    public boolean Method1940() {
        return this.Field2092;
    }

    public void Method1941(double d, double d2, double d3) {
        Vec3d vec3d = new Vec3d(d, d2, d3);
        this.Method1942(vec3d);
    }

    public void Method1942(Vec3d vec3d) {
        float[] fArray = RotationUtil.Method1946(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        this.Method1937(fArray[0], fArray[1]);
    }

    public void Method1943(BlockPos blockPos) {
        float[] fArray = RotationUtil.Method1946(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d((float)blockPos.getX() + 0.5f, (float)blockPos.getY() + 0.5f, (float)blockPos.getZ() + 0.5f));
        this.Method1937(fArray[0], fArray[1]);
    }

    public float Method1944() {
        return this.Field2091;
    }

    public float Method1945() {
        return this.Field2090;
    }

    public static float[] Method1946(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d2.x - vec3d.x;
        double d2 = (vec3d2.y - vec3d.y) * -1.0;
        double d3 = vec3d2.z - vec3d.z;
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        float f = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d3, d)) - 90.0);
        float f2 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d2, d4)));
        if (f2 > 90.0f) {
            f2 = 90.0f;
        } else if (f2 < -90.0f) {
            f2 = -90.0f;
        }
        return new float[]{f, f2};
    }
}