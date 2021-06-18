package me.darki.konas.unremaped;

import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import java.lang.reflect.Field;
import java.util.List;

import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.gui.hud.elements.*;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.SPacketChat;

public class Class109 {
    public java.util.ArrayList<Element> Field2569 = new java.util.ArrayList();
    public static TimerUtil Field2570 = new TimerUtil();

    public java.util.ArrayList<Element> Method2196() {
        return this.Field2569;
    }

    public static int Method2197(Element element, Element element2) {
        return element.Method2316().compareToIgnoreCase(element2.Method2316());
    }

    /*
     * Unable to fully structure code
     */
    public static Setting[] Method2198(Element var0) {
        Object var10000 = null;
        Element var1 = (Element)var0.getClass().getSuperclass().cast(var0);
        List<Setting> var2 = new java.util.ArrayList<>();
        Field[] var3 = var1.getClass().getDeclaredFields();
        int var4 = var3.length;

        int var5;
        Field var6;
        for(var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            if (Setting.class.isAssignableFrom(var6.getType())) {
                var6.setAccessible(true);

                try {
                    if (ListenableSettingDecorator.class.isAssignableFrom(var6.getType())) {
                        var2.add((ListenableSettingDecorator)var6.get(var1));
                    } else {
                        var2.add((Setting)var6.get(var1));
                    }
                } catch (IllegalAccessException var9) {
                    var9.printStackTrace();
                }
            }
        }

        var3 = var1.getClass().getSuperclass().getDeclaredFields();
        var4 = var3.length;

        for(var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            if (Setting.class.isAssignableFrom(var6.getType())) {
                var6.setAccessible(true);

                try {
                    var2.add((Setting)var6.get(var1));
                } catch (IllegalAccessException var8) {
                    var8.printStackTrace();
                }
            }
        }

        return (Setting[])var2.toArray();
    }

    @Subscriber
    public void Method2199(PacketEvent packetEvent) {
        block0: {
            if (packetEvent.getPacket() instanceof SPacketChat) break block0;
            Field2570.UpdateCurrentTime();
        }
    }

    public void Method2200() {
        EventDispatcher.Companion.register(this);
        EventDispatcher.Companion.subscribe(this);
        this.Field2569.add(new ArrayList());
        this.Field2569.add(new PlayerViewer());
        this.Field2569.add(new Class115());
        this.Field2569.add(new Inventory());
        this.Field2569.add(new Class141());
        this.Field2569.add(new Coords());
        this.Field2569.add(new TPS());
        this.Field2569.add(new SpeedElement());
        this.Field2569.add(new Class143());
        this.Field2569.add(new Class123());
        this.Field2569.add(new Class140());
        this.Field2569.add(new MobRadar());
        this.Field2569.add(new Radar());
        this.Field2569.add(new Class138());
        this.Field2569.add(new ChestCount());
        this.Field2569.add(new Class146());
        this.Field2569.add(new Clock());
        this.Field2569.add(new ItemGrid());
        this.Field2569.add(new Class112());
        this.Field2569.add(new LagNotifier());
        this.Field2569.add(new Crystals());
        this.Field2569.add(new Class150());
        this.Field2569.add(new Gapples());
        this.Field2569.add(new Totems());
        this.Field2569.sort(Class109::Method2197);
    }

    @Subscriber
    public void Method2201(Class91 class91) {
        Element.Field2659 = new ScaledResolution(Minecraft.getMinecraft());
        for (Element element : this.Method2196()) {
            if (!element.Method2338()) continue;
            element.onRender2D();
        }
    }

    @Subscriber
    public void Method2202(Class653 class653) {
        for (Element element : this.Method2196()) {
            if (!element.Method2338()) continue;
            element.Method2158(class653.Method1164());
        }
    }
}
