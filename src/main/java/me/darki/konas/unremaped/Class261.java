package me.darki.konas.unremaped;

import com.viaversion.viafabric.ViaFabric;
import com.viaversion.viafabric.protocol.ProtocolCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class Class261
extends GuiButton {
    public float Field2045 = 1.0f;
    public boolean Field2046;
    public float Field2047 = 0.0f;
    public float Field2048 = ProtocolCollection.values().length - 1;

    public Class261(int n, int n2, int n3, int n4, int n5) {
        super(n, n2, n3, n4, n5, "Protocol");
        for (int i = 0; i < ProtocolCollection.values().length; ++i) {
            if (ProtocolCollection.values()[i].getVersion().getVersion() != ViaFabric.getInstance().getVersion()) continue;
            this.Field2045 = (float)i / (float)ProtocolCollection.values().length;
            this.displayString = "Protocol: " + ProtocolCollection.values()[i].getVersion().getName();
        }
    }

    public void mouseReleased(int n, int n2) {
        this.Field2046 = false;
    }

    public int getHoverState(boolean bl) {
        return 0;
    }

    public void mouseDragged(Minecraft minecraft, int n, int n2) {
        block1: {
            if (!this.visible) break block1;
            if (this.Field2046) {
                this.Field2045 = (float)(n - (this.x + 4)) / (float)(this.width - 8);
                this.Field2045 = MathHelper.clamp((float)this.Field2045, (float)0.0f, (float)1.0f);
                int n3 = (int)(this.Field2045 * this.Field2048);
                ViaFabric.getInstance().setVersion(ProtocolCollection.values()[n3].getVersion().getVersion());
                this.displayString = "Protocol: " + ProtocolCollection.values()[n3].getVersion().getName();
            }
            minecraft.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawTexturedModalRect(this.x + (int)(this.Field2045 * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.Field2045 * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    public boolean mousePressed(Minecraft minecraft, int n, int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.Field2045 = (float)(n - (this.x + 4)) / (float)(this.width - 8);
            this.Field2045 = MathHelper.clamp((float)this.Field2045, (float)0.0f, (float)1.0f);
            int n3 = (int)(this.Field2045 * this.Field2048);
            ViaFabric.getInstance().setVersion(ProtocolCollection.values()[n3].getVersion().getVersion());
            this.displayString = "Protocol: " + ProtocolCollection.values()[n3].getVersion().getName();
            this.Field2046 = true;
            return true;
        }
        return false;
    }
}
