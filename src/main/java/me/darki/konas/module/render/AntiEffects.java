package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.init.MobEffects;

public class AntiEffects
extends Module {
    public Setting<Boolean> levitation = new Setting<>("Levitation", true);
    public Setting<Boolean> jumpBoost = new Setting<>("JumpBoost", true);

    public AntiEffects() {
        super("AntiEffects", "Removes unwanted effects from the player", Category.PLAYER);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (AntiEffects.mc.player != null) {
            if (this.levitation.getValue().booleanValue() && AntiEffects.mc.player.isPotionActive(MobEffects.LEVITATION)) {
                AntiEffects.mc.player.removeActivePotionEffect(MobEffects.LEVITATION);
            }
            if (this.jumpBoost.getValue().booleanValue() && AntiEffects.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                AntiEffects.mc.player.removeActivePotionEffect(MobEffects.JUMP_BOOST);
            }
        }
    }
}