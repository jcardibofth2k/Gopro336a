package me.darki.konas.gui.screen;

import java.io.IOException;
import me.darki.konas.unremaped.Class162;
import me.darki.konas.unremaped.Class223;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class KonasBeaconGui
extends GuiBeacon {
    public static ResourceLocation Field1388 = new ResourceLocation("textures/gui/container/beacon.png");
    public static Potion[][] Field1389 = new Potion[][]{{MobEffects.SPEED, MobEffects.HASTE}, {MobEffects.RESISTANCE, MobEffects.JUMP_BOOST}, {MobEffects.STRENGTH}, {MobEffects.REGENERATION}};
    public boolean Field1390;

    public void initGui() {
        super.initGui();
        this.Field1390 = true;
    }

    public KonasBeaconGui(InventoryPlayer inventoryPlayer, IInventory iInventory) {
        super(inventoryPlayer, iInventory);
    }

    public void updateScreen() {
        super.updateScreen();
        if (this.Field1390) {
            int n = 20;
            int n2 = this.guiTop;
            Potion[][] potionArray = Field1389;
            int n3 = potionArray.length;
            for (int i = 0; i < n3; ++i) {
                Potion[] potionArray2;
                for (Potion potion : potionArray2 = potionArray[i]) {
                    Class162 class162 = new Class162(this, n, this.guiLeft - 27, n2, potion, 0);
                    this.buttonList.add(class162);
                    if (potion == Potion.getPotionById(Class223.Field2629)) {
                        class162.Method118(true);
                    }
                    n2 += 27;
                    ++n;
                }
            }
        }
    }

    public void actionPerformed(@NotNull GuiButton guiButton) throws IOException {
        block2: {
            super.actionPerformed(guiButton);
            if (!(guiButton instanceof Class162)) break block2;
            Class162 class162 = (Class162)guiButton;
            if (class162.Method111()) {
                return;
            }
            int n = Potion.getIdFromPotion(Class162.Method1688(class162));
            if (Class162.Method1687(class162) < 3) {
                Class223.Field2629 = n;
            }
            this.buttonList.clear();
            this.initGui();
            this.updateScreen();
        }
    }

    public static ResourceLocation access$200() {
        return Field1388;
    }
}