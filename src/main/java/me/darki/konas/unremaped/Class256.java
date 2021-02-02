package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
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

public class Class256
extends Module {
    public GuiContainer Field2054;
    public boolean Field2055;
    public boolean Field2056 = false;

    @Subscriber
    public void Method536(Class24 class24) {
        if (class24.getPacket() instanceof CPacketCloseWindow) {
            if (this.Field2054 != null && this.Field2055) {
                class24.setCanceled(true);
            }
        } else if (class24.getPacket() instanceof CPacketClickWindow) {
            CPacketClickWindow cPacketClickWindow = (CPacketClickWindow)class24.getPacket();
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
        if (this.Field2055 && this.Field2056 && Class256.mc.currentScreen instanceof GuiContainer && !(Class256.mc.currentScreen instanceof GuiInventory)) {
            Class256.mc.currentScreen = null;
            this.Field2056 = false;
        }
    }

    @Subscriber
    public void Method791(Class17 class17) {
        if (class17.Method251() != null) {
            Block block = Class256.mc.world.getBlockState(class17.Method251()).getBlock();
            if (block == Blocks.ENDER_CHEST) {
                float f = (float)(class17.Method252().x - (double)class17.Method251().getX());
                float f2 = (float)(class17.Method252().y - (double)class17.Method251().getY());
                float f3 = (float)(class17.Method252().z - (double)class17.Method251().getZ());
                Class256.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(class17.Method251(), class17.Method250(), EnumHand.MAIN_HAND, f, f2, f3));
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
            if (this.Field2054 != null && ((ISPacketCloseWindow)sPacketCloseWindow).Method2195() == this.Field2054.inventorySlots.windowId) {
                this.Field2055 = false;
                this.Field2056 = false;
                this.Field2054 = null;
            }
        }
    }

    public Class256() {
        super("EnderBackpack", "Closes your enderchest GUI and allows you to open it whenever you want", Category.EXPLOIT, new String[0]);
    }

    @Override
    public void onDisable() {
        if (Class256.mc.world != null) {
            Class256.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(Class256.mc.player.inventoryContainer.windowId));
            this.Field2055 = false;
            this.Field2056 = false;
            this.Field2054 = null;
        }
    }

    @Subscriber
    public void Method1451(Class654 class654) {
        block1: {
            block0: {
                if (!(class654.Method1161() instanceof GuiContainer) || class654.Method1161() instanceof GuiInventory) break block0;
                this.Field2054 = (GuiContainer)class654.Method1161();
                break block1;
            }
            if (!(class654.Method1161() instanceof GuiInventory) || !this.Field2055 || this.Field2054 == null) break block1;
            this.Field2056 = false;
            class654.setCanceled(true);
            mc.displayGuiScreen((GuiScreen)this.Field2054);
        }
    }
}
