package me.darki.konas.module.misc;

import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.Class167;
import me.darki.konas.Class262;
import me.darki.konas.Class299;
import me.darki.konas.Class306;
import me.darki.konas.Class492;
import me.darki.konas.Class50;
import me.darki.konas.Class545;
import me.darki.konas.Class566;
import me.darki.konas.TickEvent;
import me.darki.konas.Class645;
import me.darki.konas.Class653;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MiddleClick
extends Module {
    public static Setting<Class306> Field960 = new Setting<>("Action", Class306.MENU);
    public static Setting<Integer> Field961 = new Setting<>("Range", 40, 250, 10, 10).Method1191(MiddleClick::Method519);
    public static Setting<Boolean> Field962 = new Setting<>("ThroughWalls", true).Method1191(MiddleClick::Method394);
    public Setting<Boolean> Field963 = new Setting<>("Rocket", false);
    public Setting<Boolean> Field964 = new Setting<>("EP", false);
    public Setting<Boolean> Field965 = new Setting<>("XP", false);
    public Setting<Boolean> Field966 = new Setting<>("XPInHoles", false).Method1191(this.Field965::getValue);
    public Class566 Field967 = new Class566();
    public int Field968 = -1;
    public boolean Field969 = true;

    public static EntityPlayer Method968(int n) {
        Entity entity = mc.getRenderViewEntity();
        if (entity != null) {
            Vec3d vec3d = MiddleClick.mc.player.getPositionEyes(1.0f);
            for (float f = 0.0f; f < (float)n; f += 0.5f) {
                vec3d = vec3d.add(MiddleClick.mc.player.getLookVec().scale(0.5));
                if (!((Boolean)Field962.getValue()).booleanValue() && MiddleClick.mc.world.getBlockState(new BlockPos(vec3d.x, vec3d.y, vec3d.z)).getBlock() != Blocks.AIR) {
                    return null;
                }
                for (EntityPlayer entityPlayer : MiddleClick.mc.world.playerEntities) {
                    AxisAlignedBB axisAlignedBB;
                    if (entityPlayer == MiddleClick.mc.player || (axisAlignedBB = entityPlayer.getEntityBoundingBox()) == null) continue;
                    if (entityPlayer.getDistance((Entity)MiddleClick.mc.player) > 6.0f) {
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
        if (GameSettings.isKeyDown((KeyBinding)MiddleClick.mc.gameSettings.keyBindPickBlock) && this.Field967.Method737(350.0)) {
            if (((Boolean)this.Field965.getValue()).booleanValue() && (!((Boolean)this.Field966.getValue()).booleanValue() || Class545.Method1009(new BlockPos((Entity)MiddleClick.mc.player)))) {
                int n = this.Method524();
                if (this.Field969) {
                    this.Field968 = MiddleClick.mc.player.inventory.currentItem;
                    this.Field969 = false;
                }
                if (n != -1) {
                    MiddleClick.mc.player.inventory.currentItem = n;
                    MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                    MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                }
            }
        } else if (this.Field968 != -1) {
            MiddleClick.mc.player.inventory.currentItem = this.Field968;
            MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field968));
            this.Field968 = -1;
            this.Field969 = true;
        }
    }

    public static Vec3d Method970(float f, float f2) {
        float f3 = MathHelper.cos((float)(-f2 * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = MathHelper.sin((float)(-f2 * ((float)Math.PI / 180) - (float)Math.PI));
        float f5 = -MathHelper.cos((float)(-f * ((float)Math.PI / 180)));
        float f6 = MathHelper.sin((float)(-f * ((float)Math.PI / 180)));
        return new Vec3d((double)(f4 * f5), (double)f6, (double)(f3 * f5));
    }

    public MiddleClick() {
        super("MiddleClick", Category.MISC, "MiddleClickPearl", "MiddleClickEP", "MCF", "MCP", "MiddleClickFriends");
    }

    public static boolean Method394() {
        return Field960.getValue() == Class306.MENU;
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
        if (Field960.getValue() == Class306.FRIEND && MiddleClick.mc.objectMouseOver.entityHit != null) {
            Entity entity = MiddleClick.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityPlayer) {
                if (Class492.Method1989(entity.getName())) {
                    Class492.Method1992(entity.getName());
                    Logger.Method1118("Removed \u00c2\u00a7b" + entity.getName() + "\u00c2\u00a7r as a friend!");
                } else {
                    Class492.Method1990(entity.getName(), entity.getUniqueID().toString());
                    if (Class167.Method1610(Class299.class).isEnabled() && ((Boolean)Class299.Field1451.getValue()).booleanValue()) {
                        EventDispatcher.Companion.dispatch(new Class645(entity.getName(), "I just friended you on Konas!"));
                    }
                    Logger.Method1118("Added \u00c2\u00a7b" + entity.getName() + "\u00c2\u00a7r as a friend!");
                }
                this.Field967.Method739();
                return;
            }
        }
        if (((Boolean)this.Field963.getValue()).booleanValue() && this.Method969() != -1) {
            this.Field967.Method739();
            int n2 = this.Method969();
            n = MiddleClick.mc.player.inventory.currentItem;
            if (n2 != -1) {
                MiddleClick.mc.player.inventory.currentItem = n2;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n2));
                MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = n;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                return;
            }
        }
        if (((Boolean)this.Field964.getValue()).booleanValue() && (!((Boolean)this.Field965.getValue()).booleanValue() || ((Boolean)this.Field966.getValue()).booleanValue() && !Class545.Method1009(new BlockPos((Entity)MiddleClick.mc.player)))) {
            int n3 = this.getBlockInHotbar();
            n = MiddleClick.mc.player.inventory.currentItem;
            if (n3 != -1) {
                MiddleClick.mc.player.inventory.currentItem = n3;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n3));
                MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = n;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
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
        return Field960.getValue() == Class306.MENU;
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
            if (Field960.getValue() != Class306.MENU) {
                return;
            }
            if (!GameSettings.isKeyDown((KeyBinding)MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            EntityPlayer entityPlayer = MiddleClick.Method968((Integer)Field961.getValue());
            if (entityPlayer == null) break block5;
            this.Field967.Method739();
            if (MiddleClick.mc.currentScreen == null) {
                mc.displayGuiScreen((GuiScreen)new Class262(entityPlayer));
            }
        }
    }
}
