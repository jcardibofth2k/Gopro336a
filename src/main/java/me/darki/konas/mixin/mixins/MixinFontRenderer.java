package me.darki.konas.mixin.mixins;

import dev.tigr.emojiapi.Emoji;
import dev.tigr.emojiapi.Emojis;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={FontRenderer.class})
public abstract class MixinFontRenderer {
    @Shadow
    private boolean Field18;
    @Shadow
    private boolean Field19;
    @Shadow
    private boolean Field20;
    @Shadow
    private boolean Field21;
    @Shadow
    private boolean Field22;
    @Shadow
    private boolean Field23;
    @Shadow
    private int Field24;
    @Shadow
    private float Field25;
    @Shadow
    private float Field26;
    @Shadow
    private float Field27;
    @Shadow
    private float Field28;
    @Shadow
    protected float Field29;
    @Shadow
    protected float Field30;
    @Shadow
    public Random Field31;
    @Final
    @Shadow
    private int[] Field32;

    @Shadow
    protected abstract void Method49(float var1, float var2, float var3, float var4);

    @Shadow
    public abstract int Method50(char var1);

    @Shadow
    protected abstract float Method51(char var1, boolean var2);

    @Shadow
    protected abstract void Method52(float var1);

    @Overwrite
    private void Method53(String text, boolean shadow) {
        int size = this.Method54("  ");
        ArrayList<Emoji> emojis = new ArrayList<Emoji>();
        for (String possile : text.split(":")) {
            if (!Emojis.isEmoji(possile)) continue;
            emojis.add(new Emoji(possile));
        }
        for (Emoji emoji : emojis) {
            if (!shadow) {
                int index = text.indexOf(":" + emoji.getName() + ":");
                if (index == -1) continue;
                int x = this.Method54(text.substring(0, index));
                Minecraft.getMinecraft().getTextureManager().bindTexture(Emojis.getEmoji(emoji));
                GlStateManager.color(1.0f, 1.0f, 1.0f, this.Field28);
                Gui.drawScaledCustomSizeModalRect((int)(this.Field29 + (float)x), (int)this.Field30, 0.0f, 0.0f, size, size, size, size, (float)size, (float)size);
            }
            text = text.replaceFirst(":" + emoji.getName() + ":", "  ");
        }
        for (int i = 0; i < text.length(); ++i) {
            boolean flag;
            char c0 = text.charAt(i);
            if (c0 == '\u00a7' && i + 1 < text.length()) {
                int i1 = "0123456789abcdefklmnor".indexOf(String.valueOf(text.charAt(i + 1)).toLowerCase(Locale.ROOT).charAt(0));
                if (i1 < 16) {
                    int j1;
                    this.Field18 = false;
                    this.Field19 = false;
                    this.Field20 = false;
                    this.Field21 = false;
                    this.Field22 = false;
                    if (i1 < 0) {
                        i1 = 15;
                    }
                    if (shadow) {
                        i1 += 16;
                    }
                    this.Field24 = j1 = this.Field32[i1];
                    this.Method49((float)(j1 >> 16) / 255.0f, (float)(j1 >> 8 & 0xFF) / 255.0f, (float)(j1 & 0xFF) / 255.0f, this.Field28);
                } else if (i1 == 16) {
                    this.Field18 = true;
                } else if (i1 == 17) {
                    this.Field19 = true;
                } else if (i1 == 18) {
                    this.Field20 = true;
                } else if (i1 == 19) {
                    this.Field21 = true;
                } else if (i1 == 20) {
                    this.Field22 = true;
                } else {
                    this.Field18 = false;
                    this.Field19 = false;
                    this.Field20 = false;
                    this.Field21 = false;
                    this.Field22 = false;
                    this.Method49(this.Field25, this.Field27, this.Field26, this.Field28);
                }
                ++i;
                continue;
            }
            int j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c0);
            if (this.Field18 && j != -1) {
                char c1;
                int k = this.Method50(c0);
                while (k != this.Method50(c1 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".charAt(j = this.Field31.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".length())))) {
                }
                c0 = c1;
            }
            float f1 = j == -1 || this.Field23 ? 0.5f : 1.0f;
            boolean bl = flag = (c0 == '\u0000' || j == -1 || this.Field23) && shadow;
            if (flag) {
                this.Field29 -= f1;
                this.Field30 -= f1;
            }
            float f = this.Method51(c0, this.Field22);
            if (flag) {
                this.Field29 += f1;
                this.Field30 += f1;
            }
            if (this.Field19) {
                this.Field29 += f1;
                if (flag) {
                    this.Field29 -= f1;
                    this.Field30 -= f1;
                }
                this.Method51(c0, this.Field22);
                this.Field29 -= f1;
                if (flag) {
                    this.Field29 += f1;
                    this.Field30 += f1;
                }
                f += 1.0f;
            }
            this.Method52(f);
        }
    }

    @Overwrite
    public int Method54(String text) {
        if (text == null) {
            return 0;
        }
        for (String possibleEmoji : text.split(":")) {
            if (!Emojis.isEmoji(possibleEmoji)) continue;
            text = text.replaceFirst(":" + possibleEmoji + ":", "  ");
        }
        int i = 0;
        boolean flag = false;
        for (int j = 0; j < text.length(); ++j) {
            char c0 = text.charAt(j);
            int k = this.Method50(c0);
            if (k < 0 && j < text.length() - 1) {
                if ((c0 = text.charAt(++j)) != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag = false;
                    }
                } else {
                    flag = true;
                }
                k = 0;
            }
            i += k;
            if (!flag || k <= 0) continue;
            ++i;
        }
        return i;
    }
}