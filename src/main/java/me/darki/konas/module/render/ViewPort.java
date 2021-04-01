package me.darki.konas.module.render;

import me.darki.konas.module.Category;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import org.lwjgl.util.glu.Project;

public class ViewPort
extends Module {
    public static Setting<Boolean> hands = new Setting<>("Hands", true);
    public static Setting<Boolean> fov = new Setting<>("FOV", true);
    public static Setting<Integer> angle = new Setting<>("Angle", 90, 180, 10, 5).visibleIf(fov::getValue);
    public static Setting<Boolean> aspect = new Setting<>("Aspect", true);
    public static Setting<Float> ratio = new Setting<>("Ratio", Float.valueOf(1.77f), Float.valueOf(2.5f), Float.valueOf(0.75f), Float.valueOf(0.1f)).visibleIf(aspect::getValue);
    public static ViewPort Field501;

    public static void Method569(float f, float f2, float f3, float f4) {
        ViewPort.Method570(f, f2, f3, f4, false);
    }

    public ViewPort() {
        super("Viewport", "Modify your viewport", Category.RENDER);
        Field501 = this;
    }

    @Override
    public void onEnable() {
        if (ViewPort.mc.player != null && ViewPort.mc.player.getName().equalsIgnoreCase("johnmcswag")) {
            this.toggle();
            Logger.Method1119("Johnmcswag can not use this module!");
            for (Module module : ModuleManager.getEnabledModules()) {
                module.toggle();
            }
        }
    }

    public static void Method570(float f, float f2, float f3, float f4, boolean bl) {
        if (Field501.isEnabled() && (!bl || hands.getValue().booleanValue())) {
            Project.gluPerspective((float)(fov.getValue() != false ? (float) angle.getValue().intValue() : f), (float)(aspect.getValue() != false ? ratio.getValue().floatValue() : f2), (float)f3, (float)f4);
        } else {
            Project.gluPerspective((float)f, (float)f2, (float)f3, (float)f4);
        }
    }
}