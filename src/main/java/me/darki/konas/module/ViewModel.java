package me.darki.konas.module;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.Category;
import me.darki.konas.Class31;
import me.darki.konas.Class656;
import me.darki.konas.Class659;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class ViewModel
extends Module {
    public static Setting<Boolean> cancelEating = new Setting<>("CancelEating", false);
    public static Setting<Boolean> snapEat = new Setting<>("SnapEat", false).Method1191(ViewModel::Method394);
    public static Setting<Boolean> customEating = new Setting<>("CustomEating", false).Method1191(ViewModel::Method393);
    public static Setting<Float> mainHandX = new Setting<>("MainHandX", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandY = new Setting<>("MainHandY", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandZ = new Setting<>("MainHandZ", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandXS = new Setting<>("MainHandXS", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandYS = new Setting<>("MainHandYS", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandZS = new Setting<>("MainHandZS", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandYaw = new Setting<>("MainHandYaw", Float.valueOf(0.0f), Float.valueOf(180.0f), Float.valueOf(-180.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandPitch = new Setting<>("MainHandPitch", Float.valueOf(0.0f), Float.valueOf(180.0f), Float.valueOf(-180.0f), Float.valueOf(1.0f));
    public static Setting<Float> mainHandRoll = new Setting<>("MainHandRoll", Float.valueOf(0.0f), Float.valueOf(180.0f), Float.valueOf(-180.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandX = new Setting<>("OffHandX", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandY = new Setting<>("OffHandY", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandZ = new Setting<>("OffHandZ", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandXS = new Setting<>("OffHandXS", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandYS = new Setting<>("OffHandYS", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandZS = new Setting<>("OffHandZS", Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(-100.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandYaw = new Setting<>("OffHandYaw", Float.valueOf(0.0f), Float.valueOf(180.0f), Float.valueOf(-180.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandPitch = new Setting<>("OffHandPitch", Float.valueOf(0.0f), Float.valueOf(180.0f), Float.valueOf(-180.0f), Float.valueOf(1.0f));
    public static Setting<Float> offHandRoll = new Setting<>("OffHandRoll", Float.valueOf(0.0f), Float.valueOf(180.0f), Float.valueOf(-180.0f), Float.valueOf(1.0f));
    public static Setting<Boolean> itemFov = new Setting<>("ItemFov", false);
    public static Setting<Float> itemFovValue = new Setting<>("ItemFovValue", Float.valueOf(110.0f), Float.valueOf(170.0f), Float.valueOf(90.0f), Float.valueOf(1.0f));

    @Subscriber
    public void Method640(Class659 class659) {
        block8: {
            boolean bl;
            block7: {
                if (ViewModel.mc.player == null) {
                    return;
                }
                class659.Cancel();
                boolean bl2 = true;
                bl = true;
                if (((Boolean) snapEat.getValue()).booleanValue() && (ViewModel.mc.player.getActiveItemStack().getItem() instanceof ItemFood || ViewModel.mc.player.getActiveItemStack().getItem() instanceof ItemPotion)) {
                    if (ViewModel.mc.player.getActiveHand() == EnumHand.MAIN_HAND) {
                        bl2 = false;
                    } else if (ViewModel.mc.player.getActiveHand() == EnumHand.OFF_HAND) {
                        bl = false;
                    }
                }
                if (class659.Method1112() != Class656.MAINHAND) break block7;
                if (bl2) {
                    class659.Method1103(class659.Method1109() + ((Float) mainHandX.getValue()).floatValue() / 100.0f);
                    class659.Method341(class659.Method258() + ((Float) mainHandY.getValue()).floatValue() / 100.0f);
                    class659.Method1111(class659.Method213() + ((Float) mainHandZ.getValue()).floatValue() / 100.0f);
                }
                class659.Method1104(class659.Method1108() + ((Float) mainHandXS.getValue()).floatValue() / 50.0f);
                class659.Method294(class659.Method215() + ((Float) mainHandYS.getValue()).floatValue() / 50.0f);
                class659.Method1106(class659.Method1110() + ((Float) mainHandZS.getValue()).floatValue() / 50.0f);
                class659.Method344(((Float) mainHandYaw.getValue()).floatValue());
                class659.Method1102(((Float) mainHandPitch.getValue()).floatValue());
                class659.Method1105(((Float) mainHandRoll.getValue()).floatValue());
                break block8;
            }
            if (class659.Method1112() != Class656.OFFHAND) break block8;
            if (bl) {
                class659.Method1103(class659.Method1109() + ((Float) offHandX.getValue()).floatValue() / 100.0f);
                class659.Method341(class659.Method258() + ((Float) offHandY.getValue()).floatValue() / 100.0f);
                class659.Method1111(class659.Method213() + ((Float) offHandZ.getValue()).floatValue() / 100.0f);
            }
            class659.Method1104(class659.Method1108() + ((Float) offHandXS.getValue()).floatValue() / 50.0f);
            class659.Method294(class659.Method215() + ((Float) offHandYS.getValue()).floatValue() / 50.0f);
            class659.Method1106(class659.Method1110() + ((Float) offHandZS.getValue()).floatValue() / 50.0f);
            class659.Method344(((Float) offHandYaw.getValue()).floatValue());
            class659.Method1102(((Float) offHandPitch.getValue()).floatValue());
            class659.Method1105(((Float) offHandRoll.getValue()).floatValue());
        }
    }

    @Subscriber
    public void Method641(EntityViewRenderEvent.FOVModifier fOVModifier) {
        block0: {
            if (!((Boolean) itemFov.getValue()).booleanValue()) break block0;
            fOVModifier.setFOV(((Float) itemFovValue.getValue()).floatValue());
        }
    }

    public static boolean Method394() {
        return (Boolean) cancelEating.getValue() == false;
    }

    @Subscriber
    public void Method642(Class31 class31) {
        block1: {
            if (((Boolean) cancelEating.getValue()).booleanValue()) {
                class31.Cancel();
            }
            if (!((Boolean) customEating.getValue()).booleanValue()) break block1;
            float f = (float) ViewModel.mc.player.getItemInUseCount() - class31.Method214() + 1.0f;
            float f2 = f / (float)class31.Method283().getMaxItemUseDuration();
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)(f2 * (class31.Method284() == EnumHandSide.RIGHT ? -((Float) mainHandZ.getValue()).floatValue() / 100.0f : -((Float) offHandZ.getValue()).floatValue() / 100.0f)));
        }
    }

    public static boolean Method393() {
        return (Boolean) cancelEating.getValue() == false;
    }

    public ViewModel() {
        super("ViewModel", "Lowers your hands", Category.RENDER, "SmallShield");
    }
}
