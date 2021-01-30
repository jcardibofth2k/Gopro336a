package me.darki.konas.module;

import com.viaversion.viafabric.ViaFabric;
import cookiedragon.eventsystem.EventDispatcher;
import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.Collections;
import me.darki.konas.Category;
import me.darki.konas.Class167;
import me.darki.konas.Class184;
import me.darki.konas.Keybind;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;

public abstract class Module {
    public String name;
    public String Field1713 = null;
    public String description = "";
    public Setting<Keybind> Bind = new Setting<>("Bind", new Keybind(0)).setDescription("Sets the module toggle key");
    public Setting<Boolean> holdBind = new Setting<>("Hold", false).Method1191(Module::Method396).setDescription("Only activate while bind is being held down");
    public Category category;
    public ArrayList<String> Field1718 = new ArrayList();
    public boolean enabled;
    public boolean Field1720 = true;
    public static Minecraft mc = Minecraft.getMinecraft();
    public int min = 0;
    public int max = 1000;

    public int Method1627() {
        return this.max;
    }

    public String getName() {
        return this.name;
    }

    public boolean isValidViaFabricVers() {
        if (ViaFabric.getInstance().getVersion() < this.min) {
            return false;
        }
        return ViaFabric.getInstance().getVersion() <= this.max;
    }

    public boolean Method1630() {
        return (Boolean)this.holdBind.getValue();
    }

    public void toggle() {
        if (!this.enabled && this.isValidViaFabricVers()) {
            EventDispatcher.Companion.subscribe(this);
            this.enabled = true;
            this.onEnable();
            Module.Method1636(this);
        } else {
            EventDispatcher.Companion.unsubscribe(this);
            this.enabled = false;
            this.onDisable();
            Module.Method1644(this);
        }
    }

    public Module(String string, String string2, Category category, String ... stringArray) {
        this.name = string;
        this.description = string2;
        this.category = category;
        Collections.addAll(this.Field1718, stringArray);
        EventDispatcher.Companion.register(this);
    }

    public Module(String string, int n, Category category, String ... stringArray) {
        this.name = string;
        ((Keybind)this.Bind.getValue()).Method850(n);
        this.category = category;
        Collections.addAll(this.Field1718, stringArray);
        EventDispatcher.Companion.register(this);
    }

    public boolean Method1632() {
        return Class167.Method1610(Class184.class).Method1651();
    }

    public void Method1633(int n, int n2) {
        this.min = n;
        this.max = n2;
    }

    public void Method1634(boolean bl) {
        this.Field1720 = bl;
    }

    public boolean Method1635() {
        return this.Field1720;
    }

    public static boolean Method396() {
        return false;
    }

    public static void Method1636(Module module) {
        block1: {
            if (Module.mc.player == null || Module.mc.world == null) {
                return;
            }
            if (!((Boolean)Class167.Method1617("Notify", "Modules").getValue()).booleanValue() || !Class167.Method1612("Notify").Method1651()) break block1;
            Logger.Method1120(module.getName() + Command.Field122 + "a enabled");
        }
    }

    public void Method1637(String string, TrayIcon.MessageType messageType) {
        block0: {
            boolean bl;
            Class184 class184 = (Class184)Class167.Method1610(Class184.class);
            if (class184 == null || !class184.Method1651() || !(bl = ((Boolean)Class167.Method1617("Notify", "SystemTray").getValue()).booleanValue())) break block0;
            class184.Method1468(this.getName(), string, messageType);
        }
    }

    public Module(String string, String string2, int n, Category category, String ... stringArray) {
        this.name = string;
        this.description = string2;
        ((Keybind)this.Bind.getValue()).Method850(n);
        this.category = category;
        Collections.addAll(this.Field1718, stringArray);
        EventDispatcher.Companion.register(this);
    }

    public void onEnable() {
    }

    public ArrayList<String> Method1638() {
        return this.Field1718;
    }

    public Category Method1639() {
        return this.category;
    }

    public void Method1640(boolean bl) {
        this.holdBind.setValue(Boolean.valueOf(bl));
    }

    public void Method1641(int n) {
        this.Bind.setValue(new Keybind(n));
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getDescription() {
        return this.description;
    }

    public static void Method1644(Module module) {
        block1: {
            if (Module.mc.player == null || Module.mc.world == null) {
                return;
            }
            if (!((Boolean)Class167.Method1617("Notify", "Modules").getValue()).booleanValue() || !Class167.Method1612("Notify").Method1651()) break block1;
            Logger.Method1120(module.getName() + Command.Field122 + "c disabled");
        }
    }

    public void Method1645(String string) {
        this.Field1713 = string;
    }

    public int Method1646() {
        return ((Keybind)this.Bind.getValue()).Method851();
    }

    public Module(String string, Category category, String ... stringArray) {
        this.name = string;
        this.category = category;
        Collections.addAll(this.Field1718, stringArray);
        EventDispatcher.Companion.register(this);
    }

    public void onDisable() {
    }

    public void Method1647(boolean bl) {
        this.enabled = bl;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void Method1649(ArrayList<String> arrayList) {
        this.Field1718 = arrayList;
    }

    public void Method1650(String string) {
        this.description = string;
    }

    public boolean Method1651() {
        return this.enabled;
    }

    public int Method1652() {
        return this.min;
    }

    public String Method756() {
        return this.Field1713;
    }
}
