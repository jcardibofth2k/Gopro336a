package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketUseEntity.class})
public interface ICPacketUseEntity {
    @Accessor(value="entityId")
    void Method506(int var1);

    @Accessor(value="action")
    void Method507(CPacketUseEntity.Action var1);
}
