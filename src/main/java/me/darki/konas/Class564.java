package me.darki.konas;

import java.nio.ByteBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public abstract class Class564 {
    public static Minecraft Field626 = Minecraft.getMinecraft();
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

    public Class564(Framebuffer framebuffer) {
        this(framebuffer.framebufferTexture, Class564.Field626.displayWidth, Class564.Field626.displayHeight, new ScaledResolution(Field626).getScaledWidth(), new ScaledResolution(Field626).getScaledHeight());
    }

    public void Method686() {
        block2: {
            if (this.Field635 > -1) {
                EXTFramebufferObject.glDeleteRenderbuffersEXT(this.Field635);
            }
            if (this.Field634 > -1) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(this.Field634);
            }
            if (this.Field633 <= -1) break block2;
            GL11.glDeleteTextures(this.Field633);
        }
    }

    public Class564(int n, int n2, int n3, int n4, int n5) {
        this.Field628 = n;
        this.Field629 = n2;
        this.Field630 = n3;
        this.Field631 = (int)((double)n4 * Field627);
        this.Field632 = (int)((double)n5 * Field627);
        this.Method690();
        this.Method691();
    }

    public void Method687() {
        GL11.glScaled(1.0 / Field627, 1.0 / Field627, 1.0 / Field627);
        GL11.glDisable(3553);
        GL11.glBegin(4);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, this.Field632);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(this.Field631, this.Field632);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(this.Field631, this.Field632);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(this.Field631, 0.0);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glEnd();
        GL11.glScaled(Field627, Field627, Field627);
    }

    public void Method688() {
        int n = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
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

    public abstract Class564 Method689();

    public void Method690() {
        this.Field634 = EXTFramebufferObject.glGenFramebuffersEXT();
        this.Field633 = GL11.glGenTextures();
        this.Field635 = EXTFramebufferObject.glGenRenderbuffersEXT();
        GL11.glBindTexture(3553, this.Field633);
        GL11.glTexParameterf(3553, 10241, 9729.0f);
        GL11.glTexParameterf(3553, 10240, 9729.0f);
        GL11.glTexParameterf(3553, 10242, 10496.0f);
        GL11.glTexParameterf(3553, 10243, 10496.0f);
        GL11.glBindTexture(3553, 0);
        GL11.glBindTexture(3553, this.Field633);
        GL11.glTexImage2D(3553, 0, 32856, this.Field629, this.Field630, 0, 6408, 5121, (ByteBuffer)null);
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.Field634);
        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, this.Field633, 0);
        EXTFramebufferObject.glBindRenderbufferEXT(36161, this.Field635);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, this.Field629, this.Field630);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, this.Field635);
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
            ARBShaderObjects.glAttachObjectARB(this.Field638, this.Field636);
            ARBShaderObjects.glAttachObjectARB(this.Field638, this.Field637);
            ARBShaderObjects.glLinkProgramARB(this.Field638);
            if (ARBShaderObjects.glGetObjectParameteriARB(this.Field638, 35714) == 0) {
                System.err.println(ShaderUtil.Method845(this.Field638));
                return;
            }
            ARBShaderObjects.glValidateProgramARB(this.Field638);
            if (ARBShaderObjects.glGetObjectParameteriARB(this.Field638, 35715) == 0) {
                System.err.println(ShaderUtil.Method845(this.Field638));
                return;
            }
            ARBShaderObjects.glUseProgramObjectARB(0);
        }
    }

    public int Method692(String string) {
        return ARBShaderObjects.glGetUniformLocationARB(this.Field638, string);
    }
}
