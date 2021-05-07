package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.gui.screen.KonasBeaconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(value=Side.CLIENT)
public class Class164
extends GuiButton {
    public ResourceLocation Field1724;
    public int Field1725;
    public int Field1726;
    public boolean Field1727;

    public void Method118(boolean bl) {
        this.Field1727 = bl;
    }

    public boolean Method111() {
        return this.Field1727;
    }

    public Class164(int n, int n2, int n3, ResourceLocation resourceLocation, int n4, int n5) {
        super(n, n2, n3, 22, 22, "");
        this.Field1724 = resourceLocation;
        this.Field1725 = n4;
        this.Field1726 = n5;
    }

    public void drawButton(@NotNull Minecraft minecraft, int n, int n2, float f) {
        block6: {
            if (!this.visible) break block6;
            minecraft.getTextureManager().bindTexture(KonasBeaconGui.access$200());
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.hovered = n >= this.x && n2 >= this.y && n < this.x + this.width && n2 < this.y + this.height;
            int n3 = 0;
            if (!this.enabled) {
                n3 += this.width * 2;
            } else if (this.Field1727) {
                n3 += this.width;
            } else if (this.hovered) {
                n3 += this.width * 3;
            }
            this.drawTexturedModalRect(this.x, this.y, n3, 219, this.width, this.height);
            if (!KonasBeaconGui.access$200().equals((Object)this.Field1724)) {
                minecraft.getTextureManager().bindTexture(this.Field1724);
            }
            this.drawTexturedModalRect(this.x + 2, this.y + 2, this.Field1725, this.Field1726, 18, 18);
        }
    }
}