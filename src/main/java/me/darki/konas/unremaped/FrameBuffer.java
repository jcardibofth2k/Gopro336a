package me.darki.konas.unremaped;

import java.nio.ByteBuffer;

import me.darki.konas.util.ShaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public abstract class FrameBuffer {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static double Field627 = 2.0;
    public int Field628;
    public int Field629;
    public int Field630;
    public int Field631;
    public int Field632;
    public int Field633 = -1;
    public int Field634 = -1;
    public int Field635 = -1;
    public int Field636 = -1;
    public int Field637 = -1;
    public int Field638 = -1;

    public int Method685() {
        return this.Field633;
    }

    public FrameBuffer(Framebuffer framebuffer) {
        this(framebuffer.framebufferTexture, FrameBuffer.mc.displayWidth, FrameBuffer.mc.displayHeight, new ScaledResolution(mc).getScaledWidth(), new ScaledResolution(mc).getScaledHeight());
    }

    public void Method686() {
        block2: {
            if (this.Field635 > -1) {
                EXTFramebufferObject.glDeleteRenderbuffersEXT((int)this.Field635);
            }
            if (this.Field634 > -1) {
                EXTFramebufferObject.glDeleteFramebuffersEXT((int)this.Field634);
            }
            if (this.Field633 <= -1) break block2;
            GL11.glDeleteTextures((int)this.Field633);
        }
    }

    public FrameBuffer(int n, int n2, int n3, int n4, int n5) {
        this.Field628 = n;
        this.Field629 = n2;
        this.Field630 = n3;
        this.Field631 = (int)((double)n4 * Field627);
        this.Field632 = (int)((double)n5 * Field627);
        this.Method690();
        this.Method691();
    }

    public void Method687() {
        GL11.glScaled((double)(1.0 / Field627), (double)(1.0 / Field627), (double)(1.0 / Field627));
        GL11.glDisable((int)3553);
        GL11.glBegin((int)4);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)0.0, (double)this.Field632);
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)this.Field631, (double)this.Field632);
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)this.Field631, (double)this.Field632);
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2d((double)this.Field631, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glEnd();
        GL11.glScaled((double)Field627, (double)Field627, (double)Field627);
    }

    public void Method688() {
        int n = EXTFramebufferObject.glCheckFramebufferStatusEXT((int)36160);
        switch (n) {
            case 36053: {
                return;
            }
            case 36054: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT");
            }
            case 36055: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT");
            }
            default: {
                throw new RuntimeException("glCheckFramebufferStatusEXT returned unknown status:" + n);
            }
            case 36057: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT");
            }
            case 36058: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT");
            }
            case 36059: 
        }
        throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT");
    }

    public abstract FrameBuffer Method689();

    public void Method690() {
        this.Field634 = EXTFramebufferObject.glGenFramebuffersEXT();
        this.Field633 = GL11.glGenTextures();
        this.Field635 = EXTFramebufferObject.glGenRenderbuffersEXT();
        GL11.glBindTexture((int)3553, (int)this.Field633);
        GL11.glTexParameterf((int)3553, (int)10241, (float)9729.0f);
        GL11.glTexParameterf((int)3553, (int)10240, (float)9729.0f);
        GL11.glTexParameterf((int)3553, (int)10242, (float)10496.0f);
        GL11.glTexParameterf((int)3553, (int)10243, (float)10496.0f);
        GL11.glBindTexture((int)3553, (int)0);
        GL11.glBindTexture((int)3553, (int)this.Field633);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)this.Field629, (int)this.Field630, (int)0, (int)6408, (int)5121, (ByteBuffer)null);
        EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)this.Field634);
        EXTFramebufferObject.glFramebufferTexture2DEXT((int)36160, (int)36064, (int)3553, (int)this.Field633, (int)0);
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)this.Field635);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)this.Field629, (int)this.Field630);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)this.Field635);
        this.Method688();
    }

    /*
     * Unable to fully structure code
     */
    public void Method691() {
        block13: {
            block12: {
                if (this.Field638 != -1) break block13;
                this.Field638 = ARBShaderObjects.glCreateProgramObjectARB();
                if (this.Field636 != -1) ** GOTO lbl17
                v0 = "#version 120\n\nvoid main()\n{\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}\n";
                var1_1 = v0;
                v1 = this;
                v2 = var1_1;
                v3 = 35633;
                v4 = ShaderUtil.Method837(v2, v3);
                v1.Field636 = v4;
lbl17:
                // 2 sources

                if (this.Field637 != -1) break block12;
                v5 = "#version 120\n\nuniform sampler2D DiffuseSamper;\nuniform vec2 TexelSize;\nuniform vec4 Color;\nuniform vec4 Fill;\nuniform int SampleRadius;\nuniform bool RenderOutline;\nuniform bool OutlineFade;\n\nvoid main()\n{\n    vec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st);\n\n    if(centerCol.a != 0.0F)\n    {\n        gl_FragColor = vec4(Fill.r, Fill.g, Fill.b, Fill.a);\n        return;\n    }\n    float closest = SampleRadius * 1.0F;\n    for(int xo = -SampleRadius; xo <= SampleRadius; xo++)\n    {\n        for(int yo = -SampleRadius; yo <= SampleRadius; yo++)\n        {\n            vec4 currCol = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y));\n            if(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F || currCol.a != 0.0F)\n            {\n                float currentDist = sqrt(xo * xo + yo * yo);\n                if(currentDist < closest)\n                {\n                    closest = currentDist;\n                }\n            }\n        }\n    }\n    if (RenderOutline) {\n        float fade = max(0, ((SampleRadius * 1.0F) - (closest - 1)) / (SampleRadius * 1.0F));\n        if (OutlineFade) {\n            float colorFade = max(0, fade - 1F);\n            gl_FragColor = vec4(Color.r - colorFade, Color.g - colorFade, Color.b - colorFade, fade);\n        } else {\n            if (fade > 0.5F) {\n                gl_FragColor = vec4(Color.r, Color.g, Color.b, Color.a);\n            } else {\n                gl_FragColor = vec4(0F, 0F, 0F, 0F);\n            }\n        }\n    } else {\n        gl_FragColor = vec4(0F, 0F, 0F, 0F);\n    }\n}\n";
                var1_1 = v5;
                v6 = this;
                v7 = var1_1;
                v8 = 35632;
                v9 = ShaderUtil.Method837(v7, v8);
                try {
                    v6.Field637 = v9;
                }
                catch (Exception var1_2) {
                    this.Field638 = -1;
                    this.Field636 = -1;
                    this.Field637 = -1;
                    var1_2.printStackTrace();
                }
            }
            if (this.Field638 == -1) break block13;
            ARBShaderObjects.glAttachObjectARB((int)this.Field638, (int)this.Field636);
            ARBShaderObjects.glAttachObjectARB((int)this.Field638, (int)this.Field637);
            ARBShaderObjects.glLinkProgramARB((int)this.Field638);
            if (ARBShaderObjects.glGetObjectParameteriARB((int)this.Field638, (int)35714) == 0) {
                System.err.println(ShaderUtil.Method845(this.Field638));
                return;
            }
            ARBShaderObjects.glValidateProgramARB((int)this.Field638);
            if (ARBShaderObjects.glGetObjectParameteriARB((int)this.Field638, (int)35715) == 0) {
                System.err.println(ShaderUtil.Method845(this.Field638));
                return;
            }
            ARBShaderObjects.glUseProgramObjectARB((int)0);
        }
    }

    public int Method692(String string) {
        return ARBShaderObjects.glGetUniformLocationARB((int)this.Field638, (CharSequence)string);
    }
}