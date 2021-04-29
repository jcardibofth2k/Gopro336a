package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class643;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockWeb;
import net.minecraft.init.Blocks;

public class Avoid
extends Module {
    public Setting<Boolean> fire = new Setting<>("Fire", true);
    public Setting<Boolean> webs = new Setting<>("Webs", true);
    public Setting<Boolean> pressurePlates = new Setting<>("PressurePlates", true);
    public Setting<Boolean> wires = new Setting<>("Wires", true);
    public Setting<Boolean> cactus = new Setting<>("Cactus", true);
    public Setting<Boolean> unloaded = new Setting<>("Unloaded", true);

    public Avoid() {
        super("Avoid", "Avoids hazards", Category.PLAYER, "AntiFlame", "AntiFire", "AntiCactus");
    }

    @Subscriber
    public void Method836(Class643 class643) {
        block0: {
            Block block = class643.Method1245();
            if (!((double)class643.Method1244().getY() >= Math.floor(Avoid.mc.player.posY)) || !(block.equals(Blocks.FIRE) && this.fire.getValue() != false || block instanceof BlockWeb && this.webs.getValue() != false || block instanceof BlockBasePressurePlate && this.pressurePlates.getValue() != false || block == Blocks.TRIPWIRE && this.wires.getValue() != false || block.equals(Blocks.CACTUS) && this.cactus.getValue() != false) && (Avoid.mc.world.isBlockLoaded(class643.Method1244(), false) && class643.Method1244().getY() >= 0 || !this.unloaded.getValue().booleanValue())) break block0;
            class643.Method1239(Block.FULL_BLOCK_AABB);
        }
    }
}