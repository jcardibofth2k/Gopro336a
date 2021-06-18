package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.AttackEntityEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IItemTool;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.TimerUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.BlockPos;

public class AutoTool
extends Module {
    public Setting<Boolean> weapons = new Setting<>("Weapons", true);
    public Setting<Class389> mining = new Setting<>("Mining", Class389.NORMAL);
    public Setting<Boolean> strict = new Setting<>("Strict", true);
    public TimerUtil Field2025 = new TimerUtil();
    public boolean Field2026 = false;
    public int Field2027 = -1;

    @Subscriber
    public void Method1845(ClickBlockEvent clickBlockEvent) {
        if (AutoTool.mc.player == null || AutoTool.mc.world == null || clickBlockEvent.Method597() != AutoTool.mc.player) {
            return;
        }
        if (this.mining.getValue() == Class389.NONE || this.mining.getValue() == Class389.SILENT) {
            return;
        }
        if (this.mining.getValue() == Class389.RETURN && this.Field2026) {
            return;
        }
        this.Method1851(AutoTool.Method1850(clickBlockEvent.Method1149()), false);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block5: {
            block4: {
                block3: {
                    block2: {
                        if (AutoTool.mc.player == null || AutoTool.mc.world == null) break block2;
                        if (this.mining.getValue() == Class389.RETURN) break block3;
                    }
                    return;
                }
                if (AutoTool.mc.playerController.getIsHittingBlock() || !this.Field2026 || this.strict.getValue().booleanValue() && !this.Field2025.GetDifferenceTiming(100.0)) break block4;
                this.Method1851(this.Field2027, true);
                break block5;
            }
            if (!this.Field2026 || this.mining.getValue() != Class389.SILENT) break block5;
            this.Method1851(this.Field2027, true);
        }
    }

    @Subscriber
    public void Method1846(Class646 class646) {
        if (AutoTool.mc.player == null || AutoTool.mc.world == null) {
            return;
        }
        if (this.mining.getValue() != Class389.SILENT) {
            return;
        }
        if (this.Field2026) {
            this.Method1851(this.Field2027, true);
        }
        this.Method1851(AutoTool.Method1850(class646.Method1149()), false);
    }

    public AutoTool() {
        super("AutoTool", "Swap to the best tool when mining and attacking", Category.PLAYER);
    }

    @Subscriber
    public void Method1847(AttackEntityEvent attackEntityEvent) {
        if (AutoTool.mc.player == null || AutoTool.mc.world == null || !this.weapons.getValue().booleanValue()) {
            return;
        }
        this.Method1851(this.Method1848(attackEntityEvent.Method293()), false);
    }

    public int Method1848(Entity entity) {
        int n = -1;
        float f = 0.0f;
        for (int i = 0; i < 9; ++i) {
            float f2;
            ItemStack itemStack = AutoTool.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || itemStack.getItem() == Items.AIR) continue;
            if (itemStack.getItem() instanceof ItemSword) {
                f2 = ((ItemSword)itemStack.getItem()).getAttackDamage() + EnchantmentHelper.getModifierForCreature(itemStack, entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED);
                if (!(f2 > f)) continue;
                f = f2;
                n = i;
                continue;
            }
            if (!(itemStack.getItem() instanceof ItemTool) || !((f2 = ((IItemTool)itemStack.getItem()).getAttackDamage() + EnchantmentHelper.getModifierForCreature(itemStack, entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED)) > f)) continue;
            f = f2;
            n = i;
        }
        return n;
    }

    @Subscriber
    public void Method1849(Class23 class23) {
        block0: {
            if (!this.Field2026 || this.mining.getValue() != Class389.SILENT) break block0;
            this.Method1851(this.Field2027, true);
        }
    }

    public static int Method1850(BlockPos blockPos) {
        IBlockState iBlockState = AutoTool.mc.world.getBlockState(blockPos);
        int n = -1;
        double d = 0.0;
        for (int i = 0; i < 9; ++i) {
            int n2;
            float f;
            ItemStack itemStack = AutoTool.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || itemStack.getItem() == Items.AIR || !((f = itemStack.getDestroySpeed(iBlockState)) > 1.0f) || !((double)(f = (float)((double)f + ((n2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack)) > 0 ? Math.pow(n2, 2.0) + 1.0 : 0.0))) > d)) continue;
            d = f;
            n = i;
        }
        return n;
    }

    public void Method1851(int n, boolean bl) {
        if (n != -1) {
            if (!bl) {
                this.Field2026 = true;
                this.Field2027 = AutoTool.mc.player.inventory.currentItem;
            } else {
                this.Field2026 = false;
                this.Field2027 = -1;
            }
            AutoTool.mc.player.inventory.currentItem = n;
            AutoTool.mc.player.connection.sendPacket(new CPacketHeldItemChange(AutoTool.mc.player.inventory.currentItem));
        }
        this.Field2025.UpdateCurrentTime();
    }
}