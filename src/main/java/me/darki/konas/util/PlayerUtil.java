package me.darki.konas.util;

import java.text.DecimalFormat;

import me.darki.konas.mixin.mixins.IEntity;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.module.render.ESP;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class PlayerUtil {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static double[] Field1067 = null;

    public static float[] Method1075(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d2.x - vec3d.x;
        double d2 = (vec3d2.y - vec3d.y) * -1.0;
        double d3 = vec3d2.z - vec3d.z;
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        return new float[]{(float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d3, d)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d2, d4)))};
    }

    public static double[] Method1076(double d, EntityPlayerSP entityPlayerSP) {
        Minecraft minecraft = Minecraft.getMinecraft();
        float f = entityPlayerSP.movementInput.moveForward;
        float f2 = entityPlayerSP.movementInput.moveStrafe;
        float f3 = entityPlayerSP.prevRotationYaw + (entityPlayerSP.rotationYaw - entityPlayerSP.prevRotationYaw) * minecraft.getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += (float)(f > 0.0f ? -45 : 45);
            } else if (f2 < 0.0f) {
                f3 += (float)(f > 0.0f ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            } else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        double d4 = (double)f * d * d3 + (double)f2 * d * d2;
        double d5 = (double)f * d * d2 - (double)f2 * d * d3;
        return new double[]{d4, d5};
    }

    public static Vec3d Method1077(Vec3d vec3d) {
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        double d = Double.parseDouble(decimalFormat.format(vec3d.x));
        double d2 = Double.parseDouble(decimalFormat.format(vec3d.y));
        double d3 = Double.parseDouble(decimalFormat.format(vec3d.z));
        return new Vec3d(d, d2, d3);
    }

    public static Vec3d Method1078() {
        return new Vec3d(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + (double) PlayerUtil.mc.player.getEyeHeight(), PlayerUtil.mc.player.posZ);
    }

    public static boolean Method1079() {
        return PlayerUtil.mc.gameSettings.keyBindForward.isKeyDown() && !PlayerUtil.mc.player.isSneaking();
    }

    public static boolean Method1080() {
        return PlayerUtil.mc.gameSettings.keyBindForward.isKeyDown() || PlayerUtil.mc.gameSettings.keyBindBack.isKeyDown() || PlayerUtil.mc.gameSettings.keyBindRight.isKeyDown() || PlayerUtil.mc.gameSettings.keyBindLeft.isKeyDown();
    }

    public static void Method1081(Vec3d vec3d) {
        float[] fArray = PlayerUtil.Method1085(vec3d);
        PlayerUtil.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(fArray[0], (float)MathHelper.normalizeAngle((int)fArray[1], 360), PlayerUtil.mc.player.onGround));
        ((IEntityPlayerSP) PlayerUtil.mc.player).setLastReportedYaw(fArray[0]);
        ((IEntityPlayerSP) PlayerUtil.mc.player).setLastReportedPitch(MathHelper.normalizeAngle((int)fArray[1], 360));
    }

    public static double Method1082(float f) {
        return (double)f * (Math.PI / 180);
    }

    public static void Method1083() {
        double d = Math.floor(PlayerUtil.mc.player.posX) + 0.5;
        double d2 = Math.floor(PlayerUtil.mc.player.posZ) + 0.5;
        PlayerUtil.mc.player.setPosition(d, PlayerUtil.mc.player.posY, d2);
        PlayerUtil.mc.player.connection.sendPacket(new CPacketPlayer.Position(d, PlayerUtil.mc.player.posY, d2, PlayerUtil.mc.player.onGround));
    }

    public static boolean Method1084() {
        boolean bl = false;
        if (PlayerUtil.mc.player.posY <= 0.0) {
            return true;
        }
        int n = 1;
        while ((double)n < PlayerUtil.mc.player.posY) {
            BlockPos blockPos = new BlockPos(PlayerUtil.mc.player.posX, n, PlayerUtil.mc.player.posZ);
            if (!(PlayerUtil.mc.world.getBlockState(blockPos).getBlock() instanceof BlockAir)) {
                bl = false;
                break;
            }
            bl = true;
            ++n;
        }
        return bl;
    }

    public static float[] Method1085(Vec3d vec3d) {
        Vec3d vec3d2 = PlayerUtil.Method1078();
        double d = vec3d.x - vec3d2.x;
        double d2 = vec3d.y - vec3d2.y;
        double d3 = vec3d.z - vec3d2.z;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f;
        float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
        float[] fArray = new float[]{PlayerUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(f - PlayerUtil.mc.player.rotationYaw), PlayerUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(f2 - PlayerUtil.mc.player.rotationPitch)};
        return fArray;
    }

    public static double[] Method1086(double d) {
        float f = PlayerUtil.mc.player.movementInput.moveForward;
        float f2 = PlayerUtil.mc.player.movementInput.moveStrafe;
        float f3 = PlayerUtil.mc.player.prevRotationYaw + (PlayerUtil.mc.player.rotationYaw - PlayerUtil.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += (float)(f > 0.0f ? -45 : 45);
            } else if (f2 < 0.0f) {
                f3 += (float)(f > 0.0f ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            } else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        double d4 = (double)f * d * d3 + (double)f2 * d * d2;
        double d5 = (double)f * d * d2 - (double)f2 * d * d3;
        return new double[]{d4, d5};
    }

    public static boolean Method1087(int n) {
        if (n != 0 && n < 256) {
            return n < 0 ? Mouse.isButtonDown(n + 100) : Keyboard.isKeyDown(n);
        }
        return false;
    }

    public static double[] Method1088(double d, double d2, double d3, EntityPlayer entityPlayer) {
        double d4 = entityPlayer.posX - d;
        double d5 = entityPlayer.posY - d2;
        double d6 = entityPlayer.posZ - d3;
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        double d8 = Math.asin(d5 /= d7);
        double d9 = Math.atan2(d6 /= d7, d4 /= d7);
        d8 = d8 * 180.0 / Math.PI;
        d9 = d9 * 180.0 / Math.PI;
        return new double[]{d9 += 90.0, d8};
    }

    public static double[] Method1089(float f) {
        double d = PlayerUtil.Method1082(f);
        double d2 = Math.sin(d);
        double d3 = Math.cos(d);
        return new double[]{d2, d3};
    }

    public static void Method1090(int n, int n2, int n3, float f, float f2, EntityPlayer entityPlayer, boolean bl, boolean bl2) {
        ESP.Field1339 = true;
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, 50.0f);
        GlStateManager.scale((float)(-n3), (float)n3, (float)n3);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        float f3 = entityPlayer.renderYawOffset;
        float f4 = entityPlayer.rotationYaw;
        float f5 = entityPlayer.rotationPitch;
        float f6 = entityPlayer.prevRotationYawHead;
        float f7 = entityPlayer.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        f = bl ? entityPlayer.rotationYaw * -1.0f : f;
        f2 = bl2 ? entityPlayer.rotationPitch * -1.0f : f2;
        GlStateManager.rotate(-((float)Math.atan(f2 / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        if (!bl) {
            entityPlayer.renderYawOffset = (float)Math.atan(f / 40.0f) * 20.0f;
            entityPlayer.rotationYawHead = entityPlayer.rotationYaw = (float)Math.atan(f / 40.0f) * 40.0f;
            entityPlayer.prevRotationYawHead = entityPlayer.rotationYaw;
        }
        if (!bl2) {
            entityPlayer.rotationPitch = -((float)Math.atan(f2 / 40.0f)) * 20.0f;
        }
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntity(entityPlayer, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        renderManager.setRenderShadow(true);
        if (!bl) {
            entityPlayer.renderYawOffset = f3;
            entityPlayer.rotationYaw = f4;
            entityPlayer.prevRotationYawHead = f6;
            entityPlayer.rotationYawHead = f7;
        }
        if (!bl2) {
            entityPlayer.rotationPitch = f5;
        }
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
        ESP.Field1339 = false;
    }

    public static void Method1091(boolean bl) {
        ((IEntity) PlayerUtil.mc.player).setFlag(7, bl);
    }
}