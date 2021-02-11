package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.Class119;
import me.darki.konas.Class392;
import me.darki.konas.Class398;
import me.darki.konas.Class46;
import me.darki.konas.KonasPlayer;
import me.darki.konas.Class49;
import me.darki.konas.Class537;
import me.darki.konas.MathUtil;
import me.darki.konas.TickEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.event.world.WorldEvent;

public class Freecam
extends Module {
    public static Setting<Class537> control = new Setting<>("Control", new Class537(56));
    public Setting<Boolean> follow = new Setting<>("Follow", false);
    public Setting<Boolean> copyInv = new Setting<>("CopyInv", false);
    public Setting<Float> hSpeed = new Setting<>("HSpeed", Float.valueOf(1.0f), Float.valueOf(2.0f), Float.valueOf(0.2f), Float.valueOf(0.1f));
    public Setting<Float> vSpeed = new Setting<>("VSpeed", Float.valueOf(1.0f), Float.valueOf(2.0f), Float.valueOf(0.2f), Float.valueOf(0.1f));
    public Entity Field2463 = null;
    public int Field2464 = -1;
    public Entity Field2465 = null;
    public KonasPlayer Field2466 = null;
    public MovementInput Field2467;
    public MovementInput Field2468;

    @Override
    public void onDisable() {
        if (Freecam.mc.player == null) {
            return;
        }
        if (this.Field2466 != null) {
            Freecam.mc.world.removeEntity(this.Field2466);
        }
        this.Field2466 = null;
        Freecam.mc.player.movementInput = new MovementInputFromOptions(Freecam.mc.gameSettings);
        mc.setRenderViewEntity(this.Field2465);
        Freecam.mc.renderChunksMany = true;
    }

    public Entity Method2118() {
        int n;
        if (this.Field2463 == null) {
            this.Field2463 = Freecam.mc.player;
        }
        if (this.Field2464 != (n = Freecam.mc.player.ticksExisted)) {
            this.Field2464 = n;
<<<<<<< HEAD
            this.Field2463 = this.isEnabled() ? (MathUtil.Method1087(((Class537) control.getValue()).Method851()) ? Freecam.mc.player : (mc.getRenderViewEntity() == null ? Freecam.mc.player : mc.getRenderViewEntity())) : Freecam.mc.player;
=======
            this.Field2463 = this.isEnabled() ? (MathUtil.Method1087(Field2458.getValue().Method851()) ? Freecam.mc.player : (mc.getRenderViewEntity() == null ? Freecam.mc.player : mc.getRenderViewEntity())) : Freecam.mc.player;
>>>>>>> f776e1904b63ad8ae23617cc10076a951d8b81da
        }
        return this.Field2463;
    }

    @Subscriber
    public void Method2119(Class119 class119) {
        class119.Cancel();
    }

    public Freecam() {
        super("Freecam", "Control your camera separately to your body", Category.PLAYER);
        this.Field2467 = new Class392(this, Freecam.mc.gameSettings);
        this.Field2468 = new Class398(this, Freecam.mc.gameSettings);
    }

    @Subscriber
    public void Method2120(Class46 class46) {
        class46.Cancel();
    }

    @Override
    public void onEnable() {
        if (Freecam.mc.player == null) {
            return;
        }
<<<<<<< HEAD
        this.Field2466 = new KonasPlayer((Boolean)this.copyInv.getValue(), (Boolean)this.follow.getValue(), ((Float)this.hSpeed.getValue()).floatValue(), ((Float)this.vSpeed.getValue()).floatValue());
=======
        this.Field2466 = new KonasPlayer(this.Field2460.getValue(), this.Field2459.getValue(), this.Field2461.getValue().floatValue(), this.Field2462.getValue().floatValue());
>>>>>>> f776e1904b63ad8ae23617cc10076a951d8b81da
        this.Field2466.movementInput = this.Field2467;
        Freecam.mc.player.movementInput = this.Field2468;
        Freecam.mc.world.addEntityToWorld(-921, this.Field2466);
        this.Field2465 = mc.getRenderViewEntity();
        mc.setRenderViewEntity(this.Field2466);
        Freecam.mc.renderChunksMany = false;
    }

    @Subscriber
    public void onTickEvent(TickEvent tickEvent) {
        if (Freecam.mc.player == null || Freecam.mc.world == null) {
            return;
        }
<<<<<<< HEAD
        this.Field2466.Method400((Boolean)this.copyInv.getValue());
        this.Field2466.Method407((Boolean)this.follow.getValue());
        this.Field2466.Method406(((Float)this.hSpeed.getValue()).floatValue());
        this.Field2466.Method402(((Float)this.vSpeed.getValue()).floatValue());
=======
        this.Field2466.Method400(this.Field2460.getValue());
        this.Field2466.Method407(this.Field2459.getValue());
        this.Field2466.Method406(this.Field2461.getValue().floatValue());
        this.Field2466.Method402(this.Field2462.getValue().floatValue());
>>>>>>> f776e1904b63ad8ae23617cc10076a951d8b81da
    }

    @Subscriber
    public void Method1656(WorldEvent.Unload unload) {
        mc.setRenderViewEntity(Freecam.mc.player);
        this.toggle();
    }

    @Subscriber
    public void Method2121(Class49 class49) {
        block0: {
            if (this.Method2118() == null) break block0;
            class49.Method274(this.Method2118());
        }
    }
}
