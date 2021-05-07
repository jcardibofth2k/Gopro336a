package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import java.util.HashMap;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

public class Class489
extends Module {
    public HashMap<String, ResourceLocation> Field2267 = new HashMap();
    public HashMap<String, String> Field2268 = new HashMap();

    public void Method714(String string) {
        DynamicTexture dynamicTexture = Class509.Method1353(string);
        ResourceLocation resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(string, dynamicTexture);
        this.Field2267.put(string, resourceLocation);
        Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, (ITextureObject)dynamicTexture);
    }

    @Override
    public void onEnable() {
        if (this.Field2268.isEmpty()) {
            this.Field2268 = Class509.Method1342();
        }
    }

    @Subscriber
    public void Method2045(Class8 class8) {
        block2: {
            if (Class489.mc.player == null || Class489.mc.world == null) {
                return;
            }
            if (this.Field2268.get(class8.Method85().toUpperCase()) == null) break block2;
            String string = this.Field2268.get(class8.Method85().toUpperCase());
            if (this.Field2267.get(string) == null) {
                mc.addScheduledTask(() -> this.Method714(string));
            }
            class8.Method84(this.Field2267.get(string));
            class8.Cancel();
        }
    }

    public Class489() {
        super("Capes", Category.RENDER, new String[0]);
    }
}