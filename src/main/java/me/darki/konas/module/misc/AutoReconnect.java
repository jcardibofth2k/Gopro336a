package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.gui.screen.AutoReconnectScreen;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.event.world.WorldEvent;

public class AutoReconnect
extends Module {
    public ServerData Field1733;
    public Setting<Integer> delay = new Setting<>("Delay", 5, 30, 1, 1);

    public void Method124() {
        ServerData serverData = mc.getCurrentServerData();
        if (serverData != null) {
            this.Field1733 = serverData;
        }
    }

    public AutoReconnect() {
        super("AutoReconnect", Category.MISC, new String[0]);
    }

    @Subscriber
    public void Method1656(WorldEvent.Unload unload) {
        this.Method124();
    }

    @Subscriber
    public void Method1451(OpenGuiEvent openGuiEvent) {
        block0: {
            if (!(openGuiEvent.Method1161() instanceof GuiDisconnected)) break block0;
            this.Method124();
            openGuiEvent.Method1160(new AutoReconnectScreen((GuiDisconnected) openGuiEvent.Method1161(), this.Field1733, (Integer)this.delay.getValue()));
        }
    }
}