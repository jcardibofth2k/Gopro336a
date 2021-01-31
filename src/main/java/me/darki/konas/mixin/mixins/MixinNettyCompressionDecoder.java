package me.darki.konas.mixin.mixins;

import me.darki.konas.Class167;
import me.darki.konas.module.player.AntiBookBan;
import net.minecraft.network.NettyCompressionDecoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value={NettyCompressionDecoder.class})
public class MixinNettyCompressionDecoder {
    @ModifyConstant(method={"decode"}, constant={@Constant(intValue=0x200000)})
    private int Method1601(int n) {
        if (Class167.Method1610(AntiBookBan.class).isEnabled()) {
            return Integer.MAX_VALUE;
        }
        return n;
    }
}
