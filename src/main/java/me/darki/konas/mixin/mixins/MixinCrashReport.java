package me.darki.konas.mixin.mixins;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.Module;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={CrashReport.class})
public class MixinCrashReport {
    @Inject(method={"getSectionsInStringBuilder"}, at={@At(value="INVOKE", target="Lnet/minecraft/crash/CrashReportCategory;appendToStringBuilder(Ljava/lang/StringBuilder;)V", ordinal=1)})
    private void Method1813(StringBuilder builder, CallbackInfo ci) {
        builder.append("Active Modules: \n");
        for (Module module : ModuleManager.getEnabledModules()) {
            builder.append(module.getName()).append("\n");
        }
        builder.append("\n");
    }
}