package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.Objects;

import me.darki.konas.mixin.mixins.IItemRenderer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class65;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;

public class OldAnnimations
extends Module {
    public Setting<Boolean> showSwapping = new Setting<>("ShowSwapping", true);

    public OldAnnimations() {
        super("OldAnimations", "1.8 Hit Animations", Category.RENDER);
    }

    @Subscriber
    public void Method1074(Class65 class65) {
        block5: {
            class65.setCanceled(true);
            ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setPrevEquippedProgressMainHand(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressMainHand());
            ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setPrevEquippedProgressOffHand(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressOffHand());
            EntityPlayerSP entityPlayerSP = OldAnnimations.mc.player;
            ItemStack itemStack = entityPlayerSP.getHeldItemMainhand();
            ItemStack itemStack2 = entityPlayerSP.getHeldItemOffhand();
            if (entityPlayerSP.isRowingBoat()) {
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setEquippedProgressMainHand(MathHelper.clamp(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressMainHand() - 0.4f, 0.0f, 1.0f));
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setEquippedProgressOffHand(MathHelper.clamp(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressOffHand() - 0.4f, 0.0f, 1.0f));
            } else {
                boolean bl;
                boolean bl2 = this.showSwapping.getValue() != false && ForgeHooksClient.shouldCauseReequipAnimation(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getItemStackMainHand(), itemStack, entityPlayerSP.inventory.currentItem);
                boolean bl3 = bl = this.showSwapping.getValue() != false && ForgeHooksClient.shouldCauseReequipAnimation(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getItemStackOffHand(), itemStack2, -1);
                if (!bl2 && !Objects.equals(Float.valueOf(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressMainHand()), itemStack)) {
                    ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setItemStackMainHand(itemStack);
                }
                if (!bl2 && !Objects.equals(Float.valueOf(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressOffHand()), itemStack2)) {
                    ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setItemStackOffHand(itemStack2);
                }
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setEquippedProgressMainHand(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressMainHand() + MathHelper.clamp((!bl2 ? 1.0f : 0.0f) - ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressMainHand(), -0.4f, 0.4f));
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setEquippedProgressOffHand(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressOffHand() + MathHelper.clamp((float)(!bl ? 1 : 0) - ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressOffHand(), -0.4f, 0.4f));
            }
            if (((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressMainHand() < 0.1f) {
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setItemStackMainHand(itemStack);
            }
            if (!(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).getEquippedProgressOffHand() < 0.1f)) break block5;
            ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).setItemStackOffHand(itemStack2);
        }
    }
}