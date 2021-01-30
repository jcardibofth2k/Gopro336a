package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketVehicleMove;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketVehicleMove.class})
public interface ICPacketVehicleMove {
    @Accessor(value="y")
    public void Method337(double var1);

    @Accessor(value="y")
    public double Method338();
}
