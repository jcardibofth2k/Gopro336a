package me.darki.konas.util;

import me.darki.konas.mixin.mixins.IRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int Method837(String string, int n) throws Exception {
        StringBuilder stringBuilder;
        RuntimeException runtimeException;
        int n2;
        int n3 = 0;
        try {
            n2 = n;
        }
        catch (Exception exception) {
            ARBShaderObjects.glDeleteObjectARB(n3);
            throw exception;
        }
        int n4 = ARBShaderObjects.glCreateShaderObjectARB(n2);
        n3 = n4;
        if (n3 == 0) {
            return 0;
        }
        int n5 = n3;
        String string2 = string;
        ARBShaderObjects.glShaderSourceARB(n5, string2);
        int n6 = n3;
        ARBShaderObjects.glCompileShaderARB(n6);
        int n7 = n3;
        int n8 = 35713;
        int n9 = ARBShaderObjects.glGetObjectParameteriARB(n7, n8);
        if (n9 != 0) return n3;
        RuntimeException runtimeException2 = runtimeException;
        RuntimeException runtimeException3 = runtimeException;
        StringBuilder stringBuilder2 = stringBuilder;
        StringBuilder stringBuilder3 = stringBuilder;
        stringBuilder2();
        String string3 = "Error creating shader: ";
        StringBuilder stringBuilder4 = stringBuilder3.append(string3);
        int n10 = n3;
        String string4 = ShaderUtil.Method845(n10);
        StringBuilder stringBuilder5 = stringBuilder4.append(string4);
        String string5 = stringBuilder5.toString();
        runtimeException2(string5);
        throw runtimeException3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean Method838(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n5 < n) return false;
        if (n5 > n3) return false;
        if (n6 < n2) return false;
        return n6 <= n4;
    }

    public static double[] Method839(Entity entity) {
        if (entity.lastTickPosX == 0.0 && entity.lastTickPosY == 0.0 && entity.lastTickPosZ == 0.0) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
        }
        double d = ShaderUtil.Method840(entity.posX, entity.lastTickPosX) - ((IRenderManager) mc.getRenderManager()).getRenderPosX();
        double d2 = ShaderUtil.Method840(entity.posY, entity.lastTickPosY) - ((IRenderManager) mc.getRenderManager()).getRenderPosY();
        double d3 = ShaderUtil.Method840(entity.posZ, entity.lastTickPosZ) - ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
        return new double[]{d, d2, d3};
    }

    public static double Method840(double d, double d2) {
        return d2 + (d - d2) * (double) mc.getRenderPartialTicks();
    }

    public static double Method841(Entity entity) {
        return ShaderUtil.Method848(entity.lastTickPosY, entity.posY, mc.getRenderPartialTicks());
    }

    public static double Method842(Entity entity) {
        return ShaderUtil.Method848(entity.lastTickPosX, entity.posX, mc.getRenderPartialTicks());
    }

    public static double Method843() {
        return ShaderUtil.Method848(ShaderUtil.mc.player.lastTickPosX, ShaderUtil.mc.player.posX, mc.getRenderPartialTicks());
    }

    public static double Method844() {
        return ShaderUtil.Method848(ShaderUtil.mc.player.lastTickPosZ, ShaderUtil.mc.player.posZ, mc.getRenderPartialTicks());
    }

    public static String Method845(int n) {
        return ARBShaderObjects.glGetInfoLogARB(n, ARBShaderObjects.glGetObjectParameteriARB(n, 35716));
    }

    public static double Method846(Entity entity) {
        return ShaderUtil.Method848(entity.lastTickPosZ, entity.posZ, mc.getRenderPartialTicks());
    }

    public static double Method847() {
        return ShaderUtil.Method848(ShaderUtil.mc.player.lastTickPosY, ShaderUtil.mc.player.posY, mc.getRenderPartialTicks());
    }

    public static double Method848(double d, double d2, double d3) {
        return d + (d2 - d) * d3;
    }
}