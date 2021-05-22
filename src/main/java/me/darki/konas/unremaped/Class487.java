package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.nio.FloatBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class Class487 {
    public Minecraft Field2237 = Minecraft.getMinecraft();
    public Matrix4f Field2238 = new Matrix4f();
    public Matrix4f Field2239 = new Matrix4f();
    public ScaledResolution Field2240 = new ScaledResolution(this.Field2237);
    public Vec3d Field2241 = new Vec3d(0.0, 0.0, 0.0);

    public boolean Method2021(Vector4f vector4f, int n, int n2) {
        return 0.0 <= (double)vector4f.x && (double)vector4f.x <= (double)n && 0.0 <= (double)vector4f.y && (double)vector4f.y <= (double)n2;
    }

    public Vector4f Method2022(Vec3d vec3d) {
        Vec3d vec3d2 = this.Field2241.subtract(vec3d);
        Vector4f vector4f = new Vector4f((float)vec3d2.x, (float)vec3d2.y, (float)vec3d2.z, 1.0f);
        this.Method2024(vector4f, this.Field2238);
        this.Method2024(vector4f, this.Field2239);
        if (vector4f.w > 0.0f) {
            vector4f.x *= -100000.0f;
            vector4f.y *= -100000.0f;
        } else {
            float f = 1.0f / vector4f.w;
            vector4f.x *= f;
            vector4f.y *= f;
        }
        return vector4f;
    }

    public Vec3d Method2023(Vec3d vec3d) {
        Vector4f vector4f = this.Method2022(vec3d);
        int n = this.Field2237.displayWidth;
        int n2 = this.Field2237.displayHeight;
        vector4f.x = (float)n / 2.0f + (0.5f * vector4f.x * (float)n + 0.5f);
        vector4f.y = (float)n2 / 2.0f - (0.5f * vector4f.y * (float)n2 + 0.5f);
        double d = this.Method2021(vector4f, n, n2) ? 0.0 : -1.0;
        return new Vec3d((double)vector4f.x, (double)vector4f.y, d);
    }

    public void Method2024(Vector4f vector4f, Matrix4f matrix4f) {
        float f = vector4f.x;
        float f2 = vector4f.y;
        float f3 = vector4f.z;
        vector4f.x = f * matrix4f.m00 + f2 * matrix4f.m10 + f3 * matrix4f.m20 + matrix4f.m30;
        vector4f.y = f * matrix4f.m01 + f2 * matrix4f.m11 + f3 * matrix4f.m21 + matrix4f.m31;
        vector4f.z = f * matrix4f.m02 + f2 * matrix4f.m12 + f3 * matrix4f.m22 + matrix4f.m32;
        vector4f.w = f * matrix4f.m03 + f2 * matrix4f.m13 + f3 * matrix4f.m23 + matrix4f.m33;
    }

    public void Method2025(Matrix4f matrix4f, int n) {
        FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer((int)16);
        GL11.glGetFloat((int)n, (FloatBuffer)floatBuffer);
        matrix4f.load(floatBuffer);
    }

    public Vec3d Method2026(Vec3d vec3d) {
        Vector4f vector4f = this.Method2022(vec3d);
        ScaledResolution scaledResolution = new ScaledResolution(this.Field2237);
        int n = scaledResolution.getScaledWidth();
        int n2 = scaledResolution.getScaledHeight();
        vector4f.x = (float)n / 2.0f + (0.5f * vector4f.x * (float)n + 0.5f);
        vector4f.y = (float)n2 / 2.0f - (0.5f * vector4f.y * (float)n2 + 0.5f);
        double d = this.Method2021(vector4f, n, n2) ? 0.0 : -1.0;
        return new Vec3d((double)vector4f.x, (double)vector4f.y, d);
    }

    @Subscriber
    public void Method2027(Render3DEvent render3DEvent) {
        if (this.Field2237.getRenderViewEntity() == null) {
            return;
        }
        Vec3d vec3d = ActiveRenderInfo.projectViewFromEntity((Entity)this.Field2237.getRenderViewEntity(), (double)this.Field2237.getRenderPartialTicks());
        Vec3d vec3d2 = ActiveRenderInfo.getCameraPosition();
        this.Method2025(this.Field2238, 2982);
        this.Method2025(this.Field2239, 2983);
        this.Field2241 = vec3d.add(vec3d2);
        this.Field2240 = new ScaledResolution(this.Field2237);
    }
}