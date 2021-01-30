package me.darki.konas;

import me.darki.konas.module.Module;

public class AntiBookBan
extends Module {
    public AntiBookBan() {
        super("AntiBookBan", "Prevents you from getting book banned but can cause inventory desync", Category.PLAYER, new String[0]);
    }
}
