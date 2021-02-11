package me.darki.konas;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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
extends Class564 {
    public int Field821 = this.Method692("DiffuseSamper");
    public int Field822 = this.Method692("TexelSize");
    public int Field823 = this.Method692("Color");
    public int Field824 = this.Method692("Fill");
    public int Field825 = this.Method692("SampleRadius");
    public int Field826 = this.Method692("RenderOutline");
    public int Field827 = this.Method692("OutlineFade");

    @Override
    public Class564 Method689() {
        if (this.Field634 == -1 || this.Field635 == -1 || this.Field638 == -1) {
            throw new RuntimeException("Invalid IDs!");
        }
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.Field634);
        int n = Math.max(Minecraft.getDebugFPS(), 30);
        GL11.glClear(16640);
        ((IEntityRenderer)Class554.Field626.entityRenderer).Method1911(((IEntityRenderer)Class554.Field626.entityRenderer).Method1915() + (long)(1000000000 / n));
        ARBShaderObjects.glUseProgramObjectARB(this.Field638);
        ARBShaderObjects.glUniform1iARB(this.Field821, 0);
        GL13.glActiveTexture(33984);
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, this.Field628);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(2);
        floatBuffer.position(0);
        floatBuffer.put(1.0f / (float)this.Field629 * (ESP.width.getValue().floatValue() / 5.0f));
        floatBuffer.put(1.0f / (float)this.Field630 * (ESP.width.getValue().floatValue() / 5.0f));
        floatBuffer.flip();
        ARBShaderObjects.glUniform2ARB(this.Field822, floatBuffer);
        FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer(4);
        floatBuffer2.position(0);
        floatBuffer2.put((float) ESP.shaderColor.getValue().Method769() / 255.0f);
        floatBuffer2.put((float) ESP.shaderColor.getValue().Method770() / 255.0f);
        floatBuffer2.put((float) ESP.shaderColor.getValue().Method779() / 255.0f);
        floatBuffer2.put((float) ESP.shaderColor.getValue().Method782() / 255.0f);
        floatBuffer2.flip();
        ARBShaderObjects.glUniform4ARB(this.Field823, floatBuffer2);
        FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer(4);
        floatBuffer3.position(0);
        if (ESP.shaderFill.getValue().booleanValue()) {
            floatBuffer3.put((float) ESP.shaderFillColor.getValue().Method769() / 255.0f);
            floatBuffer3.put((float) ESP.shaderFillColor.getValue().Method770() / 255.0f);
            floatBuffer3.put((float) ESP.shaderFillColor.getValue().Method779() / 255.0f);
            floatBuffer3.put((float) ESP.shaderFillColor.getValue().Method782() / 255.0f);
        } else {
            floatBuffer3.put(0.0f);
            floatBuffer3.put(0.0f);
            floatBuffer3.put(0.0f);
            floatBuffer3.put(0.0f);
        }
        floatBuffer3.flip();
        ARBShaderObjects.glUniform4ARB(this.Field824, floatBuffer3);
        IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        intBuffer.position(0);
        intBuffer.put(4);
        intBuffer.flip();
        ARBShaderObjects.glUniform1ARB(this.Field825, intBuffer);
        IntBuffer intBuffer2 = BufferUtils.createIntBuffer(1);
        intBuffer2.position(0);
        intBuffer2.put(ESP.shaderOutline.getValue().booleanValue() ? 1 : 0);
        intBuffer2.flip();
        ARBShaderObjects.glUniform1ARB(this.Field826, intBuffer2);
        IntBuffer intBuffer3 = BufferUtils.createIntBuffer(1);
        intBuffer3.position(0);
        intBuffer3.put(ESP.shaderFade.getValue() != false ? 1 : 0);
        intBuffer3.flip();
        ARBShaderObjects.glUniform1ARB(this.Field827, intBuffer3);
        this.Method687();
        ARBShaderObjects.glUseProgramObjectARB(0);
        return this;
    }

    public Class554(Framebuffer framebuffer) {
        super(framebuffer);
    }
}
