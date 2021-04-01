package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import me.darki.konas.module.Category;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.settingEnums.ACComfirmMode;
import me.darki.konas.settingEnums.ACInteractMode;
import me.darki.konas.settingEnums.ACRotateMode;
import me.darki.konas.settingEnums.ACDamageMode;
import me.darki.konas.unremaped.Class24;
import me.darki.konas.module.movement.PacketFly;
import me.darki.konas.settingEnums.ACSyncMode;
import me.darki.konas.settingEnums.ACTiming;
import me.darki.konas.settingEnums.ACTargetMode;
import me.darki.konas.settingEnums.ACSwapMode;
import me.darki.konas.settingEnums.ACYawstepMode;
import me.darki.konas.module.player.FastUse;
import me.darki.konas.unremaped.Class425;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.unremaped.Class473;
import me.darki.konas.unremaped.Class475;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.util.RotationUtil;
import me.darki.konas.unremaped.Class496;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.unremaped.Class502;
import me.darki.konas.unremaped.Class507;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.unremaped.Class537;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.unremaped.Class546;
import me.darki.konas.unremaped.Class566;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.Class89;
import me.darki.konas.mixin.mixins.ICPacketUseEntity;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.ThreadUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class AutoCrystal
extends Module {
    public static Setting<ParentSetting> antiCheat = new Setting<>("AntiCheat", new ParentSetting(false));
    public static Setting<ACTiming> timing = new Setting<>("Timing", ACTiming.ADAPTIVE).setParentSetting(antiCheat).setDescription("Changes how AC is timed");
    public static Setting<ACRotateMode> rotate = new Setting<>("Rotate", ACRotateMode.TRACK).setParentSetting(antiCheat).setDescription("Spoof rotations server-side");
    public static Setting<Boolean> inhibit = new Setting<>("Inhibit", false).setParentSetting(antiCheat).setDescription("Prevent unnesasary attacks");
    public static Setting<Boolean> limit = new Setting<>("Limit", true).setParentSetting(antiCheat).setDescription("Limit attacks");
    public static Setting<ACYawstepMode> yawStep = new Setting<>("YawStep", ACYawstepMode.OFF).setParentSetting(antiCheat).setDescription("Rotate slower");
    public static Setting<Float> yawAngle = new Setting<>("YawAngle", 0.3f, 1.0f, 0.1f, 0.1f).setParentSetting(antiCheat).setDescription("Maximum angle to rotate by per tick");
    public static Setting<Integer> yawTicks = new Setting<>("YawTicks", 1, 5, 1, 1).setParentSetting(antiCheat).setDescription("Rotate slower by this amount of ticks");

    public static Setting<ParentSetting> placements = new Setting<>("Placements", new ParentSetting(false));
    public static Setting<Boolean> check = new Setting<>("Check", true).setParentSetting(placements).setDescription("Check if you will be able to break the crystals you place");
    public static Setting<ACInteractMode> interact = new Setting<>("Interact", ACInteractMode.NORMAL).setParentSetting(placements).setDescription("Changes how you interact with blocks while placing");
    public static Setting<Boolean> protocol = new Setting<>("Protocol", false).setParentSetting(placements).setDescription("Place in 1x1x1 areas");
    public static Setting<Boolean> setting = new Setting<>("PlaceInFire", false).setParentSetting(placements).setDescription("Place in fires");

    public static Setting<ParentSetting> speeds = new Setting<>("Speeds", new ParentSetting(false));
    public static Setting<ACComfirmMode> confirm = new Setting<>("Confirm", ACComfirmMode.OFF).setParentSetting(speeds).setDescription("Do not place elsewhere until previous placement has been executed");
    public static Setting<Integer> ticksExisted = new Setting<>("TicksExisted", 0, 20, 0, 1).setParentSetting(speeds).setDescription("Tick delay for 2b2t");
    public static Setting<Integer> attackTicks = new Setting<>("AttackTicks", 3, 20, 1, 1).setParentSetting(speeds).setDescription("Amount of ticks to attack crystals for").visibleIf(AutoCrystal::Method519);
    public static Setting<Float> breakSpeed = new Setting<>("BreakSpeed", 20.0f, 20.0f, 1.0f, Float.valueOf(0.1f)).setParentSetting(speeds).setDescription("Crystal break speed");
    public static Setting<Float> placeSpeed = new Setting<>("PlaceSpeed", Float.valueOf(20.0f), Float.valueOf(20.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(speeds).setDescription("Crystal place speed");
    public static Setting<ACSyncMode> sync = new Setting<>("Sync", ACSyncMode.STRICT).setParentSetting(speeds).setDescription("Syncronizes breaking and placing");
    public static Setting<Float> offset = new Setting<>("Offset", Float.valueOf(0.0f), Float.valueOf(0.8f), Float.valueOf(0.0f), Float.valueOf(0.1f)).setParentSetting(speeds).visibleIf(AutoCrystal::Method992).setDescription("Syncronization offset");

    public static Setting<ParentSetting> ranges = new Setting<>("Ranges", new ParentSetting(false));
    public static Setting<Float> enemyRange = new Setting<>("EnemyRange", Float.valueOf(8.0f), Float.valueOf(15.0f), Float.valueOf(4.0f), Float.valueOf(0.5f)).setParentSetting(ranges).setDescription("Range from which to select target(s)");
    public static Setting<Float> crystalRange = new Setting<>("CrystalRange", Float.valueOf(6.0f), Float.valueOf(12.0f), Float.valueOf(2.0f), Float.valueOf(0.5f)).setParentSetting(ranges).setDescription("Maximum range between enemies and placements");
    public static Setting<Float> breakRange = new Setting<>("BreakRange", Float.valueOf(4.3f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(ranges).setDescription("Break range for breaking visible crystals");
    public static Setting<Float> breakWalls = new Setting<>("BreakWalls", Float.valueOf(1.5f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(ranges).setDescription("Break range for breaking crystals through walls");
    public static Setting<Float> placeRange = new Setting<>("PlaceRange", Float.valueOf(4.0f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(ranges).setDescription("Place range for visible blocks");
    public static Setting<Float> placeWalls = new Setting<>("PlaceWalls", Float.valueOf(3.0f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(ranges).setDescription("Place range for placing through walls");

    public static Setting<ParentSetting> swap = new Setting<>("Swap", new ParentSetting(false));
    public static Setting<ACSwapMode> autoSwap = new Setting<>("AutoSwap", ACSwapMode.OFF).setParentSetting(swap).setDescription("Auto Swap");
    public static Setting<ACSwapMode> antiWeakness = new Setting<>("AntiWeakness", ACSwapMode.OFF).setParentSetting(swap).setDescription("Swap to sword before hitting crystal when weaknessed");
    public static Setting<Float> swapDelay = new Setting<>("SwapDelay", Float.valueOf(5.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.5f)).setParentSetting(swap).setDescription("Delay for hitting crystals after swapping");

    public static Setting<ParentSetting> damages = new Setting<>("Damages", new ParentSetting(false));
    public static Setting<ACTargetMode> target = new Setting<>("Target", ACTargetMode.ALL).setParentSetting(damages).setDescription("Algorithm to use for selecting target(s)");
    public static Setting<Float> minDamage = new Setting<>("MinDamage", Float.valueOf(6.0f), Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(0.5f)).setParentSetting(damages).setDescription("Minimum amount of damage for placing crystals");
    public static Setting<Float> maxSelfPlace = new Setting<>("MaxSelfPlace", Float.valueOf(12.0f), Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(0.5f)).setParentSetting(damages).setDescription("Maximum amount of self damage for placing crystals");
    public static Setting<Float> maxSelfBreak = new Setting<>("MaxSelfBreak", Float.valueOf(2.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).setParentSetting(damages).setDescription("Maximum self damage for breaking enemy crystals");
    public static Setting<Float> facePlaceHealth = new Setting<>("FacePlaceHealth", Float.valueOf(4.0f), Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(0.5f)).setParentSetting(damages).setDescription("Health at which to start faceplacing enemies");
    public static Setting<Class537> facePlace = new Setting<>("FacePlace", new Class537(56)).setParentSetting(damages);
    public static Setting<Boolean> armorBreaker = new Setting<>("ArmorBreaker", true).setParentSetting(damages);
    public static Setting<Float> depletion = new Setting<>("Depletion", Float.valueOf(0.9f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(damages).visibleIf(armorBreaker::getValue);

    public static Setting<ParentSetting> prediction = new Setting<>("Prediction", new ParentSetting(false));
    public static Setting<Boolean> collision = new Setting<>("Collision", false).setParentSetting(prediction).setDescription("Simulate collision when predicting motion");
    public static Setting<Integer> predictTicks = new Setting<>("PredictTicks", 1, 10, 0, 1).setParentSetting(prediction).setDescription("Predict target motion by this amount of ticks");
    public static Setting<Boolean> predictDestruction = new Setting<>("PredictDestruction", false).setParentSetting(prediction).setDescription("Ignore destructable blocks when doing damage calculations");

    public static Setting<ParentSetting> pause = new Setting<>("Pause", new ParentSetting(false));
    public static Setting<Boolean> mining = new Setting<>("Mining", false).setParentSetting(pause);
    public static Setting<Boolean> gapping = new Setting<>("Gapping", false).setParentSetting(pause);
    public static Setting<Boolean> rightClickGap = new Setting<>("RightClickGap", false).visibleIf(gapping::getValue).setParentSetting(pause);
    public static Setting<Boolean> killAura = new Setting<>("KillAura", true).setParentSetting(pause);
    public static Setting<Boolean> pistonAura = new Setting<>("PistonAura", true).setParentSetting(pause);
    public static Setting<Float> health = new Setting<>("Health", Float.valueOf(2.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.5f)).setParentSetting(pause);
    public static Setting<Boolean> disableOnTP = new Setting<>("DisableOnTP", false).setParentSetting(pause);

    public static Setting<ParentSetting> render = new Setting<>("Render", new ParentSetting(false));
    public static Setting<Boolean> swing = new Setting<>("Swing", false).setParentSetting(render).setDescription("Swing arm client-side");
    public static Setting<Boolean> box = new Setting<>("Box", true).setParentSetting(render);
    public static Setting<Boolean> breaking = new Setting<>("Breaking", true).setParentSetting(render);
    public static Setting<Float> outlineWidth = new Setting<>("OutlineWidth", Float.valueOf(1.5f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).setParentSetting(render).visibleIf(box::getValue);
    public static Setting<ACDamageMode> damage = new Setting<>("Damage", ACDamageMode.NONE).setParentSetting(render);
    public static Setting<Boolean> customFont = new Setting<>("CustomFont", true).setParentSetting(render);
    public static Setting<Float> fade = new Setting<>("Fade", Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).setParentSetting(render);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(1354711231)).setParentSetting(render);
    public static Setting<ColorValue> outline = new Setting<>("Outline", new ColorValue(-4243265)).setParentSetting(render);
    public static Setting<Boolean> targetRender = new Setting<>("TargetRender", true).setParentSetting(render).setDescription("Render circle around target");
    public static Setting<Boolean> depth = new Setting<>("Depth", true).setParentSetting(render);
    public static Setting<Boolean> fill = new Setting<>("Fill", false).setParentSetting(render);
    public static Setting<Boolean> orbit = new Setting<>("Orbit", true).setParentSetting(render);
    public static Setting<Boolean> trail = new Setting<>("Trail", true).setParentSetting(render);
    public static Setting<Float> orbitSpeed = new Setting<>("OrbitSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(render);
    public static Setting<Float> animSpeed = new Setting<>("AnimSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(render);
    public static Setting<Float> width = new Setting<>("Width", Float.valueOf(2.5f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(render);
    public static Setting<ColorValue> targetColor = new Setting<>("TargetColor", new ColorValue(869950564, true)).setParentSetting(render);

    public Vec3d Field1620 = null;
    public float[] Field1621 = new float[]{0.0f, 0.0f};
    public Class566 Field1622 = new Class566();
    public EntityEnderCrystal Field1623;
    public BlockPos Field1624;
    public BlockPos Field1625 = null;
    public EnumFacing Field1626;
    public RayTraceResult Field1627;
    public Class566 Field1628 = new Class566();
    public Class566 Field1629 = new Class566();
    public Class566 Field1630 = new Class566();
    public BlockPos Field1631;
    public float Field1632 = 0.0f;
    public Class566 Field1633 = new Class566();
    public BlockPos Field1634;
    public Class566 Field1635 = new Class566();
    public boolean Field1636 = false;
    public ConcurrentHashMap<BlockPos, Long> Field1637 = new ConcurrentHashMap();
    public ConcurrentHashMap<Integer, Long> Field1638 = new ConcurrentHashMap();
    public Map<EntityPlayer, Class566> Field1639 = new ConcurrentHashMap<EntityPlayer, Class566>();
    public List<BlockPos> Field1640 = new CopyOnWriteArrayList<BlockPos>();
    public AtomicBoolean Field1641 = new AtomicBoolean(false);
    public Class566 Field1642 = new Class566();
    public Class566 Field1643 = new Class566();
    public BlockPos Field1644 = null;
    public Class566 Field1645 = new Class566();
    public EntityEnderCrystal Field1646 = null;
    public Class566 Field1647 = new Class566();
    public Vec3d Field1648 = null;
    public Thread Field1649;
    public AtomicBoolean Field1650 = new AtomicBoolean(false);
    public AtomicBoolean Field1651 = new AtomicBoolean(false);
    public EntityPlayer Field1652;
    public Class566 Field1653 = new Class566();
    public int Field1654;
    public boolean Field1655 = false;
    public int Field1656 = -1;
    public int Field1657 = -1;

    @Subscriber(priority=20)
    public void Method123(Class50 class50) {
        if (this.Field1623 != null) {
            if (this.Method1567(this.Field1623)) {
                this.Field1629.Method739();
                this.Field1638.put(this.Field1623.getEntityId(), System.currentTimeMillis());
                for (Entity entity : AutoCrystal.mc.world.loadedEntityList) {
                    if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistance(this.Field1623.posX, this.Field1623.posY, this.Field1623.posZ) <= 6.0)) continue;
                    this.Field1638.put(entity.getEntityId(), System.currentTimeMillis());
                }
                this.Field1623 = null;
                if (sync.getValue() == ACSyncMode.MERGE) {
                    this.Method1580();
                }
            }
        } else if (this.Field1624 != null) {
            if (!this.Method1572(this.Field1624, this.Field1626)) {
                this.Field1650.set(false);
                this.Field1624 = null;
                return;
            }
            this.Field1628.Method739();
            this.Field1624 = null;
        }
    }

    @Subscriber
    public void Method1523(Class89 class89) {
        block10: {
            int n;
            int n2;
            int n3;
            float f;
            if (AutoCrystal.mc.player == null || AutoCrystal.mc.world == null) {
                return;
            }
            if (!targetRender.getValue().booleanValue() || this.Field1652 == null || this.Field1653.Method737(3500.0)) break block10;
            GlStateManager.pushMatrix();
            Class507.Method1386();
            if (depth.getValue().booleanValue()) {
                GlStateManager.enableDepth();
            }
            IRenderManager iRenderManager = (IRenderManager)mc.getRenderManager();
            float[] fArray = Color.RGBtoHSB(targetColor.getValue().Method769(), targetColor.getValue().Method770(), targetColor.getValue().Method779(), null);
            float f2 = f = (float)(System.currentTimeMillis() % 7200L) / 7200.0f;
            int n4 = Color.getHSBColor(f2, fArray[1], fArray[2]).getRGB();
            ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
            double d = this.Field1652.lastTickPosX + (this.Field1652.posX - this.Field1652.lastTickPosX) * (double)class89.Method436() - iRenderManager.Method69();
            double d2 = this.Field1652.lastTickPosY + (this.Field1652.posY - this.Field1652.lastTickPosY) * (double)class89.Method436() - iRenderManager.Method70();
            double d3 = this.Field1652.lastTickPosZ + (this.Field1652.posZ - this.Field1652.lastTickPosZ) * (double)class89.Method436() - iRenderManager.Method71();
            double d4 = -Math.cos((double)System.currentTimeMillis() / 1000.0 * (double) animSpeed.getValue().floatValue()) * ((double)this.Field1652.height / 2.0) + (double)this.Field1652.height / 2.0;
            GL11.glLineWidth(width.getValue().floatValue());
            GL11.glBegin(1);
            for (n3 = 0; n3 <= 360; ++n3) {
                Vec3d vec3d = new Vec3d(d + Math.sin((double)n3 * Math.PI / 180.0) * 0.5, d2 + d4 + 0.01, d3 + Math.cos((double)n3 * Math.PI / 180.0) * 0.5);
                arrayList.add(vec3d);
            }
            for (n3 = 0; n3 < arrayList.size() - 1; ++n3) {
                float f3;
                int n5 = n4 >> 16 & 0xFF;
                n2 = n4 >> 8 & 0xFF;
                n = n4 & 0xFF;
                float f4 = orbit.getValue().booleanValue() ? (trail.getValue().booleanValue() ? (float)Math.max(0.0, -0.3183098861837907 * Math.atan(Math.tan(Math.PI * (double)((float)n3 + 1.0f) / (double)arrayList.size() + (double)System.currentTimeMillis() / 1000.0 * (double) orbitSpeed.getValue().floatValue()))) : (float)Math.max(0.0, Math.abs(Math.sin((double)(((float)n3 + 1.0f) / (float)arrayList.size()) * Math.PI + (double)System.currentTimeMillis() / 1000.0 * (double) orbitSpeed.getValue().floatValue())) * 2.0 - 1.0)) : (f3 = fill.getValue() != false ? 1.0f : (float) targetColor.getValue().Method782() / 255.0f);
                if (targetColor.getValue().Method783()) {
                    GL11.glColor4f((float)n5 / 255.0f, (float)n2 / 255.0f, (float)n / 255.0f, f3);
                } else {
                    GL11.glColor4f((float) targetColor.getValue().Method769() / 255.0f, (float) targetColor.getValue().Method770() / 255.0f, (float) targetColor.getValue().Method779() / 255.0f, f3);
                }
                GL11.glVertex3d(arrayList.get(n3).x, arrayList.get(n3).y, arrayList.get(n3).z);
                GL11.glVertex3d(arrayList.get(n3 + 1).x, arrayList.get(n3 + 1).y, arrayList.get(n3 + 1).z);
                n4 = Color.getHSBColor(f2 += 0.0027777778f, fArray[1], fArray[2]).getRGB();
            }
            GL11.glEnd();
            if (fill.getValue().booleanValue()) {
                f2 = f;
                GL11.glBegin(9);
                for (n3 = 0; n3 < arrayList.size() - 1; ++n3) {
                    int n6 = n4 >> 16 & 0xFF;
                    n2 = n4 >> 8 & 0xFF;
                    n = n4 & 0xFF;
                    if (targetColor.getValue().Method783()) {
                        GL11.glColor4f((float)n6 / 255.0f, (float)n2 / 255.0f, (float)n / 255.0f, (float) targetColor.getValue().Method782() / 255.0f);
                    } else {
                        GL11.glColor4f((float) targetColor.getValue().Method769() / 255.0f, (float) targetColor.getValue().Method770() / 255.0f, (float) targetColor.getValue().Method779() / 255.0f, (float) targetColor.getValue().Method782() / 255.0f);
                    }
                    GL11.glVertex3d(arrayList.get(n3).x, arrayList.get(n3).y, arrayList.get(n3).z);
                    GL11.glVertex3d(arrayList.get(n3 + 1).x, arrayList.get(n3 + 1).y, arrayList.get(n3 + 1).z);
                    n4 = Color.getHSBColor(f2 += 0.0027777778f, fArray[1], fArray[2]).getRGB();
                }
                GL11.glEnd();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Class507.Method1385();
            GlStateManager.popMatrix();
        }
    }

    public static Float Method1550(EntityPlayer entityPlayer) {
        return Float.valueOf(AutoCrystal.mc.player.getDistance(entityPlayer));
    }

    public int Method1551(EntityLivingBase entityLivingBase) {
        if (entityLivingBase.isPotionActive(MobEffects.HASTE)) {
            return 6 - (1 + entityLivingBase.getActivePotionEffect(MobEffects.HASTE).getAmplifier());
        }
        return entityLivingBase.isPotionActive(MobEffects.MINING_FATIGUE) ? 6 + (1 + entityLivingBase.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) * 2 : 6;
    }

    public static boolean Method1552(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() < 10.0f;
    }

    public List<Entity> Method1553() {
        return AutoCrystal.mc.world.loadedEntityList.stream().filter(AutoCrystal::Method513).filter(this::Method1555).collect(Collectors.toList());
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() > 0.0f;
    }

    @Override
    public void onEnable() {
        this.Field1623 = null;
        this.Field1624 = null;
        this.Field1626 = null;
        this.Field1627 = null;
        this.Field1625 = null;
        this.Field1644 = null;
        this.Field1648 = null;
        this.Field1651.set(false);
        this.Field1620 = null;
        this.Field1622.Method739();
        this.Field1636 = false;
        this.Field1655 = false;
        this.Field1639.clear();
        this.Field1656 = -1;
        this.Field1657 = -1;
    }

    @Subscriber(priority=50)
    public void Method135(UpdateEvent updateEvent) {
        block8: {
            this.Field1637.forEach(this::Method792);
            --this.Field1654;
            if (this.Field1648 != null) {
                for (Entity entity : AutoCrystal.mc.world.loadedEntityList) {
                    if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistance(this.Field1648.x, this.Field1648.y, this.Field1648.z) <= 6.0)) continue;
                    this.Field1638.put(entity.getEntityId(), System.currentTimeMillis());
                }
                this.Field1648 = null;
            }
            if (updateEvent.isCanceled() || !Class496.Method1959(rotate.getValue() != ACRotateMode.OFF)) {
                return;
            }
            this.Field1623 = null;
            this.Field1624 = null;
            this.Field1626 = null;
            this.Field1627 = null;
            this.Field1655 = false;
            this.Method1561();
            if (rotate.getValue() == ACRotateMode.OFF || this.Field1622.Method737(650.0) || this.Field1620 == null) break block8;
            if (rotate.getValue() == ACRotateMode.TRACK) {
                this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
            }
            if (yawAngle.getValue().floatValue() < 1.0f && yawStep.getValue() != ACYawstepMode.OFF && (this.Field1623 != null || yawStep.getValue() == ACYawstepMode.FULL)) {
                if (this.Field1654 > 0) {
                    this.Field1621[0] = ((IEntityPlayerSP)AutoCrystal.mc.player).Method238();
                    this.Field1623 = null;
                    this.Field1624 = null;
                } else {
                    float f = MathHelper.wrapDegrees(this.Field1621[0] - ((IEntityPlayerSP)AutoCrystal.mc.player).Method238());
                    if (Math.abs(f) > 180.0f * yawAngle.getValue().floatValue()) {
                        this.Field1621[0] = ((IEntityPlayerSP)AutoCrystal.mc.player).Method238() + f * (180.0f * yawAngle.getValue().floatValue() / Math.abs(f));
                        this.Field1623 = null;
                        this.Field1624 = null;
                        this.Field1654 = yawTicks.getValue();
                    }
                }
            }
            NewGui.INSTANCE.Field1139.Method1937(this.Field1621[0], this.Field1621[1]);
        }
    }

    public EntityEnderCrystal Method1554() {
        return this.Field1623;
    }

    public boolean Method1555(Entity entity) {
        return this.Method1558((EntityEnderCrystal)entity);
    }

    public static boolean Method1556(EntityPlayer entityPlayer) {
        return !Class545.Method1009(new BlockPos(entityPlayer)) && (AutoCrystal.mc.world.getBlockState(new BlockPos(entityPlayer)).getBlock() == Blocks.AIR || AutoCrystal.mc.world.getBlockState(new BlockPos(entityPlayer)).getBlock() == Blocks.WEB || AutoCrystal.mc.world.getBlockState(new BlockPos(entityPlayer)).getBlock() instanceof BlockLiquid);
    }

    public List<EntityPlayer> Method1557() {
        List<Object> list = AutoCrystal.mc.world.playerEntities.stream().filter(AutoCrystal::Method141).filter(AutoCrystal::Method122).filter(AutoCrystal::Method1577).filter(AutoCrystal::Method126).filter(AutoCrystal::Method138).filter(AutoCrystal::Method1565).sorted(Comparator.comparing(AutoCrystal::Method1056)).collect(Collectors.toList());
        if (target.getValue() == ACTargetMode.SMART) {
            List list2 = list.stream().filter(AutoCrystal::Method1556).sorted(Comparator.comparing(AutoCrystal::Method137)).collect(Collectors.toList());
            if (list2.size() > 0) {
                list = list2;
            }
            if ((list2 = list.stream().filter(AutoCrystal::Method1552).sorted(Comparator.comparing(AutoCrystal::Method1550)).collect(Collectors.toList())).size() > 0) {
                list = list2;
            }
        }
        return list;
    }

    public boolean Method1558(EntityEnderCrystal entityEnderCrystal) {
        if (AutoCrystal.mc.player.getPositionEyes(1.0f).distanceTo(entityEnderCrystal.getPositionVector()) > (double) breakRange.getValue().floatValue()) {
            return false;
        }
        if (this.Field1638.containsKey(entityEnderCrystal.getEntityId()) && limit.getValue().booleanValue()) {
            return false;
        }
        if (this.Field1638.containsKey(entityEnderCrystal.getEntityId()) && entityEnderCrystal.ticksExisted > ticksExisted.getValue() + attackTicks.getValue()) {
            return false;
        }
        return !(Class475.Method2156(entityEnderCrystal, AutoCrystal.mc.player) + 2.0f >= AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount());
    }

    public void Method1559(EnumHand enumHand) {
        if (!swing.getValue().booleanValue()) {
            return;
        }
        ItemStack itemStack = AutoCrystal.mc.player.getHeldItem(enumHand);
        if (!itemStack.isEmpty() && itemStack.getItem().onEntitySwing(AutoCrystal.mc.player, itemStack)) {
            return;
        }
        if (!AutoCrystal.mc.player.isSwingInProgress || AutoCrystal.mc.player.swingProgressInt >= this.Method1551(AutoCrystal.mc.player) / 2 || AutoCrystal.mc.player.swingProgressInt < 0) {
            AutoCrystal.mc.player.swingProgressInt = -1;
            AutoCrystal.mc.player.isSwingInProgress = true;
            AutoCrystal.mc.player.swingingHand = enumHand;
        }
    }

    public void Method1560(double d, double d2, double d3) {
        if (rotate.getValue() != ACRotateMode.OFF) {
            if (rotate.getValue() == ACRotateMode.INTERACT && this.Field1620 != null && !this.Field1622.Method737(650.0)) {
                if (this.Field1620.y < d2 - 0.1) {
                    this.Field1620 = new Vec3d(this.Field1620.x, d2, this.Field1620.z);
                }
                this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
                this.Field1622.Method739();
                return;
            }
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d - 1.0, d2, d3 - 1.0, d + 1.0, d2 + 2.0, d3 + 1.0);
            Vec3d vec3d = new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.getEntityBoundingBox().minY + (double)AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ);
            double d4 = 0.1;
            double d5 = 0.15;
            double d6 = 0.85;
            if (axisAlignedBB.intersects(AutoCrystal.mc.player.getEntityBoundingBox())) {
                d5 = 0.4;
                d6 = 0.6;
                d4 = 0.05;
            }
            Vec3d vec3d2 = null;
            double[] dArray = null;
            boolean bl = false;
            for (double d7 = d5; d7 <= d6; d7 += d4) {
                for (double d8 = d5; d8 <= d6; d8 += d4) {
                    for (double d9 = d5; d9 <= d6; d9 += d4) {
                        Vec3d vec3d3 = new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * d7, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * d8, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * d9);
                        double d10 = vec3d3.x - vec3d.x;
                        double d11 = vec3d3.y - vec3d.y;
                        double d12 = vec3d3.z - vec3d.z;
                        double[] dArray2 = new double[]{MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(d12, d10)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(d11, Math.sqrt(d10 * d10 + d12 * d12)))))};
                        boolean bl2 = interact.getValue() == ACInteractMode.VANILLA || Class475.Method2143(vec3d3);
                        if (vec3d2 != null && dArray != null) {
                            if (!bl2 && bl || !(AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d3) < AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d2))) continue;
                            vec3d2 = vec3d3;
                            dArray = dArray2;
                            continue;
                        }
                        vec3d2 = vec3d3;
                        dArray = dArray2;
                        bl = bl2;
                    }
                }
            }
            if (vec3d2 != null && dArray != null) {
                this.Field1622.Method739();
                this.Field1620 = vec3d2;
                this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
            }
        }
    }

    public void Method1561() {
        if (AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount() < health.getValue().floatValue() || killAura.getValue() != false && ModuleManager.getModuleByClass(KillAura.class).isEnabled() || pistonAura.getValue() != false && ModuleManager.getModuleByClass(PistonAura.class).isEnabled() || gapping.getValue() != false && AutoCrystal.mc.player.getActiveItemStack().getItem() instanceof ItemFood || mining.getValue().booleanValue() && AutoCrystal.mc.playerController.getIsHittingBlock() && AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool) {
            this.Field1620 = null;
            return;
        }
        if (gapping.getValue().booleanValue() && rightClickGap.getValue().booleanValue() && AutoCrystal.mc.gameSettings.keyBindUseItem.isKeyDown() && AutoCrystal.mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal) {
            int n = -1;
            for (int i = 0; i < 9; ++i) {
                if (AutoCrystal.mc.player.inventory.getStackInSlot(i).getItem() != Items.GOLDEN_APPLE) continue;
                n = i;
                break;
            }
            if (n != -1 && n != AutoCrystal.mc.player.inventory.currentItem) {
                AutoCrystal.mc.player.inventory.currentItem = n;
                AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
                return;
            }
        }
        if (!this.Method538() && !(AutoCrystal.mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal) && autoSwap.getValue() == ACSwapMode.OFF) {
            return;
        }
        List<EntityPlayer> list = this.Method1557();
        EntityEnderCrystal entityEnderCrystal = this.Method1575(list);
        int n = (int)Math.max(100.0f, (float)(Class475.Method2142() + 50) / (Class473.Field2557.Method2190() / 20.0f)) + 150;
        if (entityEnderCrystal != null && this.Field1629.Method737(1000.0f - breakSpeed.getValue().floatValue() * 50.0f) && (entityEnderCrystal.ticksExisted >= ticksExisted.getValue() || timing.getValue() == ACTiming.ADAPTIVE)) {
            this.Field1623 = entityEnderCrystal;
            this.Method1560(this.Field1623.posX, this.Field1623.posY, this.Field1623.posZ);
        }
        if (entityEnderCrystal == null && (confirm.getValue() != ACComfirmMode.FULL || this.Field1646 == null || (double)this.Field1646.ticksExisted >= Math.floor(ticksExisted.getValue().intValue())) && (sync.getValue() != ACSyncMode.STRICT || this.Field1629.Method737(950.0f - breakSpeed.getValue().floatValue() * 50.0f - (float)Class475.Method2142())) && this.Field1628.Method737(1000.0f - placeSpeed.getValue().floatValue() * 50.0f) && (timing.getValue() == ACTiming.SEQUENTIAL || this.Field1642.Method737((float) ticksExisted.getValue().intValue() * 5.0f))) {
            BlockPos blockPos;
            if (confirm.getValue() != ACComfirmMode.OFF && this.Field1644 != null && !this.Field1643.Method737(n + 100) && this.Method512(this.Field1644)) {
                this.Field1624 = this.Field1644;
                this.Field1626 = this.Method1574(this.Field1624);
                this.Field1651.set(false);
                return;
            }
            List<BlockPos> list2 = this.Method1568();
            if (!list2.isEmpty() && (blockPos = this.Method1569(list2, list)) != null) {
                this.Field1624 = blockPos;
                this.Field1626 = this.Method1574(this.Field1624);
            }
        }
        this.Field1651.set(false);
    }

    public void Method792(BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > 1500L) {
            this.Field1637.remove(blockPos);
        }
    }

    public static Thread Method1562(AutoCrystal autoCrystal) {
        return autoCrystal.Field1649;
    }

    public void Method124() {
        BlockPos blockPos;
        List<BlockPos> list;
        if (confirm.getValue() != ACComfirmMode.OFF && (confirm.getValue() != ACComfirmMode.FULL || this.Field1646 == null || (double)this.Field1646.ticksExisted >= Math.floor(ticksExisted.getValue().intValue()))) {
            int n = (int)Math.max(100.0f, (float)(Class475.Method2142() + 50) / (Class473.Field2557.Method2190() / 20.0f)) + 150;
            if (this.Field1644 != null && !this.Field1643.Method737(n + 100) && this.Method512(this.Field1644)) {
                this.Field1624 = this.Field1644;
                this.Field1626 = this.Method1574(this.Field1624);
                if (this.Field1624 != null) {
                    if (!this.Method1572(this.Field1624, this.Field1626)) {
                        this.Field1624 = null;
                        return;
                    }
                    this.Field1628.Method739();
                    this.Field1624 = null;
                }
                return;
            }
        }
        if (!(list = this.Method1568()).isEmpty() && (blockPos = this.Method1569(list, this.Method1557())) != null) {
            this.Field1624 = blockPos;
            this.Field1626 = this.Method1574(this.Field1624);
            if (this.Field1624 != null) {
                if (!this.Method1572(this.Field1624, this.Field1626)) {
                    this.Field1624 = null;
                    return;
                }
                this.Field1628.Method739();
                this.Field1624 = null;
            }
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        this.Field1641.set(tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START);
    }

    public AutoCrystal() {
        super("AutoCrystal", "Automatically places crystals around you", Category.COMBAT);
    }

    public static boolean Method384(Entity entity) {
        return entity.getPositionVector().distanceTo(AutoCrystal.mc.player.getPositionEyes(1.0f)) < (double) breakWalls.getValue().floatValue() || Class475.Method2141(entity.posX, entity.posY, entity.posZ);
    }

    public boolean Method993() {
        int n = Class475.Method2154();
        if (AutoCrystal.mc.player.inventory.currentItem != n && n != -1) {
            if (antiWeakness.getValue() == ACSwapMode.SILENT) {
                this.Field1657 = AutoCrystal.mc.player.inventory.currentItem;
            }
            AutoCrystal.mc.player.inventory.currentItem = n;
            AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
        }
        return n != -1;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block14: {
            block16: {
                block15: {
                    SPacketSoundEffect sPacketSoundEffect;
                    BlockPos blockPos;
                    BlockPos blockPos2;
                    List<BlockPos> list;
                    block13: {
                        if (!(packetEvent.getPacket() instanceof SPacketSpawnObject)) break block13;
                        SPacketSpawnObject sPacketSpawnObject = (SPacketSpawnObject) packetEvent.getPacket();
                        if (sPacketSpawnObject.getType() != 51) break block14;
                        this.Field1637.forEach((arg_0, arg_1) -> this.Method1564(sPacketSpawnObject, arg_0, arg_1));
                        break block14;
                    }
                    if (!(packetEvent.getPacket() instanceof SPacketSoundEffect)) break block15;
                    SPacketSoundEffect sPacketSoundEffect2 = (SPacketSoundEffect) packetEvent.getPacket();
                    if (sPacketSoundEffect2.getCategory() != SoundCategory.BLOCKS || sPacketSoundEffect2.getSound() != SoundEvents.ENTITY_GENERIC_EXPLODE) break block14;
                    if (this.Field1646 != null && this.Field1646.getDistance(sPacketSoundEffect2.getX(), sPacketSoundEffect2.getY(), sPacketSoundEffect2.getZ()) < 6.0) {
                        this.Field1646 = null;
                    }
                    try {
                        BlockPos blockPos3;
                        list = this.Field1640;
                        blockPos2 = blockPos3;
                        blockPos = blockPos3;
                        sPacketSoundEffect = sPacketSoundEffect2;
                    }
                    catch (ConcurrentModificationException concurrentModificationException) {}
                    double d = sPacketSoundEffect.getX();
                    SPacketSoundEffect sPacketSoundEffect3 = sPacketSoundEffect2;
                    double d2 = sPacketSoundEffect3.getY();
                    double d3 = d2 - 1.0;
                    SPacketSoundEffect sPacketSoundEffect4 = sPacketSoundEffect2;
                    double d4 = sPacketSoundEffect4.getZ();
                    blockPos2(d, d3, d4);
                    list.remove(blockPos);
                    break block14;
                }
                if (!(packetEvent.getPacket() instanceof SPacketEntityStatus)) break block16;
                SPacketEntityStatus sPacketEntityStatus = (SPacketEntityStatus) packetEvent.getPacket();
                if (sPacketEntityStatus.getOpCode() != 35 || !(sPacketEntityStatus.getEntity(AutoCrystal.mc.world) instanceof EntityPlayer)) break block14;
                this.Field1639.put((EntityPlayer)sPacketEntityStatus.getEntity(AutoCrystal.mc.world), new Class566());
                break block14;
            }
            if (!(packetEvent.getPacket() instanceof SPacketPlayerPosLook) || !disableOnTP.getValue().booleanValue() || ModuleManager.getModuleByClass(PacketFly.class).isEnabled()) break block14;
            this.toggle();
        }
    }

    public static boolean Method126(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public static void Method1563(AutoCrystal autoCrystal) {
        autoCrystal.Method124();
    }

    public void Method1564(SPacketSpawnObject sPacketSpawnObject, BlockPos blockPos, Long l) {
        block26: {
            block25: {
                if (!(this.Method1581((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, sPacketSpawnObject.getX(), sPacketSpawnObject.getY() - 1.0, sPacketSpawnObject.getZ()) < 1.0)) break block26;
                ConcurrentHashMap<BlockPos, Long> concurrentHashMap = this.Field1637;
                BlockPos blockPos2 = blockPos;
                concurrentHashMap.remove(blockPos2);
                this.Field1644 = null;
                Setting<Boolean> setting = limit;
                Object t = setting.getValue();
                Boolean bl = (Boolean)t;
                boolean bl2 = bl;
                if (bl2) break block25;
                Setting<Boolean> setting2 = inhibit;
                Object t2 = setting2.getValue();
                Boolean bl3 = (Boolean)t2;
                boolean bl4 = bl3;
                if (!bl4) break block25;
                Class566 class566 = this.Field1647;
                try {
                    class566.Method739();
                }
                catch (ConcurrentModificationException concurrentModificationException) {
                    // empty catch block
                }
            }
            if (timing.getValue() != ACTiming.ADAPTIVE) {
                return;
            }
            if (!this.Field1630.Method737(swapDelay.getValue().floatValue() * 100.0f)) {
                return;
            }
            if (this.Field1641.get()) {
                return;
            }
            if (AutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                return;
            }
            if (this.Field1638.containsKey(sPacketSpawnObject.getEntityID())) {
                return;
            }
            if (AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount() < health.getValue().floatValue() || killAura.getValue() != false && ModuleManager.getModuleByClass(KillAura.class).isEnabled() || pistonAura.getValue() != false && ModuleManager.getModuleByClass(PistonAura.class).isEnabled() || gapping.getValue() != false && AutoCrystal.mc.player.getActiveItemStack().getItem() instanceof ItemFood || mining.getValue().booleanValue() && AutoCrystal.mc.playerController.getIsHittingBlock() && AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool) {
                this.Field1620 = null;
                return;
            }
            Vec3d vec3d = new Vec3d(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ());
            if (AutoCrystal.mc.player.getPositionEyes(1.0f).distanceTo(vec3d) > (double) breakRange.getValue().floatValue()) {
                return;
            }
            if (!this.Field1629.Method737(1000.0f - breakSpeed.getValue().floatValue() * 50.0f)) {
                return;
            }
            if (Class475.Method2150(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ(), AutoCrystal.mc.player) + 2.0f >= AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount()) {
                return;
            }
            this.Field1638.put(sPacketSpawnObject.getEntityID(), System.currentTimeMillis());
            this.Field1648 = new Vec3d(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ());
            CPacketUseEntity cPacketUseEntity = new CPacketUseEntity();
            ((ICPacketUseEntity)cPacketUseEntity).Method506(sPacketSpawnObject.getEntityID());
            ((ICPacketUseEntity)cPacketUseEntity).Method507(CPacketUseEntity.Action.ATTACK);
            AutoCrystal.mc.player.connection.sendPacket(new CPacketAnimation(this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
            AutoCrystal.mc.player.connection.sendPacket(cPacketUseEntity);
            this.Method1559(this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            this.Field1634 = new BlockPos(sPacketSpawnObject.getX(), sPacketSpawnObject.getY() - 1.0, sPacketSpawnObject.getZ());
            this.Field1635.Method739();
            this.Field1629.Method739();
            this.Field1642.Method739();
            if (sync.getValue() == ACSyncMode.MERGE) {
                this.Field1628.Method738(0L);
            }
            if (sync.getValue() == ACSyncMode.STRICT) {
                this.Field1651.set(true);
            }
            if (sync.getValue() == ACSyncMode.MERGE) {
                this.Method1580();
            }
        }
    }

    public static boolean Method122(EntityPlayer entityPlayer) {
        return !entityPlayer.isDead;
    }

    public boolean Method132(EntityPlayer entityPlayer) {
        if (!armorBreaker.getValue().booleanValue()) {
            return false;
        }
        for (int i = 3; i >= 0; --i) {
            double d;
            ItemStack itemStack = entityPlayer.inventory.armorInventory.get(i);
            if (itemStack == null || !((d = itemStack.getItem().getDurabilityForDisplay(itemStack)) > (double) depletion.getValue().floatValue())) continue;
            return true;
        }
        return false;
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return entityPlayer != AutoCrystal.mc.player && entityPlayer != mc.getRenderViewEntity();
    }

    public static boolean Method1565(EntityPlayer entityPlayer) {
        return AutoCrystal.mc.player.getDistance(entityPlayer) < enemyRange.getValue().floatValue();
    }

    public void Method1566(Integer n, Long l) {
        if (System.currentTimeMillis() - l > 1000L) {
            this.Field1638.remove(n);
        }
    }

    public boolean Method1567(EntityEnderCrystal entityEnderCrystal) {
        if (entityEnderCrystal != null) {
            if (antiWeakness.getValue() != ACSwapMode.OFF && AutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS) && !(AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !this.Method993()) {
                return false;
            }
            if (!this.Field1630.Method737(swapDelay.getValue().floatValue() * 100.0f)) {
                return false;
            }
            AutoCrystal.mc.playerController.attackEntity(AutoCrystal.mc.player, entityEnderCrystal);
            AutoCrystal.mc.player.connection.sendPacket(new CPacketAnimation(this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
            this.Method1559(this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (this.Field1657 != -1 && AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                AutoCrystal.mc.player.inventory.currentItem = this.Field1657;
                AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field1657));
                this.Field1657 = -1;
            }
            if (sync.getValue() == ACSyncMode.MERGE) {
                this.Field1628.Method738(0L);
            }
            if (sync.getValue() == ACSyncMode.STRICT) {
                this.Field1651.set(true);
            }
            this.Field1645.Method739();
            this.Field1646 = entityEnderCrystal;
            this.Field1634 = new BlockPos(entityEnderCrystal).down();
            this.Field1635.Method739();
            return true;
        }
        return false;
    }

    @Subscriber
    public void Method139(Class89 class89) {
        block43: {
            Object object;
            if (AutoCrystal.mc.world == null || AutoCrystal.mc.player == null) {
                return;
            }
            if (box.getValue().booleanValue() && this.Field1631 != null) {
                if (this.Field1633.Method737(1000.0)) {
                    return;
                }
                object = null;
                WorldClient worldClient = AutoCrystal.mc.world;
                BlockPos blockPos = this.Field1631;
                IBlockState iBlockState = worldClient.getBlockState(blockPos);
                WorldClient worldClient2 = AutoCrystal.mc.world;
                BlockPos blockPos2 = this.Field1631;
                AxisAlignedBB axisAlignedBB = iBlockState.getBoundingBox(worldClient2, blockPos2);
                BlockPos blockPos3 = this.Field1631;
                AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(blockPos3);
                try {
                    object = axisAlignedBB2;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (object == null) {
                    return;
                }
                Class507.Method1386();
                Class507.Method1379(object, color.getValue().Method784((int)((float) color.getValue().Method782() * (1.0f - (float)Math.max(0L, System.currentTimeMillis() - this.Field1633.Method736() - 150L) / 850.0f * fade.getValue().floatValue()))));
                if (outlineWidth.getValue().floatValue() > 0.0f) {
                    Class507.Method1374(object, outlineWidth.getValue().floatValue(), outline.getValue().Method784((int)((float) outline.getValue().Method782() * (1.0f - (float)Math.max(0L, System.currentTimeMillis() - this.Field1633.Method736() - 150L) / 850.0f * fade.getValue().floatValue()))));
                }
                Class507.Method1385();
            }
            if (breaking.getValue().booleanValue() && this.Field1634 != null && !this.Field1635.Method737(1000.0)) {
                if (!this.Field1634.equals(this.Field1631)) {
                    object = null;
                    WorldClient worldClient = AutoCrystal.mc.world;
                    BlockPos blockPos = this.Field1634;
                    IBlockState iBlockState = worldClient.getBlockState(blockPos);
                    WorldClient worldClient3 = AutoCrystal.mc.world;
                    BlockPos blockPos4 = this.Field1634;
                    AxisAlignedBB axisAlignedBB = iBlockState.getBoundingBox(worldClient3, blockPos4);
                    BlockPos blockPos5 = this.Field1634;
                    AxisAlignedBB axisAlignedBB3 = axisAlignedBB.offset(blockPos5);
                    try {
                        object = axisAlignedBB3;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (object == null) {
                        return;
                    }
                    Class507.Method1386();
                    Class507.Method1379(object, color.getValue().Method784((int)((float) color.getValue().Method784((int)((double) color.getValue().Method782() * 0.5)).Method782() * (1.0f - (float)Math.max(0L, System.currentTimeMillis() - this.Field1635.Method736() - 150L) / 850.0f * fade.getValue().floatValue()))));
                    if (outlineWidth.getValue().floatValue() > 0.0f) {
                        Class507.Method1374(object, outlineWidth.getValue().floatValue(), outline.getValue().Method784((int)((float) outline.getValue().Method782() * (1.0f - (float)Math.max(0L, System.currentTimeMillis() - this.Field1635.Method736() - 150L) / 850.0f * fade.getValue().floatValue()))));
                    }
                    Class507.Method1385();
                }
            }
            if (damage.getValue() == ACDamageMode.NONE || this.Field1631 == null) break block43;
            if (this.Field1633.Method737(1000.0)) {
                return;
            }
            GlStateManager.pushMatrix();
            BlockPos blockPos = this.Field1631;
            int n = blockPos.getX();
            float f = (float)n + 0.5f;
            BlockPos blockPos6 = this.Field1631;
            int n2 = blockPos6.getY();
            float f2 = (float)n2 + 0.5f;
            BlockPos blockPos7 = this.Field1631;
            int n3 = blockPos7.getZ();
            float f3 = (float)n3 + 0.5f;
            EntityPlayerSP entityPlayerSP = AutoCrystal.mc.player;
            float f4 = 1.0f;
            try {
                Class502.Method1395(f, f2, f3, entityPlayerSP, f4);
            }
            catch (Exception exception) {
                // empty catch block
            }
            object = (Math.floor(this.Field1632) == (double)this.Field1632 ? Integer.valueOf((int)this.Field1632) : String.format("%.1f", Float.valueOf(this.Field1632))) + "";
            GlStateManager.disableDepth();
            if (customFont.getValue().booleanValue()) {
                GlStateManager.disableTexture2D();
            }
            GlStateManager.disableLighting();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (customFont.getValue().booleanValue()) {
                GlStateManager.scale(0.3, 0.3, 1.0);
                if (damage.getValue() == ACDamageMode.SHADED) {
                    Class425.Field958.Method826((String)object, (float)(-((double)Class425.Field958.Method830((String)object) / 2.0)), (int)(-Class425.Field958.Method831((String)object) / 2.0f), -1);
                } else {
                    Class425.Field958.Method828((String)object, (float)(-((double)Class425.Field958.Method830((String)object) / 2.0)), (int)(-Class425.Field958.Method831((String)object) / 2.0f), -1);
                }
                GlStateManager.scale(3.3333333333333335, 3.3333333333333335, 1.0);
            } else if (damage.getValue() == ACDamageMode.SHADED) {
                AutoCrystal.mc.fontRenderer.drawStringWithShadow((String)object, (float)((int)(-((double)AutoCrystal.mc.fontRenderer.getStringWidth((String)object) / 2.0))), -4.0f, -1);
            } else {
                AutoCrystal.mc.fontRenderer.drawString((String)object, (int)(-((double)AutoCrystal.mc.fontRenderer.getStringWidth((String)object) / 2.0)), -4, -1);
            }
            GlStateManager.enableLighting();
            if (customFont.getValue().booleanValue()) {
                GlStateManager.enableTexture2D();
            }
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
    }

    public boolean Method395(Entity entity) {
        return !this.Field1638.containsKey(entity.getEntityId()) && (!(entity instanceof EntityEnderCrystal) || entity.ticksExisted > 20);
    }

    public List<BlockPos> Method1568() {
        NonNullList nonNullList = NonNullList.create();
        nonNullList.addAll(AutoCrystal.Method1578(new BlockPos(AutoCrystal.mc.player), placeRange.getValue().floatValue(), placeRange.getValue().intValue(), false, true, 0).stream().filter(this::Method512).collect(Collectors.toList()));
        return nonNullList;
    }

    public static Float Method137(EntityPlayer entityPlayer) {
        return Float.valueOf(AutoCrystal.mc.player.getDistance(entityPlayer));
    }

    public static boolean Method992() {
        return sync.getValue() == ACSyncMode.MERGE;
    }

    public BlockPos Method1569(List<BlockPos> list, List<EntityPlayer> list2) {
        if (list2.isEmpty()) {
            return null;
        }
        float f = 0.5f;
        EntityPlayer entityPlayer = null;
        BlockPos blockPos = null;
        this.Field1655 = false;
        EntityPlayer entityPlayer2 = null;
        for (BlockPos blockPos2 : list) {
            float f2 = Class475.Method2152(blockPos2, AutoCrystal.mc.player);
            if (!((double)f2 + 2.0 < (double)(AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount())) || !(f2 <= maxSelfPlace.getValue().floatValue())) continue;
            if (target.getValue() != ACTargetMode.ALL) {
                entityPlayer2 = list2.get(0);
                if (entityPlayer2.getDistance((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5) > (double) crystalRange.getValue().floatValue()) continue;
                float f3 = Class475.Method2152(blockPos2, entityPlayer2);
                if (this.Method1576(entityPlayer2, f3) && (blockPos == null || entityPlayer2.getDistanceSq(blockPos2) < entityPlayer2.getDistanceSq(blockPos))) {
                    entityPlayer = entityPlayer2;
                    f = f3;
                    blockPos = blockPos2;
                    this.Field1655 = true;
                    continue;
                }
                if (this.Field1655 || !(f3 > f) || !(f3 > f2) && !(f3 > entityPlayer2.getHealth() + entityPlayer2.getAbsorptionAmount()) || f3 < minDamage.getValue().floatValue() && entityPlayer2.getHealth() + entityPlayer2.getAbsorptionAmount() > facePlaceHealth.getValue().floatValue() && !PlayerUtil.Method1087(facePlace.getValue().Method851()) && !this.Method132(entityPlayer2)) continue;
                f = f3;
                entityPlayer = entityPlayer2;
                blockPos = blockPos2;
                continue;
            }
            for (EntityPlayer entityPlayer3 : list2) {
                if (entityPlayer3.equals(entityPlayer2) || entityPlayer3.getDistance((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5) > (double) crystalRange.getValue().floatValue()) continue;
                float f4 = Class475.Method2152(blockPos2, entityPlayer3);
                if (this.Method1576(entityPlayer3, f4) && (blockPos == null || entityPlayer3.getDistanceSq(blockPos2) < entityPlayer3.getDistanceSq(blockPos))) {
                    entityPlayer = entityPlayer3;
                    f = f4;
                    blockPos = blockPos2;
                    this.Field1655 = true;
                    continue;
                }
                if (this.Field1655 || !(f4 > f) || !(f4 > f2) && !(f4 > entityPlayer3.getHealth() + entityPlayer3.getAbsorptionAmount()) || f4 < minDamage.getValue().floatValue() && entityPlayer3.getHealth() + entityPlayer3.getAbsorptionAmount() > facePlaceHealth.getValue().floatValue() && !PlayerUtil.Method1087(facePlace.getValue().Method851()) && !this.Method132(entityPlayer3)) continue;
                f = f4;
                entityPlayer = entityPlayer3;
                blockPos = blockPos2;
            }
        }
        if (entityPlayer != null && blockPos != null) {
            NewGui.INSTANCE.Field1133.Method430(entityPlayer);
            this.Method1645(entityPlayer.getName());
            this.Field1652 = entityPlayer;
            this.Field1653.Method739();
        } else {
            this.Method1645(null);
        }
        if (blockPos != null) {
            this.Field1631 = blockPos;
            this.Field1632 = f;
        }
        this.Field1644 = blockPos;
        this.Field1643.Method739();
        return blockPos;
    }

    public static AtomicBoolean Method1570(AutoCrystal autoCrystal) {
        return autoCrystal.Field1641;
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketHeldItemChange)) break block0;
            this.Field1630.Method739();
        }
    }

    public static Float Method1571(Entity entity) {
        return Float.valueOf(AutoCrystal.mc.player.getDistance(entity));
    }

    public boolean Method1572(BlockPos blockPos, EnumFacing enumFacing) {
        if (blockPos != null) {
            if (autoSwap.getValue() != ACSwapMode.OFF && !this.Method539()) {
                return false;
            }
            if (!this.Method538() && AutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
                if (this.Field1656 != -1) {
                    AutoCrystal.mc.player.inventory.currentItem = this.Field1656;
                    AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field1656));
                    this.Field1656 = -1;
                }
                return false;
            }
            if (AutoCrystal.mc.world.getBlockState(blockPos.up()).getBlock() == Blocks.FIRE) {
                AutoCrystal.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos.up(), EnumFacing.DOWN));
                AutoCrystal.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos.up(), EnumFacing.DOWN));
                if (this.Field1656 != -1) {
                    AutoCrystal.mc.player.inventory.currentItem = this.Field1656;
                    AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field1656));
                    this.Field1656 = -1;
                }
                return true;
            }
            FastUse.Field1871 = true;
            this.Field1636 = true;
            if (this.Field1627 == null) {
                Class545.Method996(blockPos, AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0), this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, enumFacing, true);
            } else {
                AutoCrystal.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (float)(this.Field1627.hitVec.x - (double)blockPos.getX()), (float)(this.Field1627.hitVec.y - (double)blockPos.getY()), (float)(this.Field1627.hitVec.z - (double)blockPos.getZ())));
                AutoCrystal.mc.player.connection.sendPacket(new CPacketAnimation(this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
            }
            if (this.Field1655 && this.Field1652 != null) {
                this.Field1639.put(this.Field1652, new Class566());
            }
            this.Field1636 = false;
            this.Field1637.put(blockPos, System.currentTimeMillis());
            this.Field1640.add(blockPos);
            this.Field1633.Method739();
            this.Field1625 = blockPos;
            if (this.Field1656 != -1) {
                AutoCrystal.mc.player.inventory.currentItem = this.Field1656;
                AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field1656));
                this.Field1656 = -1;
            }
            return true;
        }
        return false;
    }

    public BlockPos Method1573() {
        return this.Field1624;
    }

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    public EnumFacing Method1574(BlockPos blockPos) {
        block26: {
            block25: {
                if (blockPos == null) break block25;
                if (AutoCrystal.mc.player != null) break block26;
            }
            return null;
        }
        EnumFacing enumFacing = null;
        if (interact.getValue() != ACInteractMode.VANILLA) {
            double[] dArray;
            Vec3d vec3d;
            RayTraceResult rayTraceResult;
            Vec3d vec3d2;
            Vec3d vec3d3;
            float f;
            float f2;
            float f3;
            float f4;
            double[] dArray2;
            double d;
            double d2;
            double d3;
            double d4;
            double d5;
            Vec3d vec3d4;
            double d6;
            double d7;
            double d8;
            Vec3d vec3d5 = null;
            double[] dArray3 = null;
            double d9 = 0.45;
            double d10 = 0.05;
            double d11 = 0.95;
            Vec3d vec3d6 = new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.getEntityBoundingBox().minY + (double)AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ);
            for (d8 = d10; d8 <= d11; d8 += d9) {
                for (d7 = d10; d7 <= d11; d7 += d9) {
                    for (d6 = d10; d6 <= d11; d6 += d9) {
                        vec3d4 = new Vec3d(blockPos).addVector(d8, d7, d6);
                        d5 = vec3d6.distanceTo(vec3d4);
                        d4 = vec3d4.x - vec3d6.x;
                        d3 = vec3d4.y - vec3d6.y;
                        d2 = vec3d4.z - vec3d6.z;
                        d = MathHelper.sqrt(d4 * d4 + d2 * d2);
                        dArray2 = new double[]{MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(d2, d4)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(d3, d))))};
                        f4 = MathHelper.cos((float)(-dArray2[0] * 0.01745329238474369 - 3.1415927410125732));
                        f3 = MathHelper.sin((float)(-dArray2[0] * 0.01745329238474369 - 3.1415927410125732));
                        f2 = -MathHelper.cos((float)(-dArray2[1] * 0.01745329238474369));
                        f = MathHelper.sin((float)(-dArray2[1] * 0.01745329238474369));
                        vec3d3 = new Vec3d(f3 * f2, f, f4 * f2);
                        vec3d2 = vec3d6.addVector(vec3d3.x * d5, vec3d3.y * d5, vec3d3.z * d5);
                        rayTraceResult = AutoCrystal.mc.world.rayTraceBlocks(vec3d6, vec3d2, false, true, false);
                        if (!(placeWalls.getValue().floatValue() >= placeRange.getValue().floatValue())) {
                            if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK || !rayTraceResult.getBlockPos().equals(blockPos)) continue;
                        }
                        vec3d = vec3d4;
                        dArray = dArray2;
                        if (vec3d5 != null && dArray3 != null && (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK || enumFacing == null)) {
                            if (!(AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d) < AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d5))) continue;
                            vec3d5 = vec3d;
                            dArray3 = dArray;
                            if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                            enumFacing = rayTraceResult.sideHit;
                            this.Field1627 = rayTraceResult;
                            continue;
                        }
                        vec3d5 = vec3d;
                        dArray3 = dArray;
                        if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                        enumFacing = rayTraceResult.sideHit;
                        this.Field1627 = rayTraceResult;
                    }
                }
            }
            if (placeWalls.getValue().floatValue() < placeRange.getValue().floatValue() && interact.getValue() == ACInteractMode.STRICT) {
                if (dArray3 != null && enumFacing != null) {
                    this.Field1622.Method739();
                    this.Field1620 = vec3d5;
                    this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
                    return enumFacing;
                }
                for (d8 = d10; d8 <= d11; d8 += d9) {
                    for (d7 = d10; d7 <= d11; d7 += d9) {
                        for (d6 = d10; d6 <= d11; d6 += d9) {
                            vec3d4 = new Vec3d(blockPos).addVector(d8, d7, d6);
                            d5 = vec3d6.distanceTo(vec3d4);
                            d4 = vec3d4.x - vec3d6.x;
                            d3 = vec3d4.y - vec3d6.y;
                            d2 = vec3d4.z - vec3d6.z;
                            d = MathHelper.sqrt(d4 * d4 + d2 * d2);
                            dArray2 = new double[]{MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(d2, d4)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(d3, d))))};
                            f4 = MathHelper.cos((float)(-dArray2[0] * 0.01745329238474369 - 3.1415927410125732));
                            f3 = MathHelper.sin((float)(-dArray2[0] * 0.01745329238474369 - 3.1415927410125732));
                            f2 = -MathHelper.cos((float)(-dArray2[1] * 0.01745329238474369));
                            f = MathHelper.sin((float)(-dArray2[1] * 0.01745329238474369));
                            vec3d3 = new Vec3d(f3 * f2, f, f4 * f2);
                            vec3d2 = vec3d6.addVector(vec3d3.x * d5, vec3d3.y * d5, vec3d3.z * d5);
                            rayTraceResult = AutoCrystal.mc.world.rayTraceBlocks(vec3d6, vec3d2, false, true, true);
                            if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                            vec3d = vec3d4;
                            dArray = dArray2;
                            if (vec3d5 != null && dArray3 != null && (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK || enumFacing == null)) {
                                if (!(AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d) < AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d5))) continue;
                                vec3d5 = vec3d;
                                dArray3 = dArray;
                                if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                                enumFacing = rayTraceResult.sideHit;
                                this.Field1627 = rayTraceResult;
                                continue;
                            }
                            vec3d5 = vec3d;
                            dArray3 = dArray;
                            if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                            enumFacing = rayTraceResult.sideHit;
                            this.Field1627 = rayTraceResult;
                        }
                    }
                }
            } else {
                if (dArray3 != null) {
                    this.Field1622.Method739();
                    this.Field1620 = vec3d5;
                    this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
                }
                if (enumFacing != null) {
                    return enumFacing;
                }
            }
        } else {
            Vec3d vec3d;
            EnumFacing enumFacing2 = null;
            Vec3d vec3d7 = null;
            for (EnumFacing enumFacing3 : EnumFacing.values()) {
                vec3d = new Vec3d((double)blockPos.getX() + 0.5 + (double)enumFacing3.getDirectionVec().getX() * 0.5, (double)blockPos.getY() + 0.5 + (double)enumFacing3.getDirectionVec().getY() * 0.5, (double)blockPos.getZ() + 0.5 + (double)enumFacing3.getDirectionVec().getZ() * 0.5);
                RayTraceResult rayTraceResult = AutoCrystal.mc.world.rayTraceBlocks(new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY + (double)AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ), vec3d, false, true, false);
                if (rayTraceResult == null || !rayTraceResult.typeOfHit.equals(RayTraceResult.Type.BLOCK) || !rayTraceResult.getBlockPos().equals(blockPos) || vec3d7 != null && !(AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d) < AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d7))) continue;
                vec3d7 = vec3d;
                enumFacing2 = enumFacing3;
                this.Field1627 = rayTraceResult;
            }
            if (enumFacing2 != null) {
                this.Field1622.Method739();
                this.Field1620 = vec3d7;
                this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
                return enumFacing2;
            }
            for (EnumFacing enumFacing3 : EnumFacing.values()) {
                vec3d = new Vec3d((double)blockPos.getX() + 0.5 + (double)enumFacing3.getDirectionVec().getX() * 0.5, (double)blockPos.getY() + 0.5 + (double)enumFacing3.getDirectionVec().getY() * 0.5, (double)blockPos.getZ() + 0.5 + (double)enumFacing3.getDirectionVec().getZ() * 0.5);
                if (vec3d7 != null && !(AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d) < AutoCrystal.mc.player.getPositionVector().addVector(0.0, AutoCrystal.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d7))) continue;
                vec3d7 = vec3d;
                enumFacing2 = enumFacing3;
            }
            if (enumFacing2 != null) {
                this.Field1622.Method739();
                this.Field1620 = vec3d7;
                this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
                return enumFacing2;
            }
        }
        if ((double)blockPos.getY() > AutoCrystal.mc.player.posY + (double)AutoCrystal.mc.player.getEyeHeight()) {
            this.Field1622.Method739();
            this.Field1620 = new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 0.5);
            this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
            return EnumFacing.DOWN;
        }
        this.Field1622.Method739();
        this.Field1620 = new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 0.5);
        this.Field1621 = RotationUtil.Method1946(AutoCrystal.mc.player.getPositionEyes(1.0f), this.Field1620);
        return EnumFacing.UP;
    }

    public EntityEnderCrystal Method1575(List<EntityPlayer> list) {
        this.Field1638.forEach(this::Method1566);
        if (sync.getValue() == ACSyncMode.STRICT && !limit.getValue().booleanValue() && this.Field1651.get()) {
            return null;
        }
        EntityEnderCrystal entityEnderCrystal = null;
        int n = (int)Math.max(100.0f, (float)(Class475.Method2142() + 50) / (Class473.Field2557.Method2190() / 20.0f)) + 150;
        if (inhibit.getValue().booleanValue() && !limit.getValue().booleanValue() && !this.Field1645.Method737(n) && this.Field1646 != null && AutoCrystal.mc.world.getEntityByID(this.Field1646.getEntityId()) != null && this.Method1558(this.Field1646)) {
            entityEnderCrystal = this.Field1646;
            return entityEnderCrystal;
        }
        List<Entity> list2 = this.Method1553();
        if (list2.isEmpty()) {
            return null;
        }
        entityEnderCrystal = list2.stream().filter(this::Method392).filter(AutoCrystal::Method384).min(Comparator.comparing(AutoCrystal::Method1571)).orElse(null);
        return entityEnderCrystal;
    }

    public static boolean Method519() {
        return limit.getValue() == false;
    }

    public boolean Method512(BlockPos blockPos) {
        if (AutoCrystal.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && AutoCrystal.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        BlockPos blockPos2 = blockPos.add(0, 1, 0);
        if (!(AutoCrystal.mc.world.getBlockState(blockPos2).getBlock() == Blocks.AIR || AutoCrystal.mc.world.getBlockState(blockPos2).getBlock() == Blocks.FIRE && setting.getValue().booleanValue())) {
            return false;
        }
        BlockPos blockPos3 = blockPos.add(0, 2, 0);
        if (!protocol.getValue().booleanValue() && AutoCrystal.mc.world.getBlockState(blockPos3).getBlock() != Blocks.AIR) {
            AutoCrystal.mc.world.getBlockState(blockPos2).getBlock();
            return false;
        }
        if (check.getValue().booleanValue() && !Class475.Method2141((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 0.5)) {
            Vec3d vec3d = new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 0.5);
            if (AutoCrystal.mc.player.getPositionEyes(1.0f).distanceTo(vec3d) > (double) breakWalls.getValue().floatValue()) {
                return false;
            }
        }
        if (placeWalls.getValue().floatValue() < placeRange.getValue().floatValue() && ((double)blockPos.getY() > AutoCrystal.mc.player.posY + (double)AutoCrystal.mc.player.getEyeHeight() ? AutoCrystal.mc.player.getDistance((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5) > (double) placeWalls.getValue().floatValue() && !Class475.Method2151(blockPos) : AutoCrystal.mc.player.getDistance((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5) > (double) placeWalls.getValue().floatValue() && !Class475.Method2151(blockPos))) {
            return false;
        }
        return AutoCrystal.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos2, blockPos3.add(1, 1, 1))).stream().filter(this::Method395).count() == 0L;
    }

    public boolean Method392(Entity entity) {
        return this.Field1640.contains(new BlockPos(entity.posX, entity.posY - 1.0, entity.posZ)) || Class475.Method2156((EntityEnderCrystal)entity, AutoCrystal.mc.player) < maxSelfBreak.getValue().floatValue();
    }

    public boolean Method1576(EntityPlayer entityPlayer, float f) {
        if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 2.0f && (double)f > (double)entityPlayer.getHealth() + (double)entityPlayer.getAbsorptionAmount() + 0.5 && f <= 4.0f) {
            Class566 class566 = this.Field1639.get(entityPlayer);
            return class566 == null || class566.Method737(500.0);
        }
        return false;
    }

    public static Float Method1056(EntityPlayer entityPlayer) {
        return Float.valueOf(AutoCrystal.mc.player.getDistance(entityPlayer));
    }

    public static boolean Method1577(EntityPlayer entityPlayer) {
        return !Class546.Method963(entityPlayer);
    }

    public static List<BlockPos> Method1578(BlockPos blockPos, float f, int n, boolean bl, boolean bl2, int n2) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        int n3 = blockPos.getX();
        int n4 = blockPos.getY();
        int n5 = blockPos.getZ();
        int n6 = n3 - (int)f;
        while ((float)n6 <= (float)n3 + f) {
            int n7 = n5 - (int)f;
            while ((float)n7 <= (float)n5 + f) {
                int n8 = bl2 ? n4 - (int)f : n4;
                while (true) {
                    float f2 = n8;
                    float f3 = bl2 ? (float)n4 + f : (float)(n4 + n);
                    if (!(f2 < f3)) break;
                    double d = (n3 - n6) * (n3 - n6) + (n5 - n7) * (n5 - n7) + (bl2 ? (n4 - n8) * (n4 - n8) : 0);
                    if (!(!(d < (double)(f * f)) || bl && d < (double)((f - 1.0f) * (f - 1.0f)))) {
                        BlockPos blockPos2 = new BlockPos(n6, n8 + n2, n7);
                        arrayList.add(blockPos2);
                    }
                    ++n8;
                }
                ++n7;
            }
            ++n6;
        }
        return arrayList;
    }

    public static AtomicBoolean Method1579(AutoCrystal autoCrystal) {
        return autoCrystal.Field1650;
    }

    public void Method1580() {
        if (offset.getValue().floatValue() == 0.0f) {
            this.Method124();
        } else {
            this.Field1650.set(true);
            if (this.Field1649 == null || this.Field1649.isInterrupted() || !this.Field1649.isAlive()) {
                if (this.Field1649 == null) {
                    this.Field1649 = new Thread(ThreadUtil.Method727(this));
                }
                if (this.Field1649 != null && (this.Field1649.isInterrupted() || !this.Field1649.isAlive())) {
                    this.Field1649 = new Thread(ThreadUtil.Method727(this));
                }
                if (this.Field1649 != null && this.Field1649.getState() == Thread.State.NEW) {
                    Thread thread = this.Field1649;
                    try {
                        thread.start();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }
    }

    public double Method1581(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        return Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
    }

    public boolean Method539() {
        block3: {
            if (this.Method538()) {
                return true;
            }
            int n = Class475.Method2147();
            if (n == -1) {
                return false;
            }
            if (AutoCrystal.mc.player.inventory.currentItem == n) break block3;
            if (autoSwap.getValue() == ACSwapMode.SILENT) {
                this.Field1656 = AutoCrystal.mc.player.inventory.currentItem;
            }
            AutoCrystal.mc.player.inventory.currentItem = n;
            AutoCrystal.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
        }
        return true;
    }

    public boolean Method538() {
        return AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }
}