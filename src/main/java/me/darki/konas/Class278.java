package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;

public class Class278
extends Module {
    public Setting<Boolean> Field1808 = new Setting<>("Safe", false);

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block1: {
            boolean bl;
            if (Class278.mc.player == null || Class278.mc.world == null) {
                return;
            }
            boolean bl2 = bl = (Boolean)this.Field1808.getValue() == false || Class278.mc.player.getHealth() < 0.0f || Class278.mc.player.isDead;
            if (!(Class278.mc.currentScreen instanceof GuiGameOver) || !bl) break block1;
            Class278.mc.player.respawnPlayer();
        }
    }

    public Class278() {
        super("AutoRespawn", Category.MISC, new String[0]);
    }
}
