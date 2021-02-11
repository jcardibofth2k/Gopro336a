package me.darki.konas;

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
<<<<<<< HEAD:src/main/java/me/darki/konas/unremaped/Class398.java
        if (MathUtil.Method1087(((Class537)Freecam.control.getValue()).Method851())) {
=======
        if (MathUtil.Method1087(Freecam.Field2458.getValue().Method851())) {
>>>>>>> f776e1904b63ad8ae23617cc10076a951d8b81da:src/main/java/me/darki/konas/Class398.java
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
