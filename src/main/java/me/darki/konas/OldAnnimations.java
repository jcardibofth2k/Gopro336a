package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.Objects;

import me.darki.konas.mixin.mixins.IItemRenderer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;

public class OldAnnimations
extends Module {
    public Setting<Boolean> showSwapping = new Setting<>("ShowSwapping", true);

    public OldAnnimations() {
        super("OldAnimations", "1.8 Hit Animations", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method1074(Class65 class65) {
        block5: {
            class65.setCanceled(true);
            ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2289(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2286());
            ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2291(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2288());
            EntityPlayerSP entityPlayerSP = OldAnnimations.mc.player;
            ItemStack itemStack = entityPlayerSP.getHeldItemMainhand();
            ItemStack itemStack2 = entityPlayerSP.getHeldItemOffhand();
            if (entityPlayerSP.isRowingBoat()) {
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2285(MathHelper.clamp((float)(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2286() - 0.4f), (float)0.0f, (float)1.0f));
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2287(MathHelper.clamp((float)(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2288() - 0.4f), (float)0.0f, (float)1.0f));
            } else {
                boolean bl;
                boolean bl2 = (Boolean)this.showSwapping.getValue() != false && ForgeHooksClient.shouldCauseReequipAnimation((ItemStack)((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2294(), (ItemStack)itemStack, (int)entityPlayerSP.inventory.currentItem);
                boolean bl3 = bl = (Boolean)this.showSwapping.getValue() != false && ForgeHooksClient.shouldCauseReequipAnimation((ItemStack)((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2296(), (ItemStack)itemStack2, (int)-1);
                if (!bl2 && !Objects.equals(Float.valueOf(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2286()), itemStack)) {
                    ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2293(itemStack);
                }
                if (!bl2 && !Objects.equals(Float.valueOf(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2288()), itemStack2)) {
                    ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2295(itemStack2);
                }
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2285(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2286() + MathHelper.clamp((float)((!bl2 ? 1.0f : 0.0f) - ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2286()), (float)-0.4f, (float)0.4f));
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2287(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2288() + MathHelper.clamp((float)((float)(!bl ? 1 : 0) - ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2288()), (float)-0.4f, (float)0.4f));
            }
            if (((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2286() < 0.1f) {
                ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2293(itemStack);
            }
            if (!(((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2288() < 0.1f)) break block5;
            ((IItemRenderer) OldAnnimations.mc.entityRenderer.itemRenderer).Method2295(itemStack2);
        }
    }
}
