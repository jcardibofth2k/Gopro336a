package me.darki.konas.util;

import cookiedragon.eventsystem.Subscriber;
import java.io.File;

import me.darki.konas.unremaped.Class546;
import me.darki.konas.unremaped.Class589;
import me.darki.konas.event.events.OpenGuiEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;

public class ConfigUtil {
    public static ConfigUtil Field532 = new ConfigUtil();
    public static Minecraft Field533 = Minecraft.getMinecraft();

    public static void Method607(File file) {
        Class589.Method2219(Class589.Field2610);
        Class589.Method2274(file, false);
        System.out.println("Loaded Config for " + ConfigUtil.Field533.getCurrentServerData().serverIP);
    }

    @Subscriber
    public void Method608(OpenGuiEvent openGuiEvent) {
        block1: {
            if (!(openGuiEvent.Method1161() instanceof GuiConnecting) || Field533.getCurrentServerData() == null) break block1;
            Class546.Method964();
            File file = new File(Class589.Field2608, "@" + ConfigUtil.Field533.getCurrentServerData().serverIP + ".json");
            if (file.exists()) {
                new Thread(() -> ConfigUtil.Method607(file)).start();
            }
        }
    }
}
