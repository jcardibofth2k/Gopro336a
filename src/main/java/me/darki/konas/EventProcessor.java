package me.darki.konas;

import cookiedragon.eventsystem.EventDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventProcessor {
    public static EventProcessor INSTANCE = new EventProcessor();

    @SubscribeEvent
    public void Method1770(WorldEvent.Unload unload) {
        EventDispatcher.Companion.dispatch(unload);
    }

    @SubscribeEvent
    public void Method1771(PlayerSPPushOutOfBlocksEvent playerSPPushOutOfBlocksEvent) {
        Class572 class572 = Class572.Method598(playerSPPushOutOfBlocksEvent.getEntityPlayer());
        EventDispatcher.Companion.dispatch(class572);
        playerSPPushOutOfBlocksEvent.setCanceled(class572.isCanceled());
    }

    @SubscribeEvent
    public void Method1772(EntityViewRenderEvent.FOVModifier fOVModifier) {
        EventDispatcher.Companion.dispatch(fOVModifier);
    }

    @SubscribeEvent
    public void Method1773(PlayerEvent.ItemCraftedEvent itemCraftedEvent) {
        EventDispatcher.Companion.dispatch(itemCraftedEvent);
    }

    @SubscribeEvent
    public void Method1774(InputUpdateEvent inputUpdateEvent) {
        Class2 class2 = new Class2(inputUpdateEvent.getEntityPlayer(), inputUpdateEvent.getMovementInput());
        EventDispatcher.Companion.dispatch(class2);
        if (inputUpdateEvent.getEntityPlayer() == Minecraft.getMinecraft().player) {
            Class27 class27 = new Class27(class2.Method81());
            EventDispatcher.Companion.dispatch(class27);
        }
    }

    @SubscribeEvent
    public void Method1775(ItemTooltipEvent itemTooltipEvent) {
        EventDispatcher.Companion.dispatch(itemTooltipEvent);
    }

    @SubscribeEvent
    public void Method1776(ClientChatReceivedEvent clientChatReceivedEvent) {
        EventDispatcher.Companion.dispatch(clientChatReceivedEvent);
    }

    @SubscribeEvent
    public void Method1777(EntityViewRenderEvent.CameraSetup cameraSetup) {
        EventDispatcher.Companion.dispatch(cameraSetup);
    }

    @SubscribeEvent
    public void Method1778(ClientChatEvent clientChatEvent) {
        EventDispatcher.Companion.dispatch(clientChatEvent);
    }

    @SubscribeEvent
    public void Method1779(LivingEvent.LivingJumpEvent livingJumpEvent) {
        EventDispatcher.Companion.dispatch(livingJumpEvent);
    }

    @SubscribeEvent
    public void Method1780(WorldEvent worldEvent) {
        EventDispatcher.Companion.dispatch(worldEvent);
    }

    @SubscribeEvent
    public void Method1781(RenderGameOverlayEvent.Post post) {
        if (post.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            EventDispatcher.Companion.dispatch(new Class91());
        }
        EventDispatcher.Companion.dispatch(post);
    }

    @SubscribeEvent
    public void Method1782(InputUpdateEvent inputUpdateEvent) {
        EventDispatcher.Companion.dispatch(inputUpdateEvent);
    }

    @SubscribeEvent
    public void Method1783(PlayerEvent.ItemPickupEvent itemPickupEvent) {
        EventDispatcher.Companion.dispatch(itemPickupEvent);
    }

    @SubscribeEvent
    public void Method1784(EntityViewRenderEvent.FogDensity fogDensity) {
        block0: {
            Class113 class113 = new Class113(fogDensity.getRenderer(), fogDensity.getEntity(), fogDensity.getState(), fogDensity.getRenderPartialTicks());
            EventDispatcher.Companion.dispatch(class113);
            if (!class113.isCanceled()) break block0;
            fogDensity.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void Method1785(GuiScreenEvent.DrawScreenEvent.Post post) {
        Class44 class44 = Class44.Method271(post.getGui());
        EventDispatcher.Companion.dispatch(class44);
    }

    @SubscribeEvent
    public void Method1786(LivingEntityUseItemEvent livingEntityUseItemEvent) {
        if (livingEntityUseItemEvent.getEntity() instanceof EntityPlayerSP) {
            EventDispatcher.Companion.dispatch(new Class25());
        }
    }

    @SubscribeEvent
    public void Method1787(RenderWorldLastEvent renderWorldLastEvent) {
        if (renderWorldLastEvent.isCanceled()) {
            return;
        }
        Minecraft.getMinecraft().profiler.startSection("konas");
        Class86 class86 = new Class86(renderWorldLastEvent.getPartialTicks());
        EventDispatcher.Companion.dispatch(class86);
        Minecraft.getMinecraft().profiler.endSection();
    }

    @SubscribeEvent
    public void Method1788(GuiOpenEvent guiOpenEvent) {
        Class654 class654 = new Class654(guiOpenEvent.getGui());
        EventDispatcher.Companion.dispatch(class654);
        guiOpenEvent.setGui(class654.Method1161());
        guiOpenEvent.setCanceled(class654.isCanceled());
    }

    @SubscribeEvent
    public void Method1789(net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent clientTickEvent) {
        MathUtil.Field1067 = null;
        TickEvent tickEvent = TickEvent.Method325(clientTickEvent.phase);
        EventDispatcher.Companion.dispatch(tickEvent);
    }

    @SubscribeEvent
    public void Method1790(PlayerInteractEvent.RightClickItem rightClickItem) {
        EventDispatcher.Companion.dispatch(rightClickItem);
    }

    @SubscribeEvent
    public void Method1791(RenderBlockOverlayEvent renderBlockOverlayEvent) {
        Class148 class148 = new Class148(renderBlockOverlayEvent.getOverlayType());
        EventDispatcher.Companion.dispatch(class148);
        renderBlockOverlayEvent.setCanceled(class148.isCanceled());
    }

    @SubscribeEvent
    public void Method1792(EntityViewRenderEvent.FogColors fogColors) {
        Class58 class58 = new Class58(fogColors.getRed(), fogColors.getGreen(), fogColors.getBlue());
        EventDispatcher.Companion.dispatch(class58);
        fogColors.setRed(class58.Method213());
        fogColors.setGreen(class58.Method215());
        fogColors.setBlue(class58.Method214());
    }

    @SubscribeEvent
    public void Method1793(EntityViewRenderEvent.FogDensity fogDensity) {
        Class64 class64 = new Class64(fogDensity.getDensity());
        EventDispatcher.Companion.dispatch(class64);
        fogDensity.setDensity(class64.Method214());
        fogDensity.setCanceled(class64.isCanceled());
    }

    @SubscribeEvent
    public void Method1794(AttackEntityEvent attackEntityEvent) {
        Class30 class30 = Class30.Method291(attackEntityEvent.getEntityPlayer(), attackEntityEvent.getTarget());
        EventDispatcher.Companion.dispatch(class30);
    }

    @SubscribeEvent
    public void Method1795(RenderTooltipEvent.PostBackground postBackground) {
        Class136 class136 = new Class136(postBackground.getStack(), postBackground.getX(), postBackground.getY());
        EventDispatcher.Companion.dispatch(class136);
    }
}
