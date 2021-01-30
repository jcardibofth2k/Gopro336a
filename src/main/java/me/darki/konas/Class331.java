package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import me.darki.konas.module.misc.MiddleClick;
import me.darki.konas.setting.Setting;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

public class Class331
extends Module {
    public Setting<Boolean> Field514 = new Setting<>("PickaxeOnly", false);
    public Setting<Boolean> Field515 = new Setting<>("SwordOnly", false);

    @Subscriber
    public void Method584(Class33 class33) {
        if (Class331.mc.gameSettings.keyBindPickBlock.isKeyDown() && Class167.Method1610(MiddleClick.class).Method1651()) {
            return;
        }
        if (!(!((Boolean)this.Field514.getValue()).booleanValue() || Class331.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemPickaxe || ((Boolean)this.Field515.getValue()).booleanValue() && Class331.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword)) {
            return;
        }
        class33.setCanceled(true);
    }

    public Class331() {
        super("NoEntityTrace", Category.MISC, "NoEntityHit");
    }
}
