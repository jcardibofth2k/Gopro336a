package me.darki.konas.event;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.event.events.*;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.unremaped.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
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
        PushOutOfBlocksEvent pushOutOfBlocksEvent = PushOutOfBlocksEvent.Method598(playerSPPushOutOfBlocksEvent.getEntityPlayer());
        EventDispatcher.Companion.dispatch(pushOutOfBlocksEvent);
        playerSPPushOutOfBlocksEvent.setCanceled(pushOutOfBlocksEvent.isCanceled());
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
    public void Method1774(net.minecraftforge.client.event.InputUpdateEvent inputUpdateEvent) {
        InputUpdateEvent class2 = new InputUpdateEvent(inputUpdateEvent.getEntityPlayer(), inputUpdateEvent.getMovementInput());
        EventDispatcher.Companion.dispatch(class2);
        if (inputUpdateEvent.getEntityPlayer() == Minecraft.getMinecraft().player) {
            PlayerInputUpdateEvent playerInputUpdateEvent = new PlayerInputUpdateEvent(class2.Method81());
            EventDispatcher.Companion.dispatch(playerInputUpdateEvent);
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
    public void Method1782(net.minecraftforge.client.event.InputUpdateEvent inputUpdateEvent) {
        EventDispatcher.Companion.dispatch(inputUpdateEvent);
    }

    @SubscribeEvent
    public void Method1783(PlayerEvent.ItemPickupEvent itemPickupEvent) {
        EventDispatcher.Companion.dispatch(itemPickupEvent);
    }

    @SubscribeEvent
    public void Method1784(EntityViewRenderEvent.FogDensity fogDensity) {
        block0: {
            EventIdkPlsRename eventIdkPlsRename = new EventIdkPlsRename(fogDensity.getRenderer(), fogDensity.getEntity(), fogDensity.getState(), fogDensity.getRenderPartialTicks());
            EventDispatcher.Companion.dispatch(eventIdkPlsRename);
            if (!eventIdkPlsRename.isCanceled()) break block0;
            fogDensity.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void Method1785(GuiScreenEvent.DrawScreenEvent.Post post) {
        DrawScreenEvent drawScreenEvent = DrawScreenEvent.Method271(post.getGui());
        EventDispatcher.Companion.dispatch(drawScreenEvent);
    }

    @SubscribeEvent
    public void Method1786(LivingEntityUseItemEvent livingEntityUseItemEvent) {
        if (livingEntityUseItemEvent.getEntity() instanceof EntityPlayerSP) {
            EventDispatcher.Companion.dispatch(new EntityUseItemEvent());
        }
    }

    @SubscribeEvent
    public void Method1787(net.minecraftforge.client.event.RenderWorldLastEvent renderWorldLastEvent) {
        if (renderWorldLastEvent.isCanceled()) {
            return;
        }
        Minecraft.getMinecraft().profiler.startSection("konas");
        RenderWorldLastEvent renderWorldLast = new RenderWorldLastEvent(renderWorldLastEvent.getPartialTicks());
        EventDispatcher.Companion.dispatch(renderWorldLast);
        Minecraft.getMinecraft().profiler.endSection();
    }

    @SubscribeEvent
    public void Method1788(GuiOpenEvent guiOpenEvent) {
        OpenGuiEvent openGuiEvent = new OpenGuiEvent(guiOpenEvent.getGui());
        EventDispatcher.Companion.dispatch(openGuiEvent);
        guiOpenEvent.setGui(openGuiEvent.Method1161());
        guiOpenEvent.setCanceled(openGuiEvent.isCanceled());
    }

    @SubscribeEvent
    public void Method1789(net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent clientTickEvent) {
        PlayerUtil.Field1067 = null;
        TickEvent tickEvent = TickEvent.Method325(clientTickEvent.phase);
        EventDispatcher.Companion.dispatch(tickEvent);
    }

    @SubscribeEvent
    public void Method1790(PlayerInteractEvent.RightClickItem rightClickItem) {
        EventDispatcher.Companion.dispatch(rightClickItem);
    }

    @SubscribeEvent
    public void Method1791(net.minecraftforge.client.event.RenderBlockOverlayEvent renderBlockOverlayEvent) {
        RenderBlockOverlayEvent class148 = new RenderBlockOverlayEvent(renderBlockOverlayEvent.getOverlayType());
        EventDispatcher.Companion.dispatch(class148);
        renderBlockOverlayEvent.setCanceled(class148.isCanceled());
    }

    @SubscribeEvent
    public void Method1792(EntityViewRenderEvent.FogColors fogColors) {
        FogColorsEvent fogColorsEvent = new FogColorsEvent(fogColors.getRed(), fogColors.getGreen(), fogColors.getBlue());
        EventDispatcher.Companion.dispatch(fogColorsEvent);
        fogColors.setRed(fogColorsEvent.Method213());
        fogColors.setGreen(fogColorsEvent.Method215());
        fogColors.setBlue(fogColorsEvent.Method214());
    }

    @SubscribeEvent
    public void Method1793(EntityViewRenderEvent.FogDensity fogDensity) {
        StrangeClass64 class64 = new StrangeClass64(fogDensity.getDensity());
        EventDispatcher.Companion.dispatch(class64);
        fogDensity.setDensity(class64.Method214());
        fogDensity.setCanceled(class64.isCanceled());
    }

    @SubscribeEvent
    public void Method1794(net.minecraftforge.event.entity.player.AttackEntityEvent attackEntityEvent) {
        AttackEntityEvent class30 = AttackEntityEvent.Method291(attackEntityEvent.getEntityPlayer(), attackEntityEvent.getTarget());
        EventDispatcher.Companion.dispatch(class30);
    }

    @SubscribeEvent
    public void Method1795(RenderTooltipEvent.PostBackground postBackground) {
        Class136 class136 = new Class136(postBackground.getStack(), postBackground.getX(), postBackground.getY());
        EventDispatcher.Companion.dispatch(class136);
    }
}
