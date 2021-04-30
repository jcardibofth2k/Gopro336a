package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.module.player.Freecam;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

public class Class398
extends MovementInputFromOptions {
    public Freecam Field1819;

    public Class398(Freecam freecam, GameSettings gameSettings) {
        this.Field1819 = freecam;
        super(gameSettings);
    }

    public void updatePlayerMoveState() {
        if (PlayerUtil.Method1087(((Class537)Freecam.control.getValue()).Method851())) {
            super.updatePlayerMoveState();
        } else {
            this.moveStrafe = 0.0f;
            this.moveForward = 0.0f;
            this.forwardKeyDown = false;
            this.backKeyDown = false;
            this.leftKeyDown = false;
            this.rightKeyDown = false;
            this.jump = false;
            this.sneak = false;
        }
    }
}