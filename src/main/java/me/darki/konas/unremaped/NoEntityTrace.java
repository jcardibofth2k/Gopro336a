package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.misc.MiddleClick;
import me.darki.konas.setting.Setting;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

public class NoEntityTrace
extends Module {
    public Setting<Boolean> pickaxeOnly = new Setting<>("PickaxeOnly", false);
    public Setting<Boolean> swordOnly = new Setting<>("SwordOnly", false);

    @Subscriber
    public void Method584(Class33 class33) {
        if (NoEntityTrace.mc.gameSettings.keyBindPickBlock.isKeyDown() && ModuleManager.getModuleByClass(MiddleClick.class).isEnabled()) {
            return;
        }
        if (!(!((Boolean)this.pickaxeOnly.getValue()).booleanValue() || NoEntityTrace.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemPickaxe || ((Boolean)this.swordOnly.getValue()).booleanValue() && NoEntityTrace.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword)) {
            return;
        }
        class33.setCanceled(true);
    }

    public NoEntityTrace() {
        super("NoEntityTrace", Category.MISC, "NoEntityHit");
    }
}