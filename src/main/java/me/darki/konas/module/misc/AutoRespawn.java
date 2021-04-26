package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;

public class AutoRespawn
extends Module {
    public Setting<Boolean> safe = new Setting<>("Safe", false);

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block1: {
            boolean bl;
            if (AutoRespawn.mc.player == null || AutoRespawn.mc.world == null) {
                return;
            }
            boolean bl2 = bl = (Boolean)this.safe.getValue() == false || AutoRespawn.mc.player.getHealth() < 0.0f || AutoRespawn.mc.player.isDead;
            if (!(AutoRespawn.mc.currentScreen instanceof GuiGameOver) || !bl) break block1;
            AutoRespawn.mc.player.respawnPlayer();
        }
    }

    public AutoRespawn() {
        super("AutoRespawn", Category.MISC, new String[0]);
    }
}