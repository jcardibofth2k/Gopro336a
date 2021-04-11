package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.event.events.InputUpdateEvent;
import me.darki.konas.unremaped.Class498;
import me.darki.konas.unremaped.Class501;
import me.darki.konas.unremaped.Class566;
import me.darki.konas.util.PlayerUtil;
import net.minecraft.util.math.BlockPos;

public class AutoWalk
extends Module {
    public Setting<Boolean> pathFind = new Setting<>("PathFind", true);
    public Setting<Boolean> jump = new Setting<>("Jump", true);
    public Setting<Boolean> timeout = new Setting<>("Timeout", true);
    public Class501 Field1812 = null;
    public int Field1813 = 0;
    public boolean Field1814 = false;
    public Class566 Field1815 = new Class566();
    public Class566 Field1816 = new Class566();
    public BlockPos Field1817 = null;

    public AutoWalk() {
        super("AutoWalk", "Walk forward", 0, Category.MOVEMENT);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Subscriber
    public void Method1709(InputUpdateEvent inputUpdateEvent) {
        float f;
        if (AutoWalk.mc.world == null) return;
        if (AutoWalk.mc.player == null) {
            return;
        }
        float f2 = f = inputUpdateEvent.Method81().sneak ? 0.3f : 1.0f;
        if (AutoWalk.mc.player.getPosition() != this.Field1817) {
            this.Field1816.Method739();
        }
        if (this.Field1816.Method737(5000.0)) {
            Logger.Method1118("Can't find path!");
            this.toggle();
            return;
        }
        if (!this.pathFind.getValue().booleanValue()) {
            inputUpdateEvent.Method81().moveForward = f;
            if (this.jump.getValue() == false) return;
            if (!AutoWalk.mc.player.collidedHorizontally) return;
            if (!AutoWalk.mc.player.onGround) return;
            inputUpdateEvent.Method81().jump = true;
            return;
        }
        if (this.Field1812 == null) {
            Logger.Method1118("Please use the .goto command!");
            this.toggle();
            return;
        }
        if (this.Field1814) {
            if (this.Field1812.Method1430().isEmpty()) {
                Logger.Method1118("Done!");
                this.toggle();
                return;
            }
            this.Field1814 = false;
        }
        BlockPos blockPos = new BlockPos(AutoWalk.mc.player.posX, AutoWalk.mc.player.onGround ? AutoWalk.mc.player.posY + 0.5 : AutoWalk.mc.player.posY, AutoWalk.mc.player.posZ);
        if (this.Field1812.Method1419().equals(blockPos)) {
            Logger.Method1118("Done!");
            this.toggle();
            return;
        }
        if (!this.Field1815.Method737(150.0)) {
            inputUpdateEvent.Method81().moveForward = f;
        }
        Class498 class498 = this.Field1812.Method1430().get(this.Field1813);
        int n = this.Field1812.Method1430().indexOf(blockPos);
        if (blockPos.equals(class498)) {
            ++this.Field1813;
            if (this.Field1813 >= this.Field1812.Method1430().size()) {
                this.Field1814 = true;
                this.Field1813 = 0;
                this.Field1812.Method1422();
            }
        } else if (n > this.Field1813) {
            this.Field1813 = n + 1;
            if (this.Field1813 >= this.Field1812.Method1430().size()) {
                this.Field1814 = true;
                this.Field1813 = 0;
                this.Field1812.Method1422();
            }
        }
        if (this.Field1814) {
            if (this.Field1812.Method1430().isEmpty()) {
                this.pathFind.setValue(Boolean.valueOf(false));
                this.toggle();
                return;
            }
            this.Field1814 = false;
        }
        if (blockPos.getX() != class498.getX() || blockPos.getZ() != class498.getZ()) {
            inputUpdateEvent.Method81().moveForward = f;
            this.Field1815.Method739();
            double[] dArray = PlayerUtil.Method1088((float)class498.getX() + 0.5f, class498.getY(), (float)class498.getZ() + 0.5f, AutoWalk.mc.player);
            AutoWalk.mc.player.rotationYaw = (float)dArray[0];
            if (this.Field1813 <= 0 || !this.Field1812.Method1430().get(this.Field1813 - 1).Method930()) {
                if (blockPos.getY() >= class498.getY()) return;
            }
            inputUpdateEvent.Method81().jump = true;
            return;
        }
        if (blockPos.getY() == class498.getY()) return;
        if (blockPos.getY() < class498.getY()) {
            if (this.Field1813 < this.Field1812.Method1430().size() - 1 && !class498.up().equals(this.Field1812.Method1430().get(this.Field1813 + 1))) {
                ++this.Field1813;
            }
            inputUpdateEvent.Method81().jump = true;
            return;
        }
        while (this.Field1813 < this.Field1812.Method1430().size() - 1 && this.Field1812.Method1430().get(this.Field1813).down().equals(this.Field1812.Method1430().get(this.Field1813 + 1))) {
            ++this.Field1813;
        }
        inputUpdateEvent.Method81().moveForward = f;
        this.Field1815.Method739();
    }
}