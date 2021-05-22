package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import me.darki.konas.module.Category;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class147;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class262;
import me.darki.konas.unremaped.Class356;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.Class644;
import me.darki.konas.unremaped.Class658;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import org.lwjgl.input.Keyboard;

public class GuiMove
extends Module {
    public Setting<Boolean> strict = new Setting<>("Strict", false);
    public ListenableSettingDecorator<Boolean> Field2006 = new ListenableSettingDecorator("Crouch", false, new Class356(this));
    public ArrayList<KeyBinding> Field2007 = new ArrayList();

    public static ArrayList Method1835(GuiMove guiMove) {
        return guiMove.Field2007;
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block3: {
            if (!this.strict.getValue().booleanValue() || !(sendPacketEvent.getPacket() instanceof CPacketClickWindow)) break block3;
            if (GuiMove.mc.player.isActiveItemStackBlocking()) {
                GuiMove.mc.playerController.onStoppedUsingItem(GuiMove.mc.player);
            }
            if (GuiMove.mc.player.isSneaking()) {
                GuiMove.mc.player.connection.sendPacket(new CPacketEntityAction(GuiMove.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (GuiMove.mc.player.isSprinting()) {
                GuiMove.mc.player.connection.sendPacket(new CPacketEntityAction(GuiMove.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
        }
    }

    @Subscriber
    public void Method1836(Class658 class658) {
        if (GuiMove.mc.world == null || GuiMove.mc.player == null) {
            return;
        }
        if (!(GuiMove.mc.currentScreen instanceof GuiChat) && GuiMove.mc.currentScreen != null) {
            class658.Field1098 = class658.Field1099;
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (GuiMove.mc.world == null || GuiMove.mc.player == null) {
            return;
        }
        if (GuiMove.mc.currentScreen instanceof GuiOptions || GuiMove.mc.currentScreen instanceof GuiVideoSettings || GuiMove.mc.currentScreen instanceof GuiScreenOptionsSounds || GuiMove.mc.currentScreen instanceof GuiContainer || GuiMove.mc.currentScreen instanceof GuiIngameMenu || GuiMove.mc.currentScreen instanceof Class147 || GuiMove.mc.currentScreen instanceof GuiScreenAdvancements || GuiMove.mc.currentScreen instanceof Class193 || GuiMove.mc.currentScreen instanceof Class262) {
            for (KeyBinding keyBinding : this.Field2007) {
                KeyBinding.setKeyBindState(keyBinding.getKeyCode(), GameSettings.isKeyDown(keyBinding));
            }
            if (Keyboard.isKeyDown(203)) {
                GuiMove.mc.player.rotationYaw -= 5.0f;
            }
            if (Keyboard.isKeyDown(200) && GuiMove.mc.player.rotationPitch > -84.0f) {
                GuiMove.mc.player.rotationPitch -= 5.0f;
            }
            if (Keyboard.isKeyDown(208) && GuiMove.mc.player.rotationPitch < 84.0f) {
                GuiMove.mc.player.rotationPitch += 5.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                GuiMove.mc.player.rotationYaw += 5.0f;
            }
        }
    }

    @Subscriber
    public void Method1837(Class644 class644) {
        block2: {
            if (!this.strict.getValue().booleanValue()) {
                return;
            }
            if (GuiMove.mc.player.isSneaking()) {
                GuiMove.mc.player.connection.sendPacket(new CPacketEntityAction(GuiMove.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            if (!GuiMove.mc.player.isSprinting()) break block2;
            GuiMove.mc.player.connection.sendPacket(new CPacketEntityAction(GuiMove.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }

    public GuiMove() {
        super("GUIMove", "Lets you move around in GUIs", Category.PLAYER);
        this.Field2007.add(GuiMove.mc.gameSettings.keyBindForward);
        this.Field2007.add(GuiMove.mc.gameSettings.keyBindBack);
        this.Field2007.add(GuiMove.mc.gameSettings.keyBindRight);
        this.Field2007.add(GuiMove.mc.gameSettings.keyBindLeft);
        this.Field2007.add(GuiMove.mc.gameSettings.keyBindJump);
        if (((Boolean)this.Field2006.getValue()).booleanValue()) {
            this.Field2007.add(GuiMove.mc.gameSettings.keyBindSneak);
        }
    }
}