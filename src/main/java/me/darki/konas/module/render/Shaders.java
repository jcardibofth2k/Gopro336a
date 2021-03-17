package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.Locale;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.settingEnums.SecretShaderMode;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Shaders
extends Module {
    public Setting<SecretShaderMode> shader = new Setting<>("Shader", SecretShaderMode.ANTIALIAS);

    public Shaders() {
        super("Shaders", "Enable 1.8 shaders", Category.RENDER);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block27: {
            block26: {
                StringBuilder stringBuilder;
                ResourceLocation resourceLocation;
                if (!OpenGlHelper.shadersSupported || !(mc.getRenderViewEntity() instanceof EntityPlayer)) break block26;
                if (Shaders.mc.entityRenderer.getShaderGroup() != null) {
                    Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
                EntityRenderer entityRenderer = Shaders.mc.entityRenderer;
                ResourceLocation resourceLocation2 = resourceLocation;
                ResourceLocation resourceLocation3 = resourceLocation;
                StringBuilder stringBuilder2 = stringBuilder;
                StringBuilder stringBuilder3 = stringBuilder;
                stringBuilder2();
                String string = "shaders/post/";
                StringBuilder stringBuilder4 = stringBuilder3.append(string);
                Setting<SecretShaderMode> setting = this.Field528;
                Object t = setting.getValue();
                SecretShaderMode secretShaderMode = (SecretShaderMode) t;
                String string2 = secretShaderMode.name();
                Locale locale = Locale.ROOT;
                String string3 = string2.toLowerCase(locale);
                StringBuilder stringBuilder5 = stringBuilder4.append(string3);
                String string4 = ".json";
                StringBuilder stringBuilder6 = stringBuilder5.append(string4);
                String string5 = stringBuilder6.toString();
                resourceLocation2(string5);
                try {
                    entityRenderer.loadShader(resourceLocation3);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break block27;
            }
            if (Shaders.mc.entityRenderer.getShaderGroup() == null || Shaders.mc.currentScreen != null) break block27;
            Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }

    @Override
    public void onDisable() {
        block0: {
            if (Shaders.mc.entityRenderer.getShaderGroup() == null) break block0;
            Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
}