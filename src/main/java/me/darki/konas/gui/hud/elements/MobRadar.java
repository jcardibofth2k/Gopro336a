package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Comparator;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class557;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityWolf;

public class MobRadar
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));
    public static Setting<Boolean> ghasts = new Setting<>("Ghasts", true);
    public static Setting<Boolean> slimes = new Setting<>("Slimes", true);
    public static Setting<Boolean> donkeys = new Setting<>("Donkeys", true);
    public static Setting<Boolean> llamas = new Setting<>("Llamas", true);
    public static Setting<Boolean> cats = new Setting<>("Cats", true);
    public static Setting<Boolean> dogs = new Setting<>("Dogs", true);
    public static Setting<Boolean> parrots = new Setting<>("Parrots", true);
    public static Setting<ParentSetting> display = new Setting<>("Display", new ParentSetting(false));
    public static Setting<Boolean> health = new Setting<>("Health", true).setParentSetting(Field2390);
    public static Setting<Boolean> entityID = new Setting<>("EntityID", true).setParentSetting(Field2390);
    public static Setting<Boolean> distance = new Setting<>("Distance", true).setParentSetting(Field2390);
    public static Setting<Boolean> coordinates = new Setting<>("Coordinates", true).setParentSetting(Field2390);

    public MobRadar() {
        super("MobRadar", 500.0f, 400.0f, 10.0f, 10.0f);
    }

    @Override
    public void onRender2D() {
        block0: {
            super.onRender2D();
            float[] fArray = new float[]{0.0f};
            float[] fArray2 = new float[]{0.0f};
            MobRadar.mc.world.loadedEntityList.stream().filter(MobRadar::Method2100).sorted(Comparator.comparing(MobRadar::Method2097)).forEach(arg_0 -> this.Method2098(fArray, fArray2, arg_0));
            this.Method2319(fArray[0]);
            this.Method2323(fArray2[0]);
            if (fArray[0] != 0.0f && fArray2[0] != 0.0f) break block0;
            this.Method2319(50.0f);
            this.Method2323(100.0f);
        }
    }

    public static Float Method2097(Entity entity) {
        return Float.valueOf(MobRadar.mc.player.getDistance(entity));
    }

    public void Method2098(float[] fArray, float[] fArray2, Entity entity) {
        Class557.Method801(this.Method2099((EntityLivingBase)entity), this.Method2320(), this.Method2324() + fArray[0], this.textColor.getValue().Method774());
        fArray[0] = fArray[0] + Class557.Method799(this.Method2099((EntityLivingBase)entity));
        if (Class557.Method800(this.Method2099((EntityLivingBase)entity)) > fArray2[0]) {
            fArray2[0] = Class557.Method800(this.Method2099((EntityLivingBase)entity));
        }
    }

    public String Method2099(EntityLivingBase entityLivingBase) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String string = " (" + (entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount()) + ")";
        String string2 = " [" + entityLivingBase.getEntityId() + "]";
        String string3 = " \u00c2\u00a7c" + decimalFormat.format(MobRadar.mc.player.getDistance(entityLivingBase));
        String string4 = " \u00c2\u00a7rXYZ " + entityLivingBase.getPosition().getX() + " " + entityLivingBase.getPosition().getY() + " " + entityLivingBase.getPosition().getZ();
        return entityLivingBase.getName() + (health.getValue() != false ? string : "") + (entityID.getValue() != false ? string2 : "") + (distance.getValue() != false ? string3 : "") + (coordinates.getValue().booleanValue() ? string4 : "");
    }

    public static boolean Method2100(Entity entity) {
        return ghasts.getValue() != false && entity instanceof EntityGhast || slimes.getValue() != false && entity instanceof EntitySlime || donkeys.getValue() != false && entity instanceof EntityDonkey || llamas.getValue() != false && entity instanceof EntityLlama || cats.getValue() != false && entity instanceof EntityOcelot || dogs.getValue() != false && entity instanceof EntityWolf || parrots.getValue() != false && entity instanceof EntityParrot;
    }
}