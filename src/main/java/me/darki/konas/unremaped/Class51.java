package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.util.LinkedList;
import java.util.Queue;

import me.darki.konas.config.Config;
import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.event.world.WorldEvent;

public class Class51 {
    public static Class51 Field221 = new Class51();
    public static Queue<Class301> Field222 = new LinkedList<Class301>();
    public static TimerUtil Field223 = new TimerUtil();
    public Minecraft Field224 = Minecraft.getMinecraft();

    @Subscriber
    public void Method349(WorldEvent.Unload unload) {
        Field222.clear();
    }

    @Subscriber
    public void Method350(TickEvent tickEvent) {
        block1: {
            Class301 class301;
            if (this.Field224.player == null || this.Field224.world == null) {
                return;
            }
            if (!Field223.GetDifferenceTiming(((Float) ExtraChat.delay.getValue()).floatValue() * 1000.0f) || Field222.isEmpty() || this.Field224.player.connection.getPlayerInfo((class301 = Field222.poll()).Method1072()) == null) break block1;
            this.Field224.player.connection.sendPacket((Packet)new CPacketChatMessage("/" + (ExtraChat.mode.getValue() == Class295.MSG ? "msg" : "w") + " " + class301.Method1072() + " " + class301.Method1073()));
            Field223.UpdateCurrentTime();
        }
    }

    @Subscriber
    public void Method351(Class645 class645) {
        Field222.add(new Class301(class645.Method1210(), class645.Method1211()));
    }

    @Subscriber
    public void Method352(OpenGuiEvent openGuiEvent) {
        block0: {
            if (!(openGuiEvent.Method1161() instanceof GuiIngameMenu) && !(openGuiEvent.Method1161() instanceof GuiDisconnected)) break block0;
            Config.Method2219(Config.currentConfig);
        }
    }
}