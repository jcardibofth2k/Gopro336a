package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class24;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiHunger
extends Module {
    public static Setting<Boolean> sprint = new Setting<>("Sprint", true);
    public static Setting<Boolean> ground = new Setting<>("Ground", true);
    public boolean Field2015 = false;

    public AntiHunger() {
        super("AntiHunger", "Prevents hunger loss", Category.PLAYER, "NoHunger");
    }

    /**
     * edited by Gopro336
     */
    @Subscriber
    public void Method536(Class24 class24) {
        CPacketEntityAction cPacketEntityAction = null;
        if (class24.getPacket() instanceof CPacketEntityAction) {
            cPacketEntityAction = (CPacketEntityAction)class24.getPacket();
            if (sprint.getValue().booleanValue() && (cPacketEntityAction.getAction() == CPacketEntityAction.Action.START_SPRINTING || cPacketEntityAction.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)) {
                class24.setCanceled(true);
            }
        }
        if (class24.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer cPacketPlayer;
            cPacketPlayer = (CPacketPlayer)class24.getPacket();
            boolean bl = AntiHunger.mc.player.onGround;
            if (ground.getValue().booleanValue() && this.Field2015 && bl) {
                if (cPacketPlayer.getY(0.0) == (!((ICPacketPlayer)cPacketEntityAction).Method1702() ? 0.0 : AntiHunger.mc.player.posY)) {
                    ((ICPacketPlayer)cPacketEntityAction).Method1700(false);
                }
            }
            this.Field2015 = bl;
        }
    }

    @Override
    public void onDisable() {
        block0: {
            if (!sprint.getValue().booleanValue() || AntiHunger.mc.player == null || !AntiHunger.mc.player.isSprinting()) break block0;
            AntiHunger.mc.player.connection.sendPacket(new CPacketEntityAction(AntiHunger.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }

    @Override
    public void onEnable() {
        block0: {
            if (!sprint.getValue().booleanValue() || AntiHunger.mc.player == null) break block0;
            AntiHunger.mc.player.connection.sendPacket(new CPacketEntityAction(AntiHunger.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }
}
