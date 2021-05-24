package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.mixin.mixins.IRenderGlobal;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.mixin.mixins.IShaderGroup;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.ShaderUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ESP
extends Module {
    public static Setting<ParentSetting> entities = new Setting<>("Entities", new ParentSetting(true));
    public static Setting<Class486> mode = new Setting<>("Mode", Class486.SHADER).setParentSetting(entities);
    public static Setting<Boolean> skeleton = new Setting<>("Skeleton", false).setParentSetting(entities);
    public static Setting<Boolean> csgo = new Setting<>("Csgo", false).setParentSetting(entities);
    public static Setting<Float> width = new Setting<>("Width", 5.0f, 10.0f, 0.1f, 0.1f).setParentSetting(entities);
    public static Setting<Double> quality = new Setting<>("Quality", 2.0, 10.0, 0.1, 0.1).setParentSetting(entities).visibleIf(ESP::Method394);
    public static Setting<Boolean> shaderOutline = new Setting<>("ShaderOutline", true).setParentSetting(entities).visibleIf(ESP::Method980);
    public static Setting<Boolean> shaderFade = new Setting<>("ShaderFade", true).setParentSetting(entities).visibleIf(ESP::Method388);
    public static Setting<ColorValue> shaderColor = new Setting<>("ShaderColor", new ColorValue(-65281)).setParentSetting(entities).visibleIf(ESP::Method393);
    public static Setting<Boolean> shaderFill = new Setting<>("ShaderFill", false).setParentSetting(entities).visibleIf(ESP::Method538);
    public static Setting<ColorValue> shaderFillColor = new Setting<>("ShaderFillColor", new ColorValue(-1996553985)).setParentSetting(entities).visibleIf(ESP::Method535);
    public static Setting<Boolean> show_targets = new Setting<>("Show Targets", true).setParentSetting(entities);
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true).setParentSetting(entities).visibleIf(ESP::Method539);
    public static Setting<Boolean> players = new Setting<>("Players", true).setParentSetting(entities);
    public static Setting<ColorValue> playerColor = new Setting<>("PlayerColor", new ColorValue(-52237)).setParentSetting(entities).visibleIf(players::getValue);
    public static Setting<Boolean> animals = new Setting<>("Animals", false).setParentSetting(entities);
    public static Setting<ColorValue> animalColor = new Setting<>("AnimalColor", new ColorValue(-12779725)).setParentSetting(entities).visibleIf(animals::getValue);
    public static Setting<Boolean> mobs = new Setting<>("Mobs", true).setParentSetting(entities);
    public static Setting<ColorValue> colorValueSetting;
    public static Setting<Boolean> Field1311;
    public static Setting<ColorValue> Field1312;
    public static Setting<Boolean> Field1313;
    public static Setting<ColorValue> Field1314;
    public static Setting<ParentSetting> Field1315;
    public static Setting<Boolean> Field1316;
    public static Setting<Class483> Field1317;
    public static Setting<Float> Field1318;
    public static Setting<Boolean> Field1319;
    public static Setting<ColorValue> Field1320;
    public static Setting<ColorValue> Field1321;
    public static Setting<ColorValue> Field1322;
    public static Setting<ParentSetting> Field1323;
    public static Setting<Boolean> Field1324;
    public static Setting<Class483> Field1325;
    public static Setting<ColorValue> Field1326;
    public static Setting<ColorValue> Field1327;
    public static Setting<ColorValue> Field1328;
    public static Setting<ColorValue> Field1329;
    public static Setting<Integer> Field1330;
    public static Setting<Float> Field1331;
    public static Setting<Double> Field1332;
    public static Setting<Boolean> Field1333;
    public static Setting<ColorValue> Field1334;
    public static Setting<Long> Field1335;
    public static ICamera Field1336;
    public CopyOnWriteArrayList<BlockPos> Field1337 = new CopyOnWriteArrayList();
    public CopyOnWriteArrayList<Class479> Field1338 = new CopyOnWriteArrayList();
    public static boolean Field1339;
    public Framebuffer Field1340;
    public Class554 Field1341;
    public static boolean Field1342;

    public static void Method1314(BufferBuilder bufferBuilder, float f) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, (double)f + 0.55, 0.0);
        int n = playerColor.getValue().Method773(100);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(-0.375, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.375, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    public Integer Method1315(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (show_targets.getValue().booleanValue() && KonasGlobals.INSTANCE.Field1133.Method423(entity)) {
                int n = KonasGlobals.INSTANCE.Field1133.Method428(entity);
                return new Color(255, n / 5, (int)((double)n / 1.0493)).hashCode();
            }
            return playerColor.getValue().Method774();
        }
        if (entity instanceof IMob) {
            return colorValueSetting.getValue().Method774();
        }
        if (entity instanceof IAnimals || entity instanceof INpc) {
            return animalColor.getValue().Method774();
        }
        return Field1314.getValue().Method774();
    }

    public static boolean Method539() {
        return mode.getValue() == Class486.SHADER;
    }

    public Integer Method1316(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return Color.YELLOW.getRGB();
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return Color.GREEN.darker().getRGB();
        }
        if (tileEntity instanceof TileEntityShulkerBox) {
            return ((TileEntityShulkerBox)tileEntity).getColor().getColorValue();
        }
        if (tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityHopper || tileEntity instanceof TileEntityDispenser) {
            return Color.GRAY.getRGB();
        }
        return null;
    }

    public static void Method1317(BufferBuilder bufferBuilder, float f) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0, f, 0.0);
        int n = playerColor.getValue().Method773(300);
        int n2 = playerColor.getValue().Method773(100);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.0, 0.55, 0.0).color(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    public boolean Method386(Entity entity) {
        if (Field1339) {
            return false;
        }
        if (entity instanceof EntityEnderCrystal && crystals.getValue().booleanValue() && mode.getValue() == Class486.SHADER) {
            return true;
        }
        if (entity instanceof EntityPlayer && players.getValue().booleanValue() && entity != ESP.mc.player && entity != mc.getRenderViewEntity() && !Class546.Method963(entity)) {
            return true;
        }
        if (entity instanceof IAnimals && animals.getValue().booleanValue()) {
            return true;
        }
        return entity instanceof IMob && mobs.getValue() != false;
    }

    public ESP() {
        super("ESP", 0, Category.RENDER, new String[0]);
    }

    public static void Method1318(Entity entity) {
        GL11.glEnable(3553);
        double[] dArray = ShaderUtil.Method839(entity);
        double d = dArray[0];
        double d2 = dArray[1];
        double d3 = dArray[2];
        GL11.glPushMatrix();
        Render render = mc.getRenderManager().getEntityRenderObject(entity);
        if (render != null) {
            Field1342 = true;
            render.doRender(entity, d, d2, d3, 0.0f, mc.getRenderPartialTicks());
            Field1342 = false;
        }
        GL11.glDisable(3553);
        GL11.glPopMatrix();
    }

    public static boolean Method980() {
        return mode.getValue() == Class486.SHADER;
    }

    @Subscriber
    public void Method1319(RenderLivingBaseEvent renderLivingBaseEvent) {
        block1: {
            if (ESP.mc.world == null || ESP.mc.player == null) {
                return;
            }
            if (mode.getValue() != Class486.OUTLINE || !this.Method386(renderLivingBaseEvent.Method2040())) break block1;
            Class519.Method1299(width.getValue().intValue());
            renderLivingBaseEvent.Method2041().render(renderLivingBaseEvent.Method2040(), renderLivingBaseEvent.Method260(), renderLivingBaseEvent.Method1108(), renderLivingBaseEvent.Method213(), renderLivingBaseEvent.Method214(), renderLivingBaseEvent.Method258(), renderLivingBaseEvent.Method340());
            Class519.Method1295();
            renderLivingBaseEvent.Method2041().render(renderLivingBaseEvent.Method2040(), renderLivingBaseEvent.Method260(), renderLivingBaseEvent.Method1108(), renderLivingBaseEvent.Method213(), renderLivingBaseEvent.Method214(), renderLivingBaseEvent.Method258(), renderLivingBaseEvent.Method340());
            Class519.Method1293();
            Class519.Method1294(this.Method1315(renderLivingBaseEvent.Method2040()), 3.0f);
            renderLivingBaseEvent.Method2041().render(renderLivingBaseEvent.Method2040(), renderLivingBaseEvent.Method260(), renderLivingBaseEvent.Method1108(), renderLivingBaseEvent.Method213(), renderLivingBaseEvent.Method214(), renderLivingBaseEvent.Method258(), renderLivingBaseEvent.Method340());
            Class519.Method1300(3.0f);
        }
    }

    public static boolean Method535() {
        return shaderFill.getValue() != false && mode.getValue() == Class486.SHADER;
    }

    public void Method1320() {
        if (ESP.mc.world.loadedEntityList.stream().filter(this::Method386).count() == 0L) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        if (this.Field1340 == null) {
            this.Field1340 = new Framebuffer(ESP.mc.displayWidth, ESP.mc.displayHeight, false);
        } else if (this.Field1340.framebufferWidth != ESP.mc.displayWidth || this.Field1340.framebufferHeight != ESP.mc.displayHeight) {
            this.Field1340.unbindFramebuffer();
            this.Field1340 = new Framebuffer(ESP.mc.displayWidth, ESP.mc.displayHeight, false);
            if (this.Field1341 != null) {
                this.Field1341.Method686();
                this.Field1341 = new Class554(this.Field1340);
            }
        }
        if (quality.getValue() != Class554.Field627) {
            Class554.Field627 = quality.getValue();
            this.Field1341.Method686();
            this.Field1341 = new Class554(this.Field1340);
        } else if (this.Field1341 == null) {
            this.Field1341 = new Class554(this.Field1340);
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        ((IEntityRenderer) ESP.mc.entityRenderer).Method1908(mc.getRenderPartialTicks(), 0);
        GL11.glMatrixMode(5888);
        this.Field1340.bindFramebuffer(false);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(16640);
        ESP.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        ESP.mc.world.loadedEntityList.stream().filter(this::Method386).forEach(ESP::Method1318);
        ESP.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        ESP.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.Field1341.Method689();
        this.Field1340.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glScaled(1.0 / FrameBuffer.Field627, 1.0 / FrameBuffer.Field627, 1.0 / FrameBuffer.Field627);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, this.Field1341.Method685());
        GL11.glBegin(4);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, (double)scaledResolution.getScaledHeight() * Class554.Field627);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth() * Class554.Field627, (double)scaledResolution.getScaledHeight() * Class554.Field627);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth() * Class554.Field627, (double)scaledResolution.getScaledHeight() * Class554.Field627);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth() * Class554.Field627, 0.0);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glEnd();
        GL11.glScaled(FrameBuffer.Field627, FrameBuffer.Field627, FrameBuffer.Field627);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getFramebuffer().bindFramebuffer(false);
        RenderHelper.enableStandardItemLighting();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void Method1321(BufferBuilder bufferBuilder, EntityPlayer entityPlayer, float f) {
        GL11.glRotatef(entityPlayer.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0, entityPlayer.isSneaking() ? -0.16175 : 0.0, entityPlayer.isSneaking() ? -0.48025 : 0.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, f, 0.0);
        int n = playerColor.getValue().Method773(300);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(-0.125, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.125, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    public void Method1322(Entity entity) {
        block8: {
            if (entity == ESP.mc.player || !this.Method386(entity)) break block8;
            float f = mc.getRenderViewEntity().getDistance(entity);
            if (f < 3.0f) {
                f = 3.0f;
            }
            float f2 = 1.0f / (f / 3.0f);
            GL11.glBlendFunc(770, 771);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            Vec3d vec3d = new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(new Vec3d((entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks(), (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks(), (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks()));
            GlStateManager.translate(vec3d.x - ((IRenderManager)mc.getRenderManager()).getRenderPosX(), vec3d.y - ((IRenderManager)mc.getRenderManager()).getRenderPosY(), vec3d.z - ((IRenderManager)mc.getRenderManager()).getRenderPosZ());
            GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-ESP.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            Minecraft minecraft = mc;
            RenderManager renderManager = minecraft.getRenderManager();
            float f3 = renderManager.options.thirdPersonView == 2 ? -1 : 1;
            float f4 = 1.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            try {
                GlStateManager.rotate(f3, f4, f5, f6);
            }
            catch (NullPointerException nullPointerException) {
                GlStateManager.rotate(1.0f, 1.0f, 0.0f, 0.0f);
            }
            int n = this.Method1315(entity);
            float f7 = (float)(n >> 16 & 0xFF) / 255.0f;
            float f8 = (float)(n >> 8 & 0xFF) / 255.0f;
            float f9 = (float)(n & 0xFF) / 255.0f;
            GL11.glColor4f(f7, f8, f9, 1.0f);
            GL11.glLineWidth(3.0f * f2);
            GL11.glEnable(2848);
            GL11.glBegin(2);
            GL11.glVertex2d((double)(-entity.width) * 1.2, -((double)entity.height * 0.2));
            GL11.glVertex2d((double)(-entity.width) * 1.2, (double)entity.height * 1.2);
            GL11.glVertex2d((double)entity.width * 1.2, (double)entity.height * 1.2);
            GL11.glVertex2d((double)entity.width * 1.2, -((double)entity.height * 0.2));
            GL11.glEnd();
            if (entity instanceof EntityLivingBase) {
                GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.3f);
                GL11.glLineWidth(5.0f * f2);
                GL11.glBegin(1);
                GL11.glVertex2d((double)entity.width * 1.4, (double)entity.height * 1.2);
                GL11.glVertex2d((double)entity.width * 1.4, -((double)entity.height * 0.2));
                GL11.glEnd();
                GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                float f10 = ((EntityLivingBase)entity).getHealth() / ((EntityLivingBase)entity).getMaxHealth();
                GL11.glBegin(1);
                GL11.glVertex2d((double)entity.width * 1.4, (double)entity.height * 1.2 * (double)f10);
                GL11.glVertex2d((double)entity.width * 1.4, -((double)entity.height * 0.2));
                GL11.glEnd();
                float f11 = ((EntityLivingBase)entity).getAbsorptionAmount() / 16.0f;
                if (f11 > 0.0f) {
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.3f);
                    GL11.glBegin(1);
                    GL11.glVertex2d((double)entity.width * 1.6, (double)entity.height * 0.92);
                    GL11.glVertex2d((double)entity.width * 1.6, -((double)entity.height * 0.2));
                    GL11.glEnd();
                    GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
                    GL11.glBegin(1);
                    GL11.glVertex2d((double)entity.width * 1.6, (double)entity.height * 0.92 * (double)f11);
                    GL11.glVertex2d((double)entity.width * 1.6, -((double)entity.height * 0.2));
                    GL11.glEnd();
                }
            }
            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.enableDepth();
            GlStateManager.resetColor();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }

    @Subscriber
    public void Method139(final Render3DEvent render3DEvent) {
        if (ESP.mc.world == null || ESP.mc.player == null) {
            return;
        }
        if (ESP.Field1311.getValue()) {
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderPearl && ESP.mc.getRenderViewEntity().getDistance(entity) < 250.0) {
                    Class516.Method1280(entity, (ESP.Field1312.getValue()).Method774(), render3DEvent.Method436());
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.glLineWidth(1.0f);
                    Class516.Method1257(entity, (ESP.Field1312.getValue()).Method774(), render3DEvent.Method436());
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Class516.Method1261();
            GlStateManager.popMatrix();
        }
        if (ESP.csgo.getValue()) {
            if (ESP.mc.getRenderManager() == null) {
                return;
            }
            ESP.mc.world.loadedEntityList.stream().filter(ESP::Method395).forEach(this::Method1322);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (ESP.mode.getValue() == Class486.BOX) {
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            for (final Entity entity2 : ESP.mc.world.loadedEntityList) {
                if (entity2 != ESP.mc.player && this.Method386(entity2)) {
                    Class516.Method1280(entity2, this.Method1315(entity2), render3DEvent.Method436());
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.glLineWidth(1.0f);
                    Class516.Method1257(entity2, this.Method1315(entity2), render3DEvent.Method436());
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Class516.Method1261();
            GlStateManager.popMatrix();
        }
        if (ESP.Field1313.getValue()) {
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            for (final Entity entity3 : ESP.mc.world.loadedEntityList) {
                if (entity3 != ESP.mc.player && entity3 instanceof EntityItem) {
                    Class516.Method1280(entity3, this.Method1315(entity3), render3DEvent.Method436());
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.glLineWidth(1.0f);
                    Class516.Method1257(entity3, this.Method1315(entity3), render3DEvent.Method436());
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Class516.Method1261();
            GlStateManager.popMatrix();
        }
        if (ESP.Field1324.getValue()) {
            if (ESP.Field1325.getValue() == Class483.LINE) {
                GlStateManager.pushMatrix();
                Class516.Method1289();
                GlStateManager.enableBlend();
                GlStateManager.glLineWidth((float)ESP.Field1331.getValue());
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                for (final BlockPos blockPos : this.Field1337) {
                    final AxisAlignedBB axisAlignedBB = new AxisAlignedBB((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)(blockPos.getX() + 1), (double)blockPos.getY(), (double)(blockPos.getZ() + 1));
                    int n = (ESP.Field1327.getValue()).Method774();
                    if (ESP.mc.world.getBlockState(blockPos.up()).getBlock() == Blocks.AIR) {
                        n = (ESP.Field1329.getValue()).Method774();
                    }
                    Class516.Method1281(axisAlignedBB, n);
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                Class516.Method1261();
                GlStateManager.popMatrix();
            }
            else if (ESP.Field1325.getValue() == Class483.OUTLINE) {
                for (final BlockPos blockPos2 : this.Field1337) {
                    final AxisAlignedBB offset = ESP.mc.world.getBlockState(blockPos2).getBoundingBox((IBlockAccess)ESP.mc.world, blockPos2).offset(blockPos2);
                    final AxisAlignedBB offset2 = offset.setMaxY(offset.minY + (double)ESP.Field1332.getValue()).offset(-((IRenderManager)ESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosZ());
                    int n2 = (ESP.Field1327.getValue()).Method774();
                    if (ESP.mc.world.getBlockState(blockPos2.up()).getBlock() == Blocks.AIR) {
                        n2 = (ESP.Field1329.getValue()).Method774();
                    }
                    Class523.Method1216();
                    Class523.Method1215(offset2, n2, (float)ESP.Field1331.getValue());
                    Class523.Method1214();
                }
            }
            else {
                for (final BlockPos blockPos3 : this.Field1337) {
                    final AxisAlignedBB offset3 = ESP.mc.world.getBlockState(blockPos3).getBoundingBox((IBlockAccess)ESP.mc.world, blockPos3).offset(blockPos3);
                    final AxisAlignedBB offset4 = offset3.setMaxY(offset3.minY + (double)ESP.Field1332.getValue()).offset(-((IRenderManager)ESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosZ());
                    int n3 = (ESP.Field1326.getValue()).Method774();
                    if (ESP.mc.world.getBlockState(blockPos3.up()).getBlock() == Blocks.AIR) {
                        n3 = (ESP.Field1328.getValue()).Method774();
                    }
                    Class523.Method1216();
                    Class523.Method1217(offset4, n3);
                    Class523.Method1214();
                    int n4 = (ESP.Field1327.getValue()).Method774();
                    if (ESP.mc.world.getBlockState(blockPos3.up()).getBlock() == Blocks.AIR) {
                        n4 = (ESP.Field1329.getValue()).Method774();
                    }
                    Class523.Method1216();
                    Class523.Method1215(offset4, n4, (float)ESP.Field1331.getValue());
                    Class523.Method1214();
                }
            }
        }
        if (ESP.Field1333.getValue()) {
            if (ESP.mc.getRenderViewEntity() == null) {
                return;
            }
            ESP.Field1336.setPosition(ESP.mc.getRenderViewEntity().posX, ESP.mc.getRenderViewEntity().posY, ESP.mc.getRenderViewEntity().posZ);
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.glLineWidth(2.0f);
            for (final Class479 class90 : this.Field1338) {
                final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB((double)class90.Method2109(), 0.0, (double)class90.Method2107(), (double)(class90.Method2109() + 16), 0.0, (double)(class90.Method2107() + 16));
                GlStateManager.pushMatrix();
                if (ESP.Field1336.isBoundingBoxInFrustum(axisAlignedBB2)) {
                    RenderUtil3D.Method1414(axisAlignedBB2.offset(-(ESP.mc.getRenderViewEntity().lastTickPosX + (ESP.mc.getRenderViewEntity().posX - ESP.mc.getRenderViewEntity().lastTickPosX) * render3DEvent.Method436()), -(ESP.mc.getRenderViewEntity().lastTickPosY + (ESP.mc.getRenderViewEntity().posY - ESP.mc.getRenderViewEntity().lastTickPosY) * render3DEvent.Method436()), -(ESP.mc.getRenderViewEntity().lastTickPosZ + (ESP.mc.getRenderViewEntity().posZ - ESP.mc.getRenderViewEntity().lastTickPosZ) * render3DEvent.Method436())), 3, (ESP.Field1334.getValue()).Method774());
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.glLineWidth(1.0f);
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            Class516.Method1261();
            GlStateManager.popMatrix();
        }
        if (!(boolean)ESP.Field1316.getValue()) {
            return;
        }
        if (ESP.Field1317.getValue() == Class483.LINE) {
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.enableBlend();
            GlStateManager.glLineWidth((float)ESP.Field1318.getValue());
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            for (final TileEntity tileEntity : ESP.mc.world.loadedTileEntityList) {
                final BlockPos pos = tileEntity.getPos();
                final IBlockState blockState = ESP.mc.world.getBlockState(pos);
                final Integer method1331 = this.Method1331(tileEntity);
                if (method1331 != null) {
                    Class516.Method1285(blockState.getSelectedBoundingBox((World)ESP.mc.world, pos), new Color(method1331));
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Class516.Method1261();
            GlStateManager.popMatrix();
        }
        else if (ESP.Field1317.getValue() == Class483.OUTLINE) {
            for (final TileEntity tileEntity2 : ESP.mc.world.loadedTileEntityList) {
                final BlockPos pos2 = tileEntity2.getPos();
                final Integer method1332 = this.Method1331(tileEntity2);
                if (method1332 != null) {
                    final AxisAlignedBB offset5 = ESP.mc.world.getBlockState(pos2).getBoundingBox((IBlockAccess)ESP.mc.world, pos2).offset(pos2).offset(-((IRenderManager)ESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosZ());
                    Class523.Method1216();
                    Class523.Method1215(offset5, method1332, (float)ESP.Field1318.getValue());
                    Class523.Method1214();
                }
            }
        }
        else {
            for (final TileEntity tileEntity3 : ESP.mc.world.loadedTileEntityList) {
                final BlockPos pos3 = tileEntity3.getPos();
                final Integer method1333 = this.Method1331(tileEntity3);
                if (method1333 != null) {
                    final AxisAlignedBB offset6 = ESP.mc.world.getBlockState(pos3).getBoundingBox((IBlockAccess)ESP.mc.world, pos3).offset(pos3).offset(-((IRenderManager)ESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)ESP.mc.getRenderManager()).getRenderPosZ());
                    Class523.Method1216();
                    Class523.Method1217(offset6, method1333);
                    Class523.Method1214();
                    Class523.Method1216();
                    Class523.Method1215(offset6, method1333, (float)ESP.Field1318.getValue());
                    Class523.Method1214();
                }
            }
        }
    }

    public boolean Method1323(long l, int n, int n2) {
        Random random = new Random(l + (long)(n * n * 4987142) + (long)(n * 5947611) + (long)(n2 * n2) * 4392871L + (long)(n2 * 389711) ^ 0x3AD8025FL);
        return random.nextInt(10) == 0;
    }

    public static void Method1324(BufferBuilder bufferBuilder, EntityPlayer entityPlayer, float f, ModelRenderer modelRenderer) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.375, (double)f + 0.55, 0.0);
        if (modelRenderer.rotateAngleX != 0.0f) {
            GlStateManager.rotate(modelRenderer.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleY != 0.0f) {
            GlStateManager.rotate(modelRenderer.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleZ != 0.0f) {
            GlStateManager.rotate(-modelRenderer.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        int n = playerColor.getValue().Method773(100);
        int n2 = playerColor.getValue().Method773(300);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.0, -0.5, 0.0).color(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    public static void Method1325(BufferBuilder bufferBuilder, EntityPlayer entityPlayer, float f, float f2, ModelRenderer modelRenderer) {
        GL11.glRotatef(f2 - entityPlayer.rotationYawHead, 0.0f, 1.0f, 0.0f);
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0, (double)f + 0.55, 0.0);
        if (modelRenderer.rotateAngleX != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        int n = playerColor.getValue().Method773(100);
        int n2 = playerColor.getValue().Method773(0);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.0, 0.3, 0.0).color(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    public static void Method1326(EntityPlayer entityPlayer, ModelPlayer modelPlayer, float f) {
        if (!skeleton.getValue().booleanValue() || !ModuleManager.Method1612("ESP").isEnabled()) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
        GL11.glEnable(2903);
        GL11.glDisable(2848);
        double d = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)f;
        double d2 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double)f;
        double d3 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)f;
        Field1336.setPosition(d, d2, d3);
        if (!Field1336.isBoundingBoxInFrustum(entityPlayer.getEntityBoundingBox()) || entityPlayer.isDead || !entityPlayer.isEntityAlive() || entityPlayer.isPlayerSleeping()) {
            return;
        }
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(d - ((IRenderManager)mc.getRenderManager()).getRenderPosX(), d2 - ((IRenderManager)mc.getRenderManager()).getRenderPosY(), d3 - ((IRenderManager)mc.getRenderManager()).getRenderPosZ());
        float f2 = entityPlayer.prevRenderYawOffset + (entityPlayer.renderYawOffset - entityPlayer.prevRenderYawOffset) * f;
        GL11.glRotatef(-f2, 0.0f, 1.0f, 0.0f);
        float f3 = entityPlayer.isSneaking() ? 0.6f : 0.75f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        ESP.Method1330(bufferBuilder, entityPlayer, f3, modelPlayer.bipedRightLeg);
        ESP.Method1332(bufferBuilder, entityPlayer, f3, modelPlayer.bipedLeftLeg);
        ESP.Method1327(bufferBuilder, entityPlayer, f3, modelPlayer.bipedRightArm);
        ESP.Method1324(bufferBuilder, entityPlayer, f3, modelPlayer.bipedLeftArm);
        ESP.Method1325(bufferBuilder, entityPlayer, f3, f2, modelPlayer.bipedHead);
        ESP.Method1321(bufferBuilder, entityPlayer, f3);
        ESP.Method1317(bufferBuilder, f3);
        ESP.Method1314(bufferBuilder, f3);
        Gui.drawRect(0, 0, 0, 0, 0);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glDisable(2848);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
    }

    public static void Method1327(BufferBuilder bufferBuilder, EntityPlayer entityPlayer, float f, ModelRenderer modelRenderer) {
        GlStateManager.translate(0.0, 0.0, entityPlayer.isSneaking() ? 0.25 : 0.0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, entityPlayer.isSneaking() ? -0.05 : 0.0, entityPlayer.isSneaking() ? -0.01725 : 0.0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.375, (double)f + 0.55, 0.0);
        if (modelRenderer.rotateAngleX != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleY != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleZ != 0.0f) {
            GL11.glRotatef(-modelRenderer.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        int n = playerColor.getValue().Method773(100);
        int n2 = playerColor.getValue().Method773(300);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.0, -0.5, 0.0).color(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    @Subscriber
    public void Method1328(Class14 class14) {
        if (mode.getValue() == Class486.SHADER) {
            ESP ESP = this;
            try {
                ESP.Method1320();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static boolean Method395(Entity entity) {
        return ESP.mc.player != entity && entity != mc.getRenderViewEntity();
    }

    public static boolean Method388() {
        return shaderOutline.getValue() != false && mode.getValue() == Class486.SHADER;
    }

    static {
        Setting setting = new Setting<>("MobColor", new ColorValue(-65536)).setParentSetting(entities);
        Setting<Boolean> setting2 = mobs;
        setting2.getClass();
        colorValueSetting = setting.visibleIf(setting2::getValue);
        Field1311 = new Setting<>("Pearls", true).setParentSetting(entities);
        Field1312 = new Setting<>("PearlColor", new ColorValue(-1442775245)).setParentSetting(entities).visibleIf(Field1311::getValue);
        Field1313 = new Setting<>("Items", false).setParentSetting(entities);
        Setting setting3 = new Setting<>("ItemColor", new ColorValue(-2509)).setParentSetting(entities);
        Setting<Boolean> setting4 = Field1313;
        setting4.getClass();
        Field1314 = setting3.visibleIf(setting4::getValue);
        Field1315 = new Setting<>("Storage", new ParentSetting(false));
        Field1316 = new Setting<>("Storages", true).setParentSetting(Field1315);
        Field1317 = new Setting<>("StorageMode", Class483.FULL).setParentSetting(Field1315);
        Field1318 = new Setting<>("StorageWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(1.0f)).setParentSetting(Field1315);
        Field1319 = new Setting<>("CustomColors", false).setParentSetting(Field1315);
        Field1320 = new Setting<>("ChestColor", new ColorValue(Color.YELLOW.getRGB())).setParentSetting(Field1315);
        Field1321 = new Setting<>("EChestColor", new ColorValue(Color.GREEN.darker().getRGB())).setParentSetting(Field1315);
        Field1322 = new Setting<>("OtherColor", new ColorValue(Color.GRAY.getRGB())).setParentSetting(Field1315);
        Field1323 = new Setting<>("Void", new ParentSetting(false));
        Field1324 = new Setting<>("Voids", true).setParentSetting(Field1323);
        Field1325 = new Setting<>("VoidMode", Class483.FULL).setParentSetting(Field1323);
        Field1326 = new Setting<>("VoidFillColor", new ColorValue(301950208)).setParentSetting(Field1323);
        Field1327 = new Setting<>("VoidColor", new ColorValue(-39680)).setParentSetting(Field1323);
        Field1328 = new Setting<>("OpenVoidFill", new ColorValue(0x11FF0000)).setParentSetting(Field1323);
        Field1329 = new Setting<>("OpenVoid", new ColorValue(-65536)).setParentSetting(Field1323);
        Field1330 = new Setting<>("VRange", 25, 255, 5, 5).setParentSetting(Field1323);
        Field1331 = new Setting<>("VoidWidth", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(1.0f)).setParentSetting(Field1323);
        Field1332 = new Setting<>("VoidHeight", 0.5, 3.0, -3.0, 0.1).setParentSetting(Field1323);
        Field1333 = new Setting<>("SlimeChunks", false);
        Field1334 = new Setting<>("SlimeChunkColor", new ColorValue(-16711936, false)).visibleIf(Field1333::getValue);
        Field1335 = new Setting<>("Seed", 0L, Long.MAX_VALUE, Long.MIN_VALUE, 1L).visibleIf(ESP::Method519);
        Field1336 = new Frustum();
        Field1339 = false;
        Field1342 = false;
    }

    @Override
    public void onDisable() {
        for (Entity entity : ESP.mc.world.loadedEntityList) {
            entity.setGlowing(false);
        }
    }

    public void Method1329(Entity entity, TextFormatting textFormatting, String string) {
        ScorePlayerTeam scorePlayerTeam = ESP.mc.world.getScoreboard().getTeamNames().contains(string) ? ESP.mc.world.getScoreboard().getTeam(string) : ESP.mc.world.getScoreboard().createTeam(string);
        WorldClient worldClient = ESP.mc.world;
        Scoreboard scoreboard = worldClient.getScoreboard();
        Entity entity2 = entity;
        String string2 = entity2.getName();
        ScorePlayerTeam scorePlayerTeam2 = scorePlayerTeam;
        String string3 = scorePlayerTeam2.getName();
        scoreboard.addPlayerToTeam(string2, string3);
        WorldClient worldClient2 = ESP.mc.world;
        Scoreboard scoreboard2 = worldClient2.getScoreboard();
        String string4 = string;
        ScorePlayerTeam scorePlayerTeam3 = scoreboard2.getTeam(string4);
        TextFormatting textFormatting2 = textFormatting;
        try {
            scorePlayerTeam3.setColor(textFormatting2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        entity.setGlowing(true);
    }

    public static boolean Method538() {
        return mode.getValue() == Class486.SHADER;
    }

    public static boolean Method394() {
        return mode.getValue() == Class486.SHADER;
    }

    public static void Method1330(BufferBuilder bufferBuilder, EntityPlayer entityPlayer, float f, ModelRenderer modelRenderer) {
        GlStateManager.translate(0.0, 0.0, entityPlayer.isSneaking() ? -0.235 : 0.0);
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(-0.125, f, 0.0);
        if (modelRenderer.rotateAngleX != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleY != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleZ != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        int n = playerColor.getValue().Method773(300);
        int n2 = playerColor.getValue().Method773(500);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.0, -f, 0.0).color(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    public Integer Method1331(TileEntity tileEntity) {
        Integer n;
        if (Field1319.getValue().booleanValue()) {
            if (tileEntity instanceof TileEntityChest) {
                return Field1320.getValue().Method774();
            }
            if (tileEntity instanceof TileEntityEnderChest) {
                return Field1321.getValue().Method774();
            }
            if (tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityHopper || tileEntity instanceof TileEntityDispenser) {
                return Field1322.getValue().Method774();
            }
        }
        if ((n = this.Method1316(tileEntity)) == null) {
            return null;
        }
        int n2 = 40;
        int n3 = n >> 16 & 0xFF;
        int n4 = n >> 8 & 0xFF;
        int n5 = n & 0xFF;
        return new Integer((n2 & 0xFF) << 24 | (n3 & 0xFF) << 16 | (n4 & 0xFF) << 8 | n5 & 0xFF);
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        SPacketChunkData sPacketChunkData;
        Class479 class479;
        if (packetEvent.getPacket() instanceof SPacketChunkData && !this.Field1338.contains(class479 = new Class479((sPacketChunkData = (SPacketChunkData) packetEvent.getPacket()).getChunkX() * 16, sPacketChunkData.getChunkZ() * 16)) && this.Method1323(Field1335.getValue(), sPacketChunkData.getChunkX(), sPacketChunkData.getChunkZ()) && ESP.mc.player.dimension == 0) {
            this.Field1338.add(class479);
        }
    }

    public static void Method1332(BufferBuilder bufferBuilder, EntityPlayer entityPlayer, float f, ModelRenderer modelRenderer) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.125, f, 0.0);
        if (modelRenderer.rotateAngleX != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleY != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (modelRenderer.rotateAngleZ != 0.0f) {
            GL11.glRotatef(modelRenderer.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        int n = playerColor.getValue().Method773(300);
        int n2 = playerColor.getValue().Method773(500);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF).endVertex();
        bufferBuilder.pos(0.0, -f, 0.0).color(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    @Override
    public void onEnable() {
        this.Field1338.clear();
    }

    public TextFormatting Method1333(Entity entity) {
        if (entity instanceof EntityPlayer) {
            return TextFormatting.LIGHT_PURPLE;
        }
        if (entity instanceof IMob) {
            return TextFormatting.RED;
        }
        if (entity instanceof IAnimals) {
            return TextFormatting.GREEN;
        }
        return TextFormatting.YELLOW;
    }

    public static boolean Method393() {
        return shaderOutline.getValue() != false && mode.getValue() == Class486.SHADER;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent){
        if (ESP.mc.world == null || ESP.mc.player == null) {
            return;
        }
        if (mode.getValue().equals(Class486.GLOW)) {
            ((IShaderGroup)((IRenderGlobal)ESP.mc.renderGlobal).Method73()).Method1220().forEach(ESP::Method1334);
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity.getTeam() == null) {
                    this.Method1329(entity, this.Method1333(entity), "");
                }
                else {
                    this.Method1329(entity, this.Method1333(entity), entity.getTeam().getName());
                }
            }
        }
        else {
            final Iterator<Entity> iterator2 = ESP.mc.world.loadedEntityList.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().setGlowing(false);
            }
        }
        if (ESP.Field1324.getValue()) {
            this.Field1337.clear();
            if (ESP.mc.player.posY < (int)ESP.Field1330.getValue()) {
                for (final BlockPos e : BlockPos.getAllInBox(new BlockPos(ESP.mc.player.posX - 6.0, 0.0, ESP.mc.player.posZ - 6.0), new BlockPos(ESP.mc.player.posX + 6.0, 0.0, ESP.mc.player.posZ + 6.0))) {
                    final IBlockState blockState = ESP.mc.world.getBlockState(e);
                    if (blockState.getBlock() != Blocks.BEDROCK && blockState.getBlock() != Blocks.END_PORTAL_FRAME) {
                        final IBlockState blockState2 = ESP.mc.world.getBlockState(e.add(0, 1, 0));
                        if (blockState2.getBlock() == Blocks.BEDROCK || blockState2.getBlock() == Blocks.END_PORTAL_FRAME) {
                            continue;
                        }
                        final IBlockState blockState3 = ESP.mc.world.getBlockState(e.add(0, 2, 0));
                        if (blockState3.getBlock() == Blocks.BEDROCK || blockState3.getBlock() == Blocks.END_PORTAL_FRAME) {
                            continue;
                        }
                        this.Field1337.add(e);
                    }
                }
            }
        }
    }

    public static void Method1334(Shader shader) {
        block0: {
            ShaderUniform shaderUniform = shader.getShaderManager().getShaderUniform("Radius");
            if (shaderUniform == null) break block0;
            shaderUniform.set(width.getValue().floatValue());
        }
    }

    public static boolean Method519() {
        return false;
    }
}
