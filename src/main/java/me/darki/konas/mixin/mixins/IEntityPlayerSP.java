package me.darki.konas.mixin.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={EntityPlayerSP.class})
public interface IEntityPlayerSP {
    @Accessor(value="serverSneakState")
    public void Method223(boolean var1);

    @Accessor(value="serverSneakState")
    public boolean Method224();

    @Accessor(value="serverSprintState")
    public void Method225(boolean var1);

    @Accessor(value="serverSprintState")
    public boolean Method226();

    @Accessor(value="prevOnGround")
    public void Method227(boolean var1);

    @Accessor(value="prevOnGround")
    public boolean Method228();

    @Accessor(value="autoJumpEnabled")
    public void Method229(boolean var1);

    @Accessor(value="autoJumpEnabled")
    public boolean Method230();

    @Accessor(value="lastReportedPosX")
    public void Method231(double var1);

    @Accessor(value="lastReportedPosX")
    public double Method232();

    @Accessor(value="lastReportedPosY")
    public void Method233(double var1);

    @Accessor(value="lastReportedPosY")
    public double Method234();

    @Accessor(value="lastReportedPosZ")
    public void Method235(double var1);

    @Accessor(value="lastReportedPosZ")
    public double Method236();

    @Accessor(value="lastReportedYaw")
    public void Method237(float var1);

    @Accessor(value="lastReportedYaw")
    public float Method238();

    @Accessor(value="lastReportedPitch")
    public void Method239(float var1);

    @Accessor(value="lastReportedPitch")
    public float Method240();

    @Accessor(value="positionUpdateTicks")
    public void Method241(int var1);

    @Accessor(value="positionUpdateTicks")
    public int Method242();

    @Invoker(value="onUpdateWalkingPlayer")
    public void Method243();
}
