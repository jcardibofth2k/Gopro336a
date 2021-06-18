package me.darki.konas.module.misc;

import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class262;
import me.darki.konas.unremaped.ExtraChat;
import me.darki.konas.unremaped.Class306;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.Class645;
import me.darki.konas.unremaped.Class653;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MiddleClick
extends Module {
    public static Setting<Class306> action = new Setting<>("Action", Class306.MENU);
    public static Setting<Integer> range = new Setting<>("Range", 40, 250, 10, 10).visibleIf(MiddleClick::Method519);
    public static Setting<Boolean> throughWalls = new Setting<>("ThroughWalls", true).visibleIf(MiddleClick::Method394);
    public Setting<Boolean> rocket = new Setting<>("Rocket", false);
    public Setting<Boolean> eP = new Setting<>("EP", false);
    public Setting<Boolean> xP = new Setting<>("XP", false);
    public Setting<Boolean> xPInHoles = new Setting<>("XPInHoles", false).visibleIf(this.Field965::getValue);
    public TimerUtil Field967 = new TimerUtil();
    public int Field968 = -1;
    public boolean Field969 = true;

    public static EntityPlayer Method968(int n) {
        Entity entity = mc.getRenderViewEntity();
        if (entity != null) {
            Vec3d vec3d = MiddleClick.mc.player.getPositionEyes(1.0f);
            for (float f = 0.0f; f < (float)n; f += 0.5f) {
                vec3d = vec3d.add(MiddleClick.mc.player.getLookVec().scale(0.5));
                if (!throughWalls.getValue().booleanValue() && MiddleClick.mc.world.getBlockState(new BlockPos(vec3d.x, vec3d.y, vec3d.z)).getBlock() != Blocks.AIR) {
                    return null;
                }
                for (EntityPlayer entityPlayer : MiddleClick.mc.world.playerEntities) {
                    AxisAlignedBB axisAlignedBB;
                    if (entityPlayer == MiddleClick.mc.player || (axisAlignedBB = entityPlayer.getEntityBoundingBox()) == null) continue;
                    if (entityPlayer.getDistance(MiddleClick.mc.player) > 6.0f) {
                        axisAlignedBB = axisAlignedBB.grow(0.5);
                    }
                    if (!axisAlignedBB.contains(vec3d)) continue;
                    return entityPlayer;
                }
            }
        }
        return null;
    }

    public int Method969() {
        int n = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS) {
            n = MiddleClick.mc.player.inventory.currentItem;
        }
        if (n == -1) {
            for (int i = 0; i < 9; ++i) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(i).getItem() != Items.FIREWORKS) continue;
                n = i;
                break;
            }
        }
        return n;
    }

    @Subscriber
    public void Method123(Class50 class50) {
        if (GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock) && this.Field967.GetDifferenceTiming(350.0)) {
            if (this.xP.getValue().booleanValue() && (!this.xPInHoles.getValue().booleanValue() || Class545.Method1009(new BlockPos(MiddleClick.mc.player)))) {
                int n = this.Method524();
                if (this.Field969) {
                    this.Field968 = MiddleClick.mc.player.inventory.currentItem;
                    this.Field969 = false;
                }
                if (n != -1) {
                    MiddleClick.mc.player.inventory.currentItem = n;
                    MiddleClick.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
                    MiddleClick.mc.playerController.processRightClick(MiddleClick.mc.player, MiddleClick.mc.world, EnumHand.MAIN_HAND);
                }
            }
        } else if (this.Field968 != -1) {
            MiddleClick.mc.player.inventory.currentItem = this.Field968;
            MiddleClick.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field968));
            this.Field968 = -1;
            this.Field969 = true;
        }
    }

    public static Vec3d Method970(float f, float f2) {
        float f3 = MathHelper.cos(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float f6 = MathHelper.sin(-f * ((float)Math.PI / 180));
        return new Vec3d(f4 * f5, f6, f3 * f5);
    }

    public MiddleClick() {
        super("MiddleClick", Category.MISC, "MiddleClickPearl", "MiddleClickEP", "MCF", "MCP", "MiddleClickFriends");
    }

    public static boolean Method394() {
        return action.getValue() == Class306.MENU;
    }

    @Subscriber
    public void Method971(Class653 class653) {
        int n;
        if (MiddleClick.mc.player == null || MiddleClick.mc.world == null) {
            return;
        }
        if (class653.Method1164() != MiddleClick.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
            return;
        }
        if (action.getValue() == Class306.FRIEND && MiddleClick.mc.objectMouseOver.entityHit != null) {
            Entity entity = MiddleClick.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityPlayer) {
                if (Class492.Method1989(entity.getName())) {
                    Class492.Method1992(entity.getName());
                    Logger.Method1118("Removed \u00c2\u00a7b" + entity.getName() + "\u00c2\u00a7r as a friend!");
                } else {
                    Class492.Method1990(entity.getName(), entity.getUniqueID().toString());
                    if (ModuleManager.getModuleByClass(ExtraChat.class).isEnabled() && ExtraChat.notifyFriended.getValue().booleanValue()) {
                        EventDispatcher.Companion.dispatch(new Class645(entity.getName(), "I just friended you on Konas!"));
                    }
                    Logger.Method1118("Added \u00c2\u00a7b" + entity.getName() + "\u00c2\u00a7r as a friend!");
                }
                this.Field967.UpdateCurrentTime();
                return;
            }
        }
        if (this.rocket.getValue().booleanValue() && this.Method969() != -1) {
            this.Field967.UpdateCurrentTime();
            int n2 = this.Method969();
            n = MiddleClick.mc.player.inventory.currentItem;
            if (n2 != -1) {
                MiddleClick.mc.player.inventory.currentItem = n2;
                MiddleClick.mc.player.connection.sendPacket(new CPacketHeldItemChange(n2));
                MiddleClick.mc.playerController.processRightClick(MiddleClick.mc.player, MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = n;
                MiddleClick.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
                return;
            }
        }
        if (this.eP.getValue().booleanValue() && (!this.xP.getValue().booleanValue() || this.xPInHoles.getValue().booleanValue() && !Class545.Method1009(new BlockPos(MiddleClick.mc.player)))) {
            int n3 = this.getBlockInHotbar();
            n = MiddleClick.mc.player.inventory.currentItem;
            if (n3 != -1) {
                MiddleClick.mc.player.inventory.currentItem = n3;
                MiddleClick.mc.player.connection.sendPacket(new CPacketHeldItemChange(n3));
                MiddleClick.mc.playerController.processRightClick(MiddleClick.mc.player, MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = n;
                MiddleClick.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
                return;
            }
        }
    }

    public int getBlockInHotbar() {
        int n = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL) {
            n = MiddleClick.mc.player.inventory.currentItem;
        }
        if (n == -1) {
            for (int i = 0; i < 9; ++i) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(i).getItem() != Items.ENDER_PEARL) continue;
                n = i;
                break;
            }
        }
        return n;
    }

    public int Method524() {
        int n = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            n = MiddleClick.mc.player.inventory.currentItem;
        }
        if (n == -1) {
            for (int i = 0; i < 9; ++i) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(i).getItem() != Items.EXPERIENCE_BOTTLE) continue;
                n = i;
                break;
            }
        }
        return n;
    }

    public static boolean Method519() {
        return action.getValue() == Class306.MENU;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block5: {
            if (MiddleClick.mc.player == null || MiddleClick.mc.world == null) {
                return;
            }
            if (tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) {
                return;
            }
            if (action.getValue() != Class306.MENU) {
                return;
            }
            if (!GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            EntityPlayer entityPlayer = MiddleClick.Method968(range.getValue());
            if (entityPlayer == null) break block5;
            this.Field967.UpdateCurrentTime();
            if (MiddleClick.mc.currentScreen == null) {
                mc.displayGuiScreen(new Class262(entityPlayer));
            }
        }
    }
}