package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.mixin.mixins.ITimer;
import me.darki.konas.module.Module;
import net.minecraft.client.Minecraft;

public class Class565 {
    public Module Field706;
    public int Field707;
    public float Field708;
    public boolean Field709 = false;
    public boolean Field710 = false;

    public boolean Method745() {
        return this.Field710;
    }

    public void Method746(Module module, int n, float f) {
        if (module == this.Field706) {
            this.Field707 = n;
            this.Field708 = f;
            this.Field709 = true;
        } else if (n > this.Field707 || !this.Field709) {
            this.Field706 = module;
            this.Field707 = n;
            this.Field708 = f;
            this.Field709 = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Subscriber
    public void Method747(TickEvent tickEvent) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
            ((ITimer)((IMinecraft)Minecraft.getMinecraft()).getTimer()).setTickLength(50.0f);
            return;
        }
        if (this.Field710 && (double)Class473.Field2557.Method2190() > 0.125) {
            ((ITimer)((IMinecraft)Minecraft.getMinecraft()).getTimer()).setTickLength(Math.min(500.0f, 50.0f * (20.0f / Class473.Field2557.Method2190())));
            return;
        }
        ((ITimer)((IMinecraft)Minecraft.getMinecraft()).getTimer()).setTickLength(this.Field709 ? 50.0f / this.Field708 : 50.0f);
    }

    public void Method748(boolean bl) {
        this.Field710 = bl;
    }

    public void Method749(Module module) {
        if (this.Field706 == module) {
            this.Field709 = false;
        }
    }
}