package me.darki.konas.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={Minecraft.class})
public interface IMinecraft {
    @Accessor(value="integratedServer")
    void Method55(IntegratedServer var1);

    @Accessor(value="timer")
    Timer Method56();

    @Accessor(value="rightClickDelayTimer")
    void Method57(int var1);

    @Accessor(value="rightClickDelayTimer")
    int Method58();

    @Accessor(value="session")
    void Method59(Session var1);

    @Invoker(value="rightClickMouse")
    void Method60();
}