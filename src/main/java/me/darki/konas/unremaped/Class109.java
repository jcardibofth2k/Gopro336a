package me.darki.konas.unremaped;

import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import java.lang.reflect.Field;

import me.darki.konas.*;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.gui.hud.elements.*;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.SPacketChat;

public class Class109 {
    public java.util.ArrayList<Element> Field2569 = new java.util.ArrayList();
    public static Class566 Field2570 = new Class566();

    public java.util.ArrayList<Element> Method2196() {
        return this.Field2569;
    }

    public static int Method2197(Element element, Element element2) {
        return element.Method2316().compareToIgnoreCase(element2.Method2316());
    }

    /*
     * Unable to fully structure code
     */
    public static java.util.ArrayList<Setting> Method2198(Element var0) {
        var1_1 = (Element)var0.getClass().getSuperclass().cast(var0);
        var2_2 = new java.util.ArrayList<Setting>();
        for (Field var6_6 : var1_1.getClass().getDeclaredFields()) {
            if (!Setting.class.isAssignableFrom(var6_6.getType())) continue;
            var6_6.setAccessible(true);
            v0 = IdkWhatThisSettingThingDoes.class;
            v1 = var6_6;
            v2 = v1.getType();
            v3 = v0.isAssignableFrom(v2);
            if (!v3) ** GOTO lbl28
            v4 = var2_2;
            v5 = var6_6;
            v6 = var1_1;
            v7 = v5.get(v6);
            v8 = (IdkWhatThisSettingThingDoes)v7;
            v4.add(v8);
            continue;
lbl28:
            // 1 sources

            v9 = var2_2;
            v10 = var6_6;
            v11 = var1_1;
            v12 = v10.get(v11);
            v13 = (Setting)v12;
            v9.add(v13);
            try {
            }
            catch (IllegalAccessException var7_7) {
                var7_7.printStackTrace();
            }
        }
        for (Field var6_6 : var1_1.getClass().getSuperclass().getDeclaredFields()) {
            if (!Setting.class.isAssignableFrom(var6_6.getType())) continue;
            var6_6.setAccessible(true);
            v14 = var2_2;
            v15 = var6_6;
            v16 = var1_1;
            v17 = v15.get(v16);
            v18 = (Setting)v17;
            v14.add(v18);
            try {
            }
            catch (IllegalAccessException var7_9) {
                var7_9.printStackTrace();
            }
        }
        return var2_2;
    }

    @Subscriber
    public void Method2199(PacketEvent packetEvent) {
        block0: {
            if (packetEvent.getPacket() instanceof SPacketChat) break block0;
            Field2570.Method739();
        }
    }

    public void Method2200() {
        EventDispatcher.Companion.register(this);
        EventDispatcher.Companion.subscribe(this);
        this.Field2569.add(new ArrayList());
        this.Field2569.add(new PlayerViewer());
        this.Field2569.add(new Class115());
        this.Field2569.add(new Class111());
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
        this.Field2569.add(new Class120());
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
