package me.darki.konas.mixin.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={EntityPlayerSP.class})
public interface IEntityPlayerSP {
    @Accessor(value="serverSneakState")
    void Method223(boolean var1);

    @Accessor(value="serverSneakState")
    boolean Method224();

    @Accessor(value="serverSprintState")
    void Method225(boolean var1);

    @Accessor(value="serverSprintState")
    boolean Method226();

    @Accessor(value="prevOnGround")
    void Method227(boolean var1);

    @Accessor(value="prevOnGround")
    boolean Method228();

    @Accessor(value="autoJumpEnabled")
    void Method229(boolean var1);

    @Accessor(value="autoJumpEnabled")
    boolean Method230();

    @Accessor(value="lastReportedPosX")
    void Method231(double var1);

    @Accessor(value="lastReportedPosX")
    double Method232();

    @Accessor(value="lastReportedPosY")
    void Method233(double var1);

    @Accessor(value="lastReportedPosY")
    double Method234();

    @Accessor(value="lastReportedPosZ")
    void Method235(double var1);

    @Accessor(value="lastReportedPosZ")
    double Method236();

    @Accessor(value="lastReportedYaw")
    void Method237(float var1);

    @Accessor(value="lastReportedYaw")
    float Method238();

    @Accessor(value="lastReportedPitch")
    void Method239(float var1);

    @Accessor(value="lastReportedPitch")
    float Method240();

    @Accessor(value="positionUpdateTicks")
    void Method241(int var1);

    @Accessor(value="positionUpdateTicks")
    int Method242();

    @Invoker(value="onUpdateWalkingPlayer")
    void Method243();
}
