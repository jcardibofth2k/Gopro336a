package me.darki.konas.unremaped;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.module.render.ESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Class554
extends FrameBuffer {
    public int Field821 = this.Method692("DiffuseSamper");
    public int Field822 = this.Method692("TexelSize");
    public int Field823 = this.Method692("Color");
    public int Field824 = this.Method692("Fill");
    public int Field825 = this.Method692("SampleRadius");
    public int Field826 = this.Method692("RenderOutline");
    public int Field827 = this.Method692("OutlineFade");

    @Override
    public FrameBuffer Method689() {
        if (this.Field634 == -1 || this.Field635 == -1 || this.Field638 == -1) {
            throw new RuntimeException("Invalid IDs!");
        }
        EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)this.Field634);
        int n = Math.max(Minecraft.getDebugFPS(), 30);
        GL11.glClear((int)16640);
        ((IEntityRenderer)Class554.mc.entityRenderer).updateFogColor(((IEntityRenderer)Class554.mc.entityRenderer).getRenderEndNanoTime() + (long)(1000000000 / n));
        ARBShaderObjects.glUseProgramObjectARB((int)this.Field638);
        ARBShaderObjects.glUniform1iARB((int)this.Field821, (int)0);
        GL13.glActiveTexture((int)33984);
        GL11.glEnable((int)3553);
        GL11.glBindTexture((int)3553, (int)this.Field628);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer((int)2);
        floatBuffer.position(0);
        floatBuffer.put(1.0f / (float)this.Field629 * (((Float) ESP.width.getValue()).floatValue() / 5.0f));
        floatBuffer.put(1.0f / (float)this.Field630 * (((Float) ESP.width.getValue()).floatValue() / 5.0f));
        floatBuffer.flip();
        ARBShaderObjects.glUniform2ARB((int)this.Field822, (FloatBuffer)floatBuffer);
        FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer((int)4);
        floatBuffer2.position(0);
        floatBuffer2.put((float)((ColorValue) ESP.shaderColor.getValue()).Method769() / 255.0f);
        floatBuffer2.put((float)((ColorValue) ESP.shaderColor.getValue()).Method770() / 255.0f);
        floatBuffer2.put((float)((ColorValue) ESP.shaderColor.getValue()).Method779() / 255.0f);
        floatBuffer2.put((float)((ColorValue) ESP.shaderColor.getValue()).Method782() / 255.0f);
        floatBuffer2.flip();
        ARBShaderObjects.glUniform4ARB((int)this.Field823, (FloatBuffer)floatBuffer2);
        FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer((int)4);
        floatBuffer3.position(0);
        if (((Boolean) ESP.shaderFill.getValue()).booleanValue()) {
            floatBuffer3.put((float)((ColorValue) ESP.shaderFillColor.getValue()).Method769() / 255.0f);
            floatBuffer3.put((float)((ColorValue) ESP.shaderFillColor.getValue()).Method770() / 255.0f);
            floatBuffer3.put((float)((ColorValue) ESP.shaderFillColor.getValue()).Method779() / 255.0f);
            floatBuffer3.put((float)((ColorValue) ESP.shaderFillColor.getValue()).Method782() / 255.0f);
        } else {
            floatBuffer3.put(0.0f);
            floatBuffer3.put(0.0f);
            floatBuffer3.put(0.0f);
            floatBuffer3.put(0.0f);
        }
        floatBuffer3.flip();
        ARBShaderObjects.glUniform4ARB((int)this.Field824, (FloatBuffer)floatBuffer3);
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)1);
        intBuffer.position(0);
        intBuffer.put(4);
        intBuffer.flip();
        ARBShaderObjects.glUniform1ARB((int)this.Field825, (IntBuffer)intBuffer);
        IntBuffer intBuffer2 = BufferUtils.createIntBuffer((int)1);
        intBuffer2.position(0);
        intBuffer2.put(((Boolean) ESP.shaderOutline.getValue()).booleanValue() ? 1 : 0);
        intBuffer2.flip();
        ARBShaderObjects.glUniform1ARB((int)this.Field826, (IntBuffer)intBuffer2);
        IntBuffer intBuffer3 = BufferUtils.createIntBuffer((int)1);
        intBuffer3.position(0);
        intBuffer3.put((Boolean) ESP.shaderFade.getValue() != false ? 1 : 0);
        intBuffer3.flip();
        ARBShaderObjects.glUniform1ARB((int)this.Field827, (IntBuffer)intBuffer3);
        this.Method687();
        ARBShaderObjects.glUseProgramObjectARB((int)0);
        return this;
    }

    public Class554(Framebuffer framebuffer) {
        super(framebuffer);
    }
}