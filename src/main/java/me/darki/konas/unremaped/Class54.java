package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.MathUtil;
import me.darki.konas.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

public class Class54 {
    public static Class54 Field200 = new Class54();
    public static boolean Field201 = false;

    @Subscriber
    public void Method328(Class91 class91) {
        if (Minecraft.getMinecraft().currentScreen != null && (Minecraft.getMinecraft().currentScreen instanceof Class193 || Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
            return;
        }
        for (Module module : Class167.Method1619()) {
            if (!module.Method1630()) continue;
            if (MathUtil.Method1087(module.Method1646()) && !module.isEnabled()) {
                module.toggle();
                continue;
            }
            if (MathUtil.Method1087(module.Method1646()) || !module.isEnabled()) continue;
            module.toggle();
        }
    }

    @Subscriber
    public void Method329(Class653 class653) {
        if (class653.Method1164() == 0) {
            return;
        }
        if (Keyboard.isKeyDown((int)61)) {
            return;
        }
        for (Module object : Class167.Method1619()) {
            if (object.Method1630() || object.Method1646() != class653.Method1164()) continue;
            object.toggle();
        }
        if (Class167.Method1610(Class191.class).isEnabled()) {
            for (Class152 class152 : Class157.Method1704()) {
                if (class152.Method1757() != class653.Method1164()) continue;
                Field201 = true;
                class152.Method1759();
                Field201 = false;
            }
        }
    }
}
