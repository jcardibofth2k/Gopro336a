package me.darki.konas.module.render;

import com.google.common.io.ByteStreams;
import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class480;
import me.darki.konas.unremaped.Class522;
import me.darki.konas.unremaped.Class91;
import me.darki.konas.setting.ColorValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.opengl.GL11;

public class HitMarkers
extends Module {
    public static Setting<Class522> mode = new Setting<>("Mode", Class522.PACKET);
    public static Setting<Boolean> sfx = new Setting<>("SFX", false);
    public static Setting<Float> volume = new Setting<>("Volume", Float.valueOf(2.5f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).visibleIf(sfx::getValue);
    public static Setting<Integer> time = new Setting<>("Time", 5, 20, 1, 1);
    public static Setting<Integer> offset = new Setting<>("Offset", 5, 20, 1, 1);
    public static Setting<Integer> length = new Setting<>("Length", 10, 50, 1, 1);
    public static Setting<Float> thickness = new Setting<>("Thickness", Float.valueOf(1.0f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-1));
    public int Field1277 = 0;
    public File Field1278;

    public void Method124() {
        FloatControl floatControl;
        Clip clip;
        if (!this.Field1278.exists()) {
            return;
        }
        File file = this.Field1278;
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioInputStream audioInputStream2 = audioInputStream;
        Clip clip2 = AudioSystem.getClip();
        Clip clip3 = clip = clip2;
        AudioInputStream audioInputStream3 = audioInputStream2;
        clip3.open(audioInputStream3);
        Clip clip4 = clip;
        FloatControl.Type type = FloatControl.Type.MASTER_GAIN;
        Control control = clip4.getControl(type);
        FloatControl floatControl2 = floatControl = (FloatControl)control;
        float f = -50.0f;
        Setting<Float> setting = volume;
        Object t = setting.getValue();
        Float f2 = (Float)t;
        float f3 = f2.floatValue();
        float f4 = f + f3 * 10.0f;
        floatControl2.setValue(f4);
        Clip clip5 = clip;
        try {
            clip5.start();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void Method1248(int n, int n2) {
        GL11.glPushMatrix();
        float f = n;
        float f2 = n2;
        float f3 = 0.0f;
        GL11.glTranslatef(f, f2, f3);
        float f4 = 45.0f;
        float f5 = n;
        float f6 = n2;
        float f7 = 8000.0f;
        GL11.glRotatef(f4, f5, f6, f7);
        float f8 = -n;
        float f9 = -n2;
        float f10 = 0.0f;
        GL11.glTranslatef(f8, f9, f10);
        HitMarkers hitMarkers = this;
        int n3 = n;
        int n4 = n2;
        Setting<ColorValue> setting = color;
        Object t = setting.getValue();
        ColorValue colorValue = (ColorValue)t;
        Color color = colorValue.Method775();
        hitMarkers.Method1249(n3, n4, color);
        try {
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public HitMarkers() {
        super("HitMarkers", Category.RENDER);
        this.Field1278 = new File(HitMarkers.mc.mcDataDir + File.separator + "Konas" + File.separator + "hitmarker.wav");
        try {
            if (!this.Field1278.exists()) {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/sounds/hitmarker.wav");
                FileOutputStream fileOutputStream = new FileOutputStream(this.Field1278);
                ByteStreams.copy(inputStream, fileOutputStream);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void Method1249(int n, int n2, Color color) {
        Setting<Float> setting = thickness;
        Object t = setting.getValue();
        Float f = (Float)t;
        float f2 = f.floatValue();
        float f3 = f2;
        float f4 = (float)n - f3;
        int n3 = n2;
        Setting<Integer> setting2 = offset;
        Object t2 = setting2.getValue();
        Integer n4 = (Integer)t2;
        int n5 = n4;
        int n6 = n3 - n5;
        Setting<Integer> setting3 = length;
        Object t3 = setting3.getValue();
        Integer n7 = (Integer)t3;
        int n8 = n7;
        float f5 = n6 - n8;
        float f6 = (float)n + f3;
        int n9 = n2;
        Setting<Integer> setting4 = offset;
        Object t4 = setting4.getValue();
        Integer n10 = (Integer)t4;
        int n11 = n10;
        float f7 = n9 - n11;
        Color color2 = color;
        boolean bl = true;
        Class480.Method2083(f4, f5, f6, f7, color2, bl);
        float f8 = (float)n - f3;
        int n12 = n2;
        Setting<Integer> setting5 = offset;
        Object t5 = setting5.getValue();
        Integer n13 = (Integer)t5;
        int n14 = n13;
        float f9 = n12 + n14;
        float f10 = (float)n + f3;
        int n15 = n2;
        Setting<Integer> setting6 = offset;
        Object t6 = setting6.getValue();
        Integer n16 = (Integer)t6;
        int n17 = n16;
        int n18 = n15 + n17;
        Setting<Integer> setting7 = length;
        Object t7 = setting7.getValue();
        Integer n19 = (Integer)t7;
        int n20 = n19;
        float f11 = n18 + n20;
        Color color3 = color;
        boolean bl2 = true;
        Class480.Method2083(f8, f9, f10, f11, color3, bl2);
        int n21 = n;
        Setting<Integer> setting8 = offset;
        Object t8 = setting8.getValue();
        Integer n22 = (Integer)t8;
        int n23 = n22;
        int n24 = n21 - n23;
        Setting<Integer> setting9 = length;
        Object t9 = setting9.getValue();
        Integer n25 = (Integer)t9;
        int n26 = n25;
        float f12 = n24 - n26;
        float f13 = (float)n2 - f3;
        int n27 = n;
        Setting<Integer> setting10 = offset;
        Object t10 = setting10.getValue();
        Integer n28 = (Integer)t10;
        int n29 = n28;
        float f14 = n27 - n29;
        float f15 = (float)n2 + f3;
        Color color4 = color;
        boolean bl3 = true;
        Class480.Method2083(f12, f13, f14, f15, color4, bl3);
        int n30 = n;
        Setting<Integer> setting11 = offset;
        Object t11 = setting11.getValue();
        Integer n31 = (Integer)t11;
        int n32 = n31;
        float f16 = n30 + n32;
        float f17 = (float)n2 - f3;
        int n33 = n;
        Setting<Integer> setting12 = offset;
        Object t12 = setting12.getValue();
        Integer n34 = (Integer)t12;
        int n35 = n34;
        int n36 = n33 + n35;
        Setting<Integer> setting13 = length;
        Object t13 = setting13.getValue();
        Integer n37 = (Integer)t13;
        int n38 = n37;
        float f18 = n36 + n38;
        float f19 = (float)n2 + f3;
        Color color5 = color;
        boolean bl4 = true;
        try {
            Class480.Method2083(f16, f17, f18, f19, color5, bl4);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block19: {
            if (HitMarkers.mc.player == null) {
                return;
            }
            if (mode.getValue() == Class522.MOUSE) {
                return;
            }
            SendPacketEvent class242 = sendPacketEvent;
            Packet packet = class242.getPacket();
            if (!(packet instanceof CPacketUseEntity)) break block19;
            SendPacketEvent class243 = sendPacketEvent;
            Packet packet2 = class243.getPacket();
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)packet2;
            CPacketUseEntity.Action action = cPacketUseEntity.getAction();
            if (action != CPacketUseEntity.Action.ATTACK) break block19;
            HitMarkers hitMarkers = this;
            Setting<Integer> setting = time;
            Object t = setting.getValue();
            Integer n = (Integer)t;
            int n2 = n;
            hitMarkers.Field1277 = n2;
            Setting<Boolean> setting2 = sfx;
            Object t2 = setting2.getValue();
            Boolean bl = (Boolean)t2;
            boolean bl2 = bl;
            if (!bl2) break block19;
            HitMarkers class5212 = this;
            try {
                class5212.Method124();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Subscriber
    public void Method466(Class91 class91) {
        block10: {
            ScaledResolution scaledResolution;
            if (this.Field1277 <= 0) break block10;
            ScaledResolution scaledResolution2 = scaledResolution;
            ScaledResolution scaledResolution3 = scaledResolution;
            Minecraft minecraft = mc;
            scaledResolution2(minecraft);
            ScaledResolution scaledResolution4 = scaledResolution3;
            HitMarkers hitMarkers = this;
            ScaledResolution scaledResolution5 = scaledResolution4;
            int n = scaledResolution5.getScaledWidth();
            int n2 = n / 2;
            ScaledResolution scaledResolution6 = scaledResolution4;
            int n3 = scaledResolution6.getScaledHeight();
            int n4 = n3 / 2;
            hitMarkers.Method1248(n2, n4);
            try {
                --this.Field1277;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Subscriber
    public void Method1250(MouseEvent mouseEvent) {
        block15: {
            if (HitMarkers.mc.player == null) {
                return;
            }
            if (mode.getValue() == Class522.PACKET) {
                return;
            }
            MouseEvent mouseEvent2 = mouseEvent;
            int n = mouseEvent2.getButton();
            if (!(n == 0 & HitMarkers.mc.objectMouseOver.entityHit != null)) break block15;
            HitMarkers hitMarkers = this;
            Setting<Integer> setting = time;
            Object t = setting.getValue();
            Integer n2 = (Integer)t;
            int n3 = n2;
            hitMarkers.Field1277 = n3;
            Setting<Boolean> setting2 = sfx;
            Object t2 = setting2.getValue();
            Boolean bl = (Boolean)t2;
            boolean bl2 = bl;
            if (!bl2) break block15;
            HitMarkers class5212 = this;
            try {
                class5212.Method124();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}