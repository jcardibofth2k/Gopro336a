package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class28;
import me.darki.konas.unremaped.Class566;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class SkinBlinker
extends Module {
    public static Setting<Float> delay = new Setting<>("Delay", Float.valueOf(0.0f), Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public static Setting<Boolean> random = new Setting<>("Random", true);
    public Class566 Field2211 = new Class566();

    @Subscriber
    public void Method390(Class28 class28) {
        block1: {
            if (!this.Field2211.Method737(delay.getValue().floatValue() * 1000.0f)) break block1;
            EnumPlayerModelParts[] enumPlayerModelPartsArray = EnumPlayerModelParts.values();
            for (int i = 0; i < enumPlayerModelPartsArray.length; ++i) {
                EnumPlayerModelParts enumPlayerModelParts = enumPlayerModelPartsArray[i];
                SkinBlinker.mc.gameSettings.setModelPartEnabled(enumPlayerModelParts, random.getValue().booleanValue() ? Math.random() < 0.5 : !SkinBlinker.mc.gameSettings.getModelParts().contains(enumPlayerModelParts));
            }
            this.Field2211.Method739();
        }
    }

    public SkinBlinker() {
        super("SkinBlinker", "Flashes your skin parts", Category.PLAYER);
    }
}