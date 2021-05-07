package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class Class497
extends Module {
    public float Field2060 = 0.0f;
    public float Field2061 = 0.0f;
    public static Setting<Boolean> autoThirdPerson = new Setting<>("AutoThirdPerson", true);

    @Subscriber
    public void Method1923(Class37 class37) {
        block0: {
            if (Class497.mc.gameSettings.thirdPersonView <= 0) break block0;
            this.Field2060 = (float)((double)this.Field2060 + (double)class37.Method215() * 0.15);
            this.Field2061 = (float)((double)this.Field2061 - (double)class37.Method214() * 0.15);
            this.Field2061 = MathHelper.clamp((float)this.Field2061, (float)-90.0f, (float)90.0f);
            class37.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1924(EntityViewRenderEvent.CameraSetup cameraSetup) {
        block0: {
            if (Class497.mc.gameSettings.thirdPersonView <= 0) break block0;
            cameraSetup.setYaw(cameraSetup.getYaw() + this.Field2060);
            cameraSetup.setPitch(cameraSetup.getPitch() + this.Field2061);
        }
    }

    public Class497() {
        super("FreeLook", "Look around in 3rd person", Category.RENDER, new String[0]);
    }

    @Override
    public void onDisable() {
        if (((Boolean)autoThirdPerson.getValue()).booleanValue()) {
            Class497.mc.gameSettings.thirdPersonView = 0;
        }
    }

    @Override
    public void onEnable() {
        this.Field2060 = 0.0f;
        this.Field2061 = 0.0f;
        if (((Boolean)autoThirdPerson.getValue()).booleanValue()) {
            Class497.mc.gameSettings.thirdPersonView = 1;
        }
    }
}