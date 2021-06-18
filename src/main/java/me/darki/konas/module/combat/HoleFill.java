package me.darki.konas.module.combat;

import com.google.common.collect.Lists;
import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;
import me.darki.konas.module.Category;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.module.movement.PacketFly;
import me.darki.konas.unremaped.Class305;
import me.darki.konas.unremaped.Class443;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.util.CrystalUtils;
import me.darki.konas.unremaped.Class490;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.util.rotation.Rotation;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.unremaped.Class523;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.unremaped.Class546;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class HoleFill
extends Module {
    public static Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static Setting<Boolean> swing = new Setting<>("Swing", true);
    public static Setting<Double> range = new Setting<>("Range", 5.0, 6.0, 1.0, 0.1);
    public static Setting<Boolean> strictDirection = new Setting<>("StrictDirection", false);
    public static Setting<Integer> actionShift = new Setting<>("ActionShift", 1, 3, 1, 1);
    public static Setting<Integer> actionInterval = new Setting<>("ActionInterval", 0, 5, 0, 1);
    public static Setting<Boolean> rayTrace = new Setting<>("RayTrace", false);
    public static Setting<Boolean> aDouble = new Setting<>("Double", false);
    public static Setting<Boolean> jumpDisable = new Setting<>("JumpDisable", false);
    public static Setting<Boolean> filter = new Setting<>("Filter", false);
    public static Setting<Class443> validBlocks = new Setting<>("ValidBlocks", new Class443());
    public static Setting<Class305> smart = new Setting<>("Smart", Class305.ALWAYS);
    public static Setting<Double> enemyRange = new Setting<>("EnemyRange", 10.0, 15.0, 1.0, 0.5);
    public static Setting<Boolean> disableWhenNone = new Setting<>("DisableWhenNone", false);
    public Map<BlockPos, Long> Field1045 = new ConcurrentHashMap<BlockPos, Long>();
    public Class490 Field1046 = null;
    public int Field1047;
    public ArrayList<BlockPos> Field1048;
    public Map<BlockPos, Long> Field1049 = new ConcurrentHashMap<BlockPos, Long>();
    public int Field1050 = 0;

    public static boolean Method122(EntityPlayer entityPlayer) {
        return (double)HoleFill.mc.player.getDistance(entityPlayer) < enemyRange.getValue();
    }

    public static boolean Method512(BlockPos blockPos) {
        return Rotation.Method1970(blockPos, strictDirection.getValue(), rayTrace.getValue(), true);
    }

    public static Float Method1051(EntityPlayer entityPlayer, BlockPos blockPos) {
        return Float.valueOf(smart.getValue() != Class305.NONE && entityPlayer != null ? MathHelper.sqrt(HoleFill.mc.player.getDistanceSq(entityPlayer)) : MathHelper.sqrt(HoleFill.mc.player.getDistanceSq(blockPos)));
    }

    public void Method1052(int n, BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > (long)(n + 100)) {
            this.Field1049.remove(blockPos);
        }
    }

    public boolean Method1053(Item item) {
        if (item instanceof ItemBlock) {
            if (filter.getValue().booleanValue()) {
                return validBlocks.getValue().Method682().contains(((ItemBlock)item).getBlock());
            }
            return true;
        }
        return false;
    }

    @Subscriber(priority=60)
    public void Method135(UpdateEvent updateEvent) {
        block8: {
            block7: {
                this.Field1046 = null;
                if (jumpDisable.getValue().booleanValue() && HoleFill.mc.player.prevPosY < HoleFill.mc.player.posY) {
                    this.toggle();
                }
                if (updateEvent.isCanceled() || !Rotation.Method1959(rotate.getValue())) {
                    return;
                }
                if (ModuleManager.getModuleByClass(PacketFly.class).isEnabled()) {
                    return;
                }
                if (this.Field1050 < actionInterval.getValue()) {
                    ++this.Field1050;
                }
                if (this.Field1050 < actionInterval.getValue()) {
                    return;
                }
                int n = this.Method524();
                this.Field1047 = -1;
                if (n == -1) {
                    return;
                }
                this.Field1048 = Lists.newArrayList(BlockPos.getAllInBox(HoleFill.mc.player.getPosition().add(-range.getValue().doubleValue(), -range.getValue().doubleValue(), -range.getValue().doubleValue()), HoleFill.mc.player.getPosition().add(range.getValue().doubleValue(), range.getValue().doubleValue(), range.getValue().doubleValue())));
                int n2 = CrystalUtils.Method2142();
                this.Field1049.forEach((arg_0, arg_1) -> this.Method1052(n2, arg_0, arg_1));
                if (smart.getValue() == Class305.TARGET && this.Method1054() == null) {
                    return;
                }
                BlockPos blockPos = StreamSupport.stream(this.Field1048.spliterator(), false).filter(this::Method515).filter(HoleFill::Method526).filter(HoleFill::Method512).min(Comparator.comparing(HoleFill::Method1055)).orElse(null);
                if (blockPos == null) break block7;
                this.Field1046 = Rotation.Method1964(blockPos, rotate.getValue(), false, strictDirection.getValue(), rayTrace.getValue());
                if (this.Field1046 == null) break block8;
                this.Field1050 = 0;
                this.Field1047 = n;
                this.Field1045.put(blockPos, System.currentTimeMillis());
                this.Field1049.put(blockPos, System.currentTimeMillis());
                break block8;
            }
            if (!disableWhenNone.getValue().booleanValue()) break block8;
            this.toggle();
        }
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return entityPlayer != HoleFill.mc.player;
    }

    public EntityPlayer Method1054() {
        return HoleFill.mc.world.playerEntities.stream().filter(HoleFill::Method132).filter(HoleFill::Method141).filter(HoleFill::Method138).filter(HoleFill::Method122).min(Comparator.comparing(HoleFill::Method1056)).orElse(null);
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class546.Method963(entityPlayer);
    }

    public void Method792(BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > 1000L) {
            this.Field1045.remove(blockPos);
        } else {
            Class523.Method1218(blockPos);
        }
    }

    public static boolean Method526(BlockPos blockPos) {
        return HoleFill.mc.player.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) <= range.getValue();
    }

    public static Float Method1055(BlockPos blockPos) {
        return Float.valueOf(MathHelper.sqrt(HoleFill.mc.player.getDistanceSq(blockPos)));
    }

    @Override
    public void onEnable() {
        if (HoleFill.mc.player == null || HoleFill.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field1048 = Lists.newArrayList(BlockPos.getAllInBox(HoleFill.mc.player.getPosition().add(-range.getValue().doubleValue(), -range.getValue().doubleValue(), -range.getValue().doubleValue()), HoleFill.mc.player.getPosition().add(range.getValue().doubleValue(), range.getValue().doubleValue(), range.getValue().doubleValue())));
        this.Field1050 = actionInterval.getValue();
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (HoleFill.mc.world == null || HoleFill.mc.player == null) {
            return;
        }
        this.Field1045.forEach(this::Method792);
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public HoleFill() {
        super("HoleFill", Category.COMBAT);
    }

    public int Method524() {
        ItemStack itemStack = HoleFill.mc.player.getHeldItemMainhand();
        if (!itemStack.isEmpty() && this.Method1053(itemStack.getItem())) {
            return HoleFill.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            itemStack = HoleFill.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || !this.Method1053(itemStack.getItem())) continue;
            return i;
        }
        return -1;
    }

    @Subscriber(priority=15)
    public void Method123(Class50 class50) {
        block7: {
            if (this.Field1046 == null || this.Field1047 == -1) break block7;
            boolean bl = HoleFill.mc.player.inventory.currentItem != this.Field1047;
            int n = HoleFill.mc.player.inventory.currentItem;
            if (bl) {
                HoleFill.mc.player.inventory.currentItem = this.Field1047;
                HoleFill.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field1047));
            }
            boolean bl2 = HoleFill.mc.player.isSprinting();
            boolean bl3 = Class545.Method1004(this.Field1046.Method1982());
            if (bl2) {
                HoleFill.mc.player.connection.sendPacket(new CPacketEntityAction(HoleFill.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (bl3) {
                HoleFill.mc.player.connection.sendPacket(new CPacketEntityAction(HoleFill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            //Vec3d vec3d = new Vec3d((Vec3i)this.Field1046.Method1982()).add(0.5, 0.5, 0.5).add(new Vec3d(this.Field1046.Method1980().getDirectionVec()).scale(0.5));
            Vec3d vec3d = new Vec3d(this.Field1046.Method1982()).addVector(0.5, 0.5, 0.5).add(new Vec3d(this.Field1046.Method1980().getDirectionVec()).scale(0.5));
            Rotation.Method1969(this.Field1046.Method1982(), vec3d, EnumHand.MAIN_HAND, this.Field1046.Method1980(), true, swing.getValue());
            double d = HoleFill.mc.player.posX - ((IEntityPlayerSP)HoleFill.mc.player).getLastReportedPosX();
            double d2 = HoleFill.mc.player.posY - ((IEntityPlayerSP)HoleFill.mc.player).getLastReportedPosY();
            double d3 = HoleFill.mc.player.posZ - ((IEntityPlayerSP)HoleFill.mc.player).getLastReportedPosZ();
            boolean bl4 = d * d + d2 * d2 + d3 * d3 > 9.0E-4;
            for (int i = 0; i < actionShift.getValue() - 1 && !bl4; ++i) {
                Class490 class490;
                EntityPlayer entityPlayer = this.Method1054();
                BlockPos blockPos = StreamSupport.stream(this.Field1048.spliterator(), false).filter(this::Method515).min(Comparator.comparing(arg_0 -> HoleFill.Method1051(entityPlayer, arg_0))).orElse(null);
                if (blockPos == null || !Rotation.Method1967(blockPos, strictDirection.getValue()) || (class490 = Rotation.Method1968(blockPos, rotate.getValue(), true, strictDirection.getValue())) == null) break;
                //Vec3d vec3d2 = new Vec3d((Vec3i)class490.Method1982()).add(0.5, 0.5, 0.5).add(new Vec3d(class490.Method1980().getDirectionVec()).scale(0.5));
                Vec3d vec3d2 = new Vec3d(class490.Method1982()).addVector(0.5, 0.5, 0.5).add(new Vec3d(class490.Method1980().getDirectionVec()).scale(0.5));
                Rotation.Method1969(class490.Method1982(), vec3d2, EnumHand.MAIN_HAND, class490.Method1980(), true, swing.getValue());
                this.Field1049.put(blockPos, System.currentTimeMillis());
                this.Field1045.put(blockPos, System.currentTimeMillis());
            }
            if (bl3) {
                HoleFill.mc.player.connection.sendPacket(new CPacketEntityAction(HoleFill.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (bl2) {
                HoleFill.mc.player.connection.sendPacket(new CPacketEntityAction(HoleFill.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            if (bl) {
                HoleFill.mc.player.inventory.currentItem = n;
                HoleFill.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
            }
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketBlockChange && this.Field1045.containsKey(((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition()) && ((SPacketBlockChange) packetEvent.getPacket()).getBlockState().getBlock() != Blocks.AIR) {
            this.Field1045.remove(((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition());
        }
    }

    public static Float Method1056(EntityPlayer entityPlayer) {
        return Float.valueOf(HoleFill.mc.player.getDistance(entityPlayer));
    }

    public boolean Method515(BlockPos blockPos) {
        if (this.Field1049.containsKey(blockPos)) {
            return false;
        }
        for (Entity entity : HoleFill.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow) continue;
            return false;
        }
        if (aDouble.getValue().booleanValue()) {
            BlockPos blockPos2 = Class545.Method1007(blockPos);
            if (blockPos2 == null) {
                blockPos2 = Class545.Method995(blockPos);
            }
            if (blockPos2 != null) {
                return true;
            }
        }
        return Class545.Method1009(blockPos);
    }
}