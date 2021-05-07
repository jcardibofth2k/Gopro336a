package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.unremaped.portalHitboxEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IEntity;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraftforge.client.GuiIngameForge;

public class BetterPortals
extends Module {
    public static Setting<Boolean> allowGuis = new Setting<>("AllowGuis", true);
    public static Setting<Boolean> noRender = new Setting<>("NoRender", true);
    public static Setting<Boolean> noHitbox = new Setting<>("NoHitbox", false);
    public boolean Field2266 = false;

    @Subscriber
    public void Method2044(portalHitboxEvent portalHitboxEvent) {
        block0: {
            if (!noHitbox.getValue().booleanValue()) break block0;
            portalHitboxEvent.Cancel();
        }
    }

    public BetterPortals() {
        super("BetterPortals", "Remove unwanted portal functionality", Category.PLAYER, "PortalChat");
    }

    @Override
    public void onEnable() {
        this.Field2266 = GuiIngameForge.renderPortal;
    }

    @Override
    public void onDisable() {
        GuiIngameForge.renderPortal = this.Field2266;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (BetterPortals.mc.player == null || BetterPortals.mc.world == null) {
            return;
        }
        if (allowGuis.getValue().booleanValue()) {
            ((IEntity) BetterPortals.mc.player).setInPortal(false);
        }
        if (noRender.getValue().booleanValue()) {
            GuiIngameForge.renderPortal = false;
        }
    }
}