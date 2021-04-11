package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class31;
import me.darki.konas.settingEnums.ViewModelMode;
import me.darki.konas.unremaped.Class659;
import me.darki.konas.module.Module;
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
    public static Setting<Boolean> snapEat = new Setting<>("SnapEat", false).visibleIf(ViewModel::Method394);
    public static Setting<Boolean> customEating = new Setting<>("CustomEating", false).visibleIf(ViewModel::Method393);
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
                if (snapEat.getValue().booleanValue() && (ViewModel.mc.player.getActiveItemStack().getItem() instanceof ItemFood || ViewModel.mc.player.getActiveItemStack().getItem() instanceof ItemPotion)) {
                    if (ViewModel.mc.player.getActiveHand() == EnumHand.MAIN_HAND) {
                        bl2 = false;
                    } else if (ViewModel.mc.player.getActiveHand() == EnumHand.OFF_HAND) {
                        bl = false;
                    }
                }
                if (class659.Method1112() != ViewModelMode.MAINHAND) break block7;
                if (bl2) {
                    class659.Method1103(class659.Method1109() + mainHandX.getValue().floatValue() / 100.0f);
                    class659.Method341(class659.Method258() + mainHandY.getValue().floatValue() / 100.0f);
                    class659.Method1111(class659.Method213() + mainHandZ.getValue().floatValue() / 100.0f);
                }
                class659.Method1104(class659.Method1108() + mainHandXS.getValue().floatValue() / 50.0f);
                class659.Method294(class659.Method215() + mainHandYS.getValue().floatValue() / 50.0f);
                class659.Method1106(class659.Method1110() + mainHandZS.getValue().floatValue() / 50.0f);
                class659.Method344(mainHandYaw.getValue().floatValue());
                class659.Method1102(mainHandPitch.getValue().floatValue());
                class659.Method1105(mainHandRoll.getValue().floatValue());
                break block8;
            }
            if (class659.Method1112() != ViewModelMode.OFFHAND) break block8;
            if (bl) {
                class659.Method1103(class659.Method1109() + offHandX.getValue().floatValue() / 100.0f);
                class659.Method341(class659.Method258() + offHandY.getValue().floatValue() / 100.0f);
                class659.Method1111(class659.Method213() + offHandZ.getValue().floatValue() / 100.0f);
            }
            class659.Method1104(class659.Method1108() + offHandXS.getValue().floatValue() / 50.0f);
            class659.Method294(class659.Method215() + offHandYS.getValue().floatValue() / 50.0f);
            class659.Method1106(class659.Method1110() + offHandZS.getValue().floatValue() / 50.0f);
            class659.Method344(offHandYaw.getValue().floatValue());
            class659.Method1102(offHandPitch.getValue().floatValue());
            class659.Method1105(offHandRoll.getValue().floatValue());
        }
    }

    @Subscriber
    public void Method641(EntityViewRenderEvent.FOVModifier fOVModifier) {
        block0: {
            if (!itemFov.getValue().booleanValue()) break block0;
            fOVModifier.setFOV(itemFovValue.getValue().floatValue());
        }
    }

    public static boolean Method394() {
        return cancelEating.getValue() == false;
    }

    @Subscriber
    public void Method642(Class31 class31) {
        block1: {
            if (cancelEating.getValue().booleanValue()) {
                class31.Cancel();
            }
            if (!customEating.getValue().booleanValue()) break block1;
            float f = (float) ViewModel.mc.player.getItemInUseCount() - class31.Method214() + 1.0f;
            float f2 = f / (float)class31.Method283().getMaxItemUseDuration();
            GlStateManager.translate(0.0f, 0.0f, f2 * (class31.Method284() == EnumHandSide.RIGHT ? -mainHandZ.getValue().floatValue() / 100.0f : -offHandZ.getValue().floatValue() / 100.0f));
        }
    }

    public static boolean Method393() {
        return cancelEating.getValue() == false;
    }

    public ViewModel() {
        super("ViewModel", "Lowers your hands", Category.RENDER, "SmallShield");
    }
}