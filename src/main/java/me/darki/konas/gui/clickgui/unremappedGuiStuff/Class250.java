package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import com.mojang.authlib.GameProfile;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class215;
import me.darki.konas.module.render.Chams;
import me.darki.konas.setting.PlayerPreview;
import me.darki.konas.module.render.ESP;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Class250
extends Class215 {
    public Setting<PlayerPreview> Field2184;

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)Minecraft.getMinecraft().world, new GameProfile(Minecraft.getMinecraft().player.getUniqueID(), Minecraft.getMinecraft().player.getName()));
        entityOtherPlayerMP.copyLocationAndAnglesFrom((Entity)Minecraft.getMinecraft().player);
        entityOtherPlayerMP.posX = -20000.0;
        entityOtherPlayerMP.posZ = -20000.0;
        entityOtherPlayerMP.rotationPitch = -15.0f;
        entityOtherPlayerMP.prevRotationPitch = -15.0f;
        entityOtherPlayerMP.rotationYaw = 20.0f;
        entityOtherPlayerMP.prevRotationYaw = 20.0f;
        entityOtherPlayerMP.rotationYawHead = 20.0f;
        entityOtherPlayerMP.prevRotationYawHead = 20.0f;
        entityOtherPlayerMP.ticksExisted = Minecraft.getMinecraft().player.ticksExisted;
        this.Method1985((int)(this.Method486() + this.Method489() / 2.0f), (int)(this.Method492() + this.Method476() - 10.0f), 100, n, n2, (EntityPlayer)entityOtherPlayerMP, true, true);
    }

    public Class250(Setting<PlayerPreview> setting, float f, float f2, float f3, float f4, float f5, float f6) {
        super(setting.Method1183(), f, f2, f3, f4, f5, f6);
        this.Field2184 = setting;
    }

    public void Method1985(int n, int n2, int n3, float f, float f2, EntityPlayer entityPlayer, boolean bl, boolean bl2) {
        Chams.Field2147 = true;
        ESP.Field1339 = true;
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)50.0f);
        GlStateManager.scale((float)(-n3), (float)n3, (float)n3);
        GlStateManager.rotate((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float f3 = entityPlayer.renderYawOffset;
        float f4 = entityPlayer.rotationYaw;
        float f5 = entityPlayer.rotationPitch;
        float f6 = entityPlayer.prevRotationYawHead;
        float f7 = entityPlayer.rotationYawHead;
        GlStateManager.rotate((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        f = bl ? entityPlayer.rotationYaw * -1.0f : f;
        f2 = bl2 ? entityPlayer.rotationPitch * -1.0f : f2;
        GlStateManager.rotate((float)(-((float)Math.atan(f2 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        if (!bl) {
            entityPlayer.renderYawOffset = (float)Math.atan(f / 40.0f) * 20.0f;
            entityPlayer.rotationYawHead = entityPlayer.rotationYaw = (float)Math.atan(f / 40.0f) * 40.0f;
            entityPlayer.prevRotationYawHead = entityPlayer.rotationYaw;
        }
        if (!bl2) {
            entityPlayer.rotationPitch = -((float)Math.atan(f2 / 40.0f)) * 20.0f;
        }
        GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.0f);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntity((Entity)entityPlayer, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
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
        GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
        ESP.Field1339 = false;
        Chams.Field2147 = false;
    }
}