package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.util.Iterator;

import me.darki.konas.unremaped.Class492;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Radar
extends Element {
    public Setting<arrowType> rotate = new Setting<>("Rotate", arrowType.ARROW);
    public Setting<Boolean> players = new Setting<>("Players", true);
    public Setting<Boolean> mobs = new Setting<>("Mobs", true);
    public Setting<Boolean> animals = new Setting<>("Animals", true);
    public Setting<Boolean> invisibles = new Setting<>("Invisibles", true);
    public Setting<Double> size = new Setting<>("Size", 1.0, 2.0, 0.1, 0.05);

    @Override
    public void onRender2D() {
        block13: {
            if (Radar.mc.player == null || Radar.mc.world == null) {
                return;
            }
            if (Radar.mc.gameSettings.showDebugInfo) break block13;
            int n = (int)this.Method2320();
            int n2 = (int)this.Method2324();
            int n3 = (int)((double)this.Method2329() * 0.425);
            int n4 = (int)((float)n + this.Method2329() / 2.0f);
            int n5 = (int)((float)n2 + this.Method2322() / 2.0f);
            RenderUtil2.Method1338(this.Method2320(), this.Method2324(), this.Method2329(), this.Method2322(), this.color.getValue().Method774());
            RenderUtil2.Method1336(this.Method2320(), this.Method2324(), this.Method2329(), this.Method2322(), 1.0f, this.outline.getValue().Method774());
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            Iterator<Entity> objectArrayArray = Radar.mc.world.loadedEntityList.iterator();
            while (objectArrayArray.hasNext()) {
                double d;
                double d2;
                double d3;
                Entity objectArrayArray2 = objectArrayArray.next();
                if (!this.shouldRender(objectArrayArray2) || !((d3 = Math.sqrt((d2 = Radar.mc.player.posX - objectArrayArray2.posX) * d2 + (d = Radar.mc.player.posZ - objectArrayArray2.posZ) * d)) <= (double)this.Method2329() / 2.0 - (double)this.Method2329() / 50.0)) continue;
                GL11.glPushMatrix();
                double d4 = Radar.mc.player.posX - objectArrayArray2.posX + (double)n + (double)(this.Method2329() / 2.0f);
                double d5 = Radar.mc.player.posZ - objectArrayArray2.posZ + (double)n2 + (double)(this.Method2329() / 2.0f);
                GL11.glTranslated(n4, n5, 0.0);
                GL11.glRotatef(this.rotate.getValue() != arrowType.ARROW ? -Radar.mc.player.rotationYaw : -180.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslated(-n4, -n5, 0.0);
                double d6 = 12.0;
                GL11.glTranslated(d4, d5, 0.0);
                GL11.glRotatef((this.rotate.getValue() != arrowType.ARROW ? Radar.mc.player.rotationYaw + 180.0f : 0.0f) + 180.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslated(-d4, -d5, 0.0);
                if (objectArrayArray2 instanceof EntityPlayer) {
                    if (Class492.Method1989(objectArrayArray2.getName())) {
                        GL11.glColor4f(0.3f, 1.0f, 0.3f, 1.0f);
                    } else {
                        GL11.glColor4f(1.0f, 0.3f, 0.3f, 1.0f);
                    }
                } else if (objectArrayArray2 instanceof EntityMob) {
                    GL11.glColor4f(1.0f, 0.5f, 0.5f, 1.0f);
                } else if (objectArrayArray2 instanceof EntityAnimal) {
                    GL11.glColor4f(0.5f, 1.0f, 0.5f, 1.0f);
                } else {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.0f);
                }
                GL11.glBegin(9);
                GL11.glVertex2d(d4, d5 + (double) this.size.getValue().floatValue());
                GL11.glVertex2d(d4 + (double) this.size.getValue().floatValue(), d5);
                GL11.glVertex2d(d4, d5 - (double) this.size.getValue().floatValue());
                GL11.glVertex2d(d4 - (double) this.size.getValue().floatValue(), d5);
                GL11.glEnd();
                GL11.glPopMatrix();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)n4, (float)n5, 0.0f);
            GL11.glRotatef(this.rotate.getValue() == arrowType.ARROW ? Radar.mc.player.rotationYaw : 180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef((float)(-n4), (float)(-n5), 0.0f);
            GL11.glBegin(9);
            GL11.glVertex2d(n4, n5 + 3);
            GL11.glVertex2d((double)n4 + 1.5, n5 - 3);
            GL11.glVertex2d((double)n4 - 1.5, n5 - 3);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(3553);
            for (Object[] objectArray : new Object[][]{{"N", -90.0}, {"S", 90.0}, {"E", 0.0}, {"W", 180.0}}) {
                if (objectArray.length < 2) {
                    return;
                }
                if (!(objectArray[0] instanceof String) || !(objectArray[1] instanceof Double)) continue;
                String string = (String)objectArray[0];
                Double d = (Double)objectArray[1];
                if (this.rotate.getValue() != arrowType.ARROW) {
                    d = d - (double)Radar.mc.player.rotationYaw;
                    d = d - 180.0;
                }
                Radar.mc.fontRenderer.drawStringWithShadow(string, (float)((double)n4 + (double)n3 * Math.cos(Math.toRadians(d))) - (float)(Radar.mc.fontRenderer.getStringWidth(string) / 2), (float)((double)n5 + (double)n3 * Math.sin(Math.toRadians(d))) - (float)(Radar.mc.fontRenderer.FONT_HEIGHT / 2), -1);
            }
            GL11.glPopMatrix();
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    public Radar() {
        super("Radar", 2.0f, 100.0f, 100.0f, 100.0f);
        this.color.getValue().Method771(new Color(31, 31, 31, 170).hashCode());
        this.outline.getValue().Method771(new Color(255, 85, 255, 255).hashCode());
    }

    public boolean shouldRender(Object object) {
        if (!(object instanceof Entity)) {
            return false;
        }
        Entity entity = (Entity)object;
        if (entity == Radar.mc.player) {
            return false;
        }
        if (entity.isInvisible() && !this.invisibles.getValue().booleanValue()) {
            return false;
        }
        if (object instanceof EntityPlayer) {
            if (this.players.getValue().booleanValue()) {
                EntityPlayer entityPlayer = (EntityPlayer)object;
                return true;
            }
        } else if (object instanceof EntityMob) {
            return this.mobs.getValue().booleanValue();
        } else return object instanceof EntityAnimal && this.animals.getValue().booleanValue();
        return false;
    }
}