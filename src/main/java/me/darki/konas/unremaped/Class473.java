package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import java.util.Arrays;
import java.util.EventListener;

import me.darki.konas.event.events.PacketEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class Class473
implements EventListener {
    public static Class473 Field2557;
    public float[] Field2558 = new float[20];
    public int Field2559 = 0;
    public long Field2560;

    public void Method2189() {
        this.Field2559 = 0;
        this.Field2560 = -1L;
        Arrays.fill(this.Field2558, 0.0f);
    }

    public float Method2190() {
        float f;
        float f2;
        float f3;
        try {
            f3 = this.Field2558[this.Field2558.length - 1];
            f2 = 0.0f;
            f = 20.0f;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return 20.0f;
        }
        return MathHelper.clamp((float)f3, (float)f2, (float)f);
    }

    public void Method2191() {
        if (this.Field2560 != -1L) {
            float f = (float)(System.currentTimeMillis() - this.Field2560) / 1000.0f;
            this.Field2558[this.Field2559 % this.Field2558.length] = MathHelper.clamp((float)(20.0f / f), (float)0.0f, (float)20.0f);
            ++this.Field2559;
        }
        this.Field2560 = System.currentTimeMillis();
    }

    public float Method2192() {
        float f = 20.0f;
        for (float f2 : this.Field2558) {
            if (!(f2 > 0.0f) || !(f2 < f)) continue;
            f = f2;
        }
        return MathHelper.clamp((float)f, (float)0.0f, (float)20.0f);
    }

    public float Method2193() {
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 : this.Field2558) {
            if (!(f3 > 0.0f)) continue;
            f2 += f3;
            f += 1.0f;
        }
        return MathHelper.clamp((float)(f2 / f), (float)0.0f, (float)20.0f);
    }

    public Class473() {
        EventDispatcher.Companion.register(this);
        EventDispatcher.Companion.subscribe(this);
        this.Method2189();
    }

    @Subscriber
    public void Method2194(PacketEvent packetEvent) {
        block0: {
            if (!(packetEvent.getPacket() instanceof SPacketTimeUpdate)) break block0;
            Field2557.Method2191();
        }
    }
}