package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.PacketEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class Class456 {
    public static Class456 Field486 = new Class456();
    public long Field487 = -1L;
    public float[] Field488 = new float[20];
    public int Field489;

    @Subscriber
    public void Method563(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketTimeUpdate) {
            if (this.Field487 != -1L) {
                long l = System.currentTimeMillis();
                float f = (float)(l - this.Field487) / 1000.0f;
                this.Field488[this.Field489 % this.Field488.length] = MathHelper.clamp((float)(20.0f / f), (float)0.0f, (float)20.0f);
                ++this.Field489;
            }
            this.Field487 = System.currentTimeMillis();
        }
    }

    public float Method564() {
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 : this.Field488) {
            if (!(f3 > 0.0f)) continue;
            f2 += f3;
            f += 1.0f;
        }
        return MathHelper.clamp((float)(f2 / f), (float)0.0f, (float)20.0f);
    }
}