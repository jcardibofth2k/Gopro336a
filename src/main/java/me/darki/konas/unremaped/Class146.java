package me.darki.konas.unremaped;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Class146
extends Element {
    public Float Method1815(PotionEffect potionEffect, Float f) {
        return Float.valueOf(Math.max(f.floatValue(), Class557.Method800(this.Method1818(potionEffect))));
    }

    public void Method1816(AtomicInteger atomicInteger, AtomicReference atomicReference, PotionEffect potionEffect) {
        atomicInteger.set(atomicInteger.get() + (int)Class557.Method799(this.Method1818(potionEffect)) + 1);
        atomicReference.updateAndGet(arg_0 -> this.Method1815(potionEffect, arg_0));
    }

    public int Method1817(PotionEffect potionEffect) {
        String string;
        switch (string = this.Method1819(potionEffect)) {
            case "Absorption": {
                return new Color(33, 118, 255).hashCode();
            }
            case "Fire Resistance": {
                return new Color(247, 152, 36).hashCode();
            }
            case "Regeneration": {
                return new Color(232, 142, 237).hashCode();
            }
            case "Strength": 
            case "Resistance": {
                return new Color(237, 28, 36).hashCode();
            }
            case "Hunger": {
                return new Color(41, 191, 18).hashCode();
            }
            case "Jump Boost": {
                return new Color(0, 204, 51).hashCode();
            }
            case "Haste": {
                return new Color(255, 207, 0).hashCode();
            }
            case "Speed": {
                return new Color(0, 255, 227).hashCode();
            }
        }
        return Color.WHITE.hashCode();
    }

    public String Method1818(PotionEffect potionEffect) {
        return this.Method1819(potionEffect) + " " + Potion.getPotionDurationString((PotionEffect)potionEffect, (float)1.0f);
    }

    public String Method1819(PotionEffect potionEffect) {
        return I18n.format((String)potionEffect.getEffectName(), (Object[])new Object[0]);
    }

    public void Method1820(AtomicReference atomicReference, AtomicInteger atomicInteger, PotionEffect potionEffect) {
        Class557.Method801(this.Method1818(potionEffect), this.Method2321() == 0.0f ? this.Method2320() : this.Method2320() + ((Float)atomicReference.get()).floatValue() - Class557.Method800(this.Method1818(potionEffect)), (float)atomicInteger.get() + this.Method2324(), this.Method1817(potionEffect));
        atomicInteger.addAndGet((int)Class557.Method799(this.Method1818(potionEffect)) + 1);
    }

    @Override
    public void onRender2D() {
        block0: {
            super.onRender2D();
            AtomicInteger atomicInteger = new AtomicInteger(2);
            AtomicReference<Float> atomicReference = new AtomicReference<Float>(Float.valueOf(0.0f));
            Class146.mc.player.getActivePotionEffects().stream().forEach(arg_0 -> this.Method1816(atomicInteger, atomicReference, arg_0));
            atomicReference.set(Float.valueOf(Math.max(atomicReference.get().floatValue(), 5.0f)));
            this.Method2323(atomicReference.get().floatValue() + 1.0f);
            this.Method2319(atomicInteger.get());
            AtomicInteger atomicInteger2 = new AtomicInteger(0);
            Class146.mc.player.getActivePotionEffects().stream().forEach(arg_0 -> this.Method1820(atomicReference, atomicInteger2, arg_0));
            if (Class146.mc.player.getActivePotionEffects().stream().count() != 0L || !(Class146.mc.currentScreen instanceof Class193)) break block0;
            RenderUtil2.Method1337(this.Method2320(), this.Method2324(), this.Method2329(), this.Method2322(), 0, 0x34000000);
        }
    }

    public Class146() {
        super("Potions", 350.0f, 350.0f, 30.0f, 40.0f);
    }
}