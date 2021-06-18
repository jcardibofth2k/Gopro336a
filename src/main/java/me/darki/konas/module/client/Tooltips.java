package me.darki.konas.module.client;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;

import me.darki.konas.event.events.DrawScreenEvent;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class116;
import me.darki.konas.unremaped.Class136;
import me.darki.konas.unremaped.Class557;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class Tooltips
extends Module {
    public static Setting<Boolean> texture = new Setting<>("Texture", true);
    public static Setting<Boolean> customColor = new Setting<>("CustomColor", false);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-65281, true));
    public static Setting<Boolean> shulkers = new Setting<>("Shulkers", true);
    public static Setting<Boolean> maps = new Setting<>("Maps", true);
    public int Field551;
    public int Field552;

    public Tooltips() {
        super("Tooltips", "Enchances inventory tooltips", Category.CLIENT, new String[0]);
    }

    @Subscriber
    public void Method624(ItemTooltipEvent itemTooltipEvent) {
        if (((Boolean)maps.getValue()).booleanValue() && itemTooltipEvent.getItemStack().getItem() instanceof ItemMap) {
            itemTooltipEvent.getToolTip().clear();
            itemTooltipEvent.getToolTip().add(itemTooltipEvent.getItemStack().getDisplayName());
        }
    }

    @Subscriber
    public void Method625(DrawScreenEvent drawScreenEvent) {
        block3: {
            if (!((Boolean)maps.getValue()).booleanValue() || !(drawScreenEvent.Method272() instanceof GuiContainer) || !(Tooltips.mc.player.inventory.getItemStack().getItem() instanceof ItemAir)) break block3;
            Slot slot = ((GuiContainer) drawScreenEvent.Method272()).getSlotUnderMouse();
            if (slot == null || !slot.getHasStack()) {
                return;
            }
            ItemStack itemStack = slot.getStack();
            if (itemStack.getItem() instanceof ItemMap) {
                MapData mapData = ((ItemMap)itemStack.getItem()).getMapData(itemStack, (World) Tooltips.mc.world);
                if (mapData == null) {
                    return;
                }
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/map/map_background.png"));
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                GlStateManager.translate((double)this.Field551, (double)((double)this.Field552 - 72.0), (double)0.0);
                GlStateManager.scale((double)0.5, (double)0.5, (double)1.0);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                bufferBuilder.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
                bufferBuilder.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
                bufferBuilder.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
                bufferBuilder.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
                tessellator.draw();
                Tooltips.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    @Subscriber
    public void Method626(Class136 class136) {
        if (((Boolean)maps.getValue()).booleanValue() && class136.Method1926().getItem() instanceof ItemMap) {
            this.Field551 = class136.Method1928();
            this.Field552 = class136.Method1927();
        }
    }

    public void Method627(ItemStack itemStack, int n, int n2) {
        NBTTagCompound nBTTagCompound = itemStack.getTagCompound();
        GlStateManager.enableBlend();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        if (((Boolean)texture.getValue()).booleanValue()) {
            NBTTagCompound nBTTagCompound2;
            int n3 = 144;
            int n4 = n + 12;
            int n5 = n2 - 12;
            int n6 = 48 + Class557.Method802();
            Tooltips.mc.getRenderItem().zLevel = 300.0f;
            Color color = (Boolean)customColor.getValue() != false ? new Color(((ColorValue)color.getValue()).Method774()) : new Color(((BlockShulkerBox)((ItemShulkerBox)itemStack.getItem()).getBlock()).getColor().getColorValue());
            this.Method629((double)n4 - 8.5, n5 - 3, 0.0, 0.0, n3 + 3, n6 + 6, color);
            Tooltips.mc.fontRenderer.drawString(itemStack.getDisplayName(), n + 8, n2 - 12, 0xFFFFFF);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.scale((double)0.75, (double)0.75, (double)0.75);
            if (nBTTagCompound != null && !(nBTTagCompound2 = nBTTagCompound.getCompoundTag("BlockEntityTag")).isEmpty() && nBTTagCompound2.getTagList("Items", 10) != null) {
                NonNullList nonNullList = NonNullList.withSize((int)27, (Object)ItemStack.EMPTY);
                ItemStackHelper.loadAllItems((NBTTagCompound)nBTTagCompound2, (NonNullList)nonNullList);
                for (int i = 0; i < nonNullList.size(); ++i) {
                    int n7 = n + i % 9 * 15 + 11;
                    int n8 = n2 + i / 9 * 15 - 11 + 10;
                    n7 = (int)((double)n7 / 0.75);
                    n8 = (int)((double)n8 / 0.75);
                    ItemStack itemStack2 = (ItemStack)nonNullList.get(i);
                    mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack2, n7, n8);
                    mc.getRenderItem().renderItemOverlayIntoGUI(Tooltips.mc.fontRenderer, itemStack2, n7, n8, null);
                }
            }
            GlStateManager.scale((double)-0.75, (double)-0.75, (double)-0.75);
            RenderHelper.disableStandardItemLighting();
            Tooltips.mc.getRenderItem().zLevel = 0.0f;
        } else {
            float f = Math.max(144.0f, Class557.Method800(itemStack.getDisplayName()) + 3.0f);
            int n9 = n + 12;
            int n10 = n2 - 12;
            int n11 = 48 + Class557.Method802();
            Tooltips.mc.getRenderItem().zLevel = 300.0f;
            Color color = (Boolean)customColor.getValue() != false ? new Color(((ColorValue)color.getValue()).Method774()) : new Color(((BlockShulkerBox)((ItemShulkerBox)itemStack.getItem()).getBlock()).getColor().getColorValue());
            Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
            GuiScreen.drawRect((int)(n9 - 3), (int)(n10 - 3), (int)((int)((float)n9 + f + 3.0f)), (int)(n10 + n11 + 3), (int)color2.getRGB());
            ItemStack itemStack3 = itemStack;
            String string = itemStack3.getDisplayName();
            float f2 = n + 12;
            float f3 = n2 - 12;
            int n12 = -1;
            try {
                Class557.Method798(string, f2, f3, n12);
            }
            catch (NullPointerException nullPointerException) {
                System.out.println("Error rendering font");
            }
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            if (nBTTagCompound != null) {
                NBTTagCompound nBTTagCompound3 = nBTTagCompound.getCompoundTag("BlockEntityTag");
                NonNullList nonNullList = NonNullList.withSize((int)27, (Object)ItemStack.EMPTY);
                ItemStackHelper.loadAllItems((NBTTagCompound)nBTTagCompound3, (NonNullList)nonNullList);
                for (int i = 0; i < nonNullList.size(); ++i) {
                    int n13 = n + i % 9 * 16 + 11;
                    int n14 = n2 + i / 9 * 16 - 11 + 8;
                    ItemStack itemStack4 = (ItemStack)nonNullList.get(i);
                    mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack4, n13, n14);
                    mc.getRenderItem().renderItemOverlayIntoGUI(Tooltips.mc.fontRenderer, itemStack4, n13, n14, null);
                }
            }
            RenderHelper.disableStandardItemLighting();
            Tooltips.mc.getRenderItem().zLevel = 0.0f;
        }
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }

    @Subscriber
    public void Method628(Class116 class116) {
        block0: {
            if (!((Boolean)shulkers.getValue()).booleanValue() || !(class116.Method2102().getItem() instanceof ItemShulkerBox)) break block0;
            this.Method627(class116.Method2102(), class116.Method2103(), class116.Method2101());
            class116.setCanceled(true);
        }
    }

    public void Method629(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        mc.getTextureManager().bindTexture(new ResourceLocation("konas/textures/container.png"));
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(d, d2 + d6, 69.0).tex((double)((float)d3 * 0.00683593f), (double)((float)(d4 + d6) * 0.015676616f)).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(d + d5, d2 + d6, 69.0).tex((double)((float)(d3 + d5) * 0.0068f), (double)((float)(d4 + d6) * 0.015676616f)).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(d + d5, d2 + 0.0, 69.0).tex((double)((float)(d3 + d5) * 0.0068f), (double)((float)d4 * 0.015676616f)).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(d, d2 + 0.0, 69.0).tex((double)((float)d3 * 0.00683593f), (double)((float)d4 * 0.015676616f)).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        tessellator.draw();
    }
}