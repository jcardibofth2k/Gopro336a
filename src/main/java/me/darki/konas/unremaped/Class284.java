package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.AutoReconnectScreen;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.event.world.WorldEvent;

public class Class284
extends Module {
    public ServerData Field1733;
    public Setting<Integer> Field1734 = new Setting<>("Delay", 5, 30, 1, 1);

    public void Method124() {
        ServerData serverData = mc.getCurrentServerData();
        if (serverData != null) {
            this.Field1733 = serverData;
        }
    }

    public Class284() {
        super("AutoReconnect", Category.MISC, new String[0]);
    }

    @Subscriber
    public void Method1656(WorldEvent.Unload unload) {
        this.Method124();
    }

    @Subscriber
    public void Method1451(Class654 class654) {
        block0: {
            if (!(class654.Method1161() instanceof GuiDisconnected)) break block0;
            this.Method124();
            class654.Method1160(new AutoReconnectScreen((GuiDisconnected)class654.Method1161(), this.Field1733, (Integer)this.Field1734.getValue()));
        }
    }
}
