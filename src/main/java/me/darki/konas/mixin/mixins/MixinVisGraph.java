package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class70;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={VisGraph.class})
public class MixinVisGraph {
    @Inject(method={"setOpaqueCube"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method2307(BlockPos pos, CallbackInfo info) {
        Class70 event = new Class70();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
}