package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.ISPacketCloseWindow;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.util.EnumHand;

public class EnderBackpack
extends Module {
    public GuiContainer Field2054;
    public boolean Field2055;
    public boolean Field2056 = false;

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        if (sendPacketEvent.getPacket() instanceof CPacketCloseWindow) {
            if (this.Field2054 != null && this.Field2055) {
                sendPacketEvent.setCanceled(true);
            }
        } else if (sendPacketEvent.getPacket() instanceof CPacketClickWindow) {
            CPacketClickWindow cPacketClickWindow = (CPacketClickWindow) sendPacketEvent.getPacket();
            if (cPacketClickWindow.getClickType().equals((Object)ClickType.THROW) && this.Field2055 && this.Field2054 != null) {
                this.Field2056 = true;
            } else if (cPacketClickWindow.getClickType().equals((Object)ClickType.THROW) && !this.Field2055) {
                this.Field2056 = false;
                this.Field2054 = null;
            }
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (this.Field2055 && this.Field2056 && EnderBackpack.mc.currentScreen instanceof GuiContainer && !(EnderBackpack.mc.currentScreen instanceof GuiInventory)) {
            EnderBackpack.mc.currentScreen = null;
            this.Field2056 = false;
        }
    }

    @Subscriber
    public void Method791(rightClickBlockEvent rightClickBlockEvent) {
        if (rightClickBlockEvent.Method251() != null) {
            Block block = EnderBackpack.mc.world.getBlockState(rightClickBlockEvent.Method251()).getBlock();
            if (block == Blocks.ENDER_CHEST) {
                float f = (float)(rightClickBlockEvent.Method252().x - (double) rightClickBlockEvent.Method251().getX());
                float f2 = (float)(rightClickBlockEvent.Method252().y - (double) rightClickBlockEvent.Method251().getY());
                float f3 = (float)(rightClickBlockEvent.Method252().z - (double) rightClickBlockEvent.Method251().getZ());
                EnderBackpack.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(rightClickBlockEvent.Method251(), rightClickBlockEvent.Method250(), EnumHand.MAIN_HAND, f, f2, f3));
                this.Field2055 = true;
            } else if (block instanceof BlockContainer) {
                this.Field2055 = false;
                this.Field2056 = false;
                this.Field2054 = null;
            }
        }
    }

    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketCloseWindow) {
            SPacketCloseWindow sPacketCloseWindow = (SPacketCloseWindow) packetEvent.getPacket();
            if (this.Field2054 != null && ((ISPacketCloseWindow)sPacketCloseWindow).getWindowId() == this.Field2054.inventorySlots.windowId) {
                this.Field2055 = false;
                this.Field2056 = false;
                this.Field2054 = null;
            }
        }
    }

    public EnderBackpack() {
        super("EnderBackpack", "Closes your enderchest GUI and allows you to open it whenever you want", Category.EXPLOIT, new String[0]);
    }

    @Override
    public void onDisable() {
        if (EnderBackpack.mc.world != null) {
            EnderBackpack.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(EnderBackpack.mc.player.inventoryContainer.windowId));
            this.Field2055 = false;
            this.Field2056 = false;
            this.Field2054 = null;
        }
    }

    @Subscriber
    public void Method1451(OpenGuiEvent openGuiEvent) {
        block1: {
            block0: {
                if (!(openGuiEvent.Method1161() instanceof GuiContainer) || openGuiEvent.Method1161() instanceof GuiInventory) break block0;
                this.Field2054 = (GuiContainer) openGuiEvent.Method1161();
                break block1;
            }
            if (!(openGuiEvent.Method1161() instanceof GuiInventory) || !this.Field2055 || this.Field2054 == null) break block1;
            this.Field2056 = false;
            openGuiEvent.setCanceled(true);
            mc.displayGuiScreen((GuiScreen)this.Field2054);
        }
    }
}