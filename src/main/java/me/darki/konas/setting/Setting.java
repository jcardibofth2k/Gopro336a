package me.darki.konas.setting;

import com.viaversion.viafabric.ViaFabric;
import java.util.function.BooleanSupplier;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.Module;

public class Setting<T> {
    public String name;
    public String description = "";
    public T value;
    public T minimum;
    public T maximum;
    public T increment;
    public int minProtocol = 0;
    public int maxProtocol = 1000;
    public BooleanSupplier Field1220 = Setting::Method1186;
    public Setting<ParentSetting> parentSetting = null;

    public T Method1176() {
        return this.increment;
    }

    public Setting<T> Method1177(int n, int n2) {
        this.minProtocol = n;
        this.maxProtocol = n2;
        return this;
    }

    public boolean Method1178() {
        if (ViaFabric.getInstance().getVersion() < this.minProtocol) {
            return false;
        }
        return ViaFabric.getInstance().getVersion() <= this.maxProtocol;
    }

    public void setName(String string) {
        this.name = string;
    }

    public boolean Method1180() {
        if (this.parentSetting != null && !this.parentSetting.getValue().Method1230()) {
            return false;
        }
        if (!this.Method1178()) {
            return false;
        }
        return this.Field1220.getAsBoolean();
    }

    public void setIncrement(T t) {
        this.increment = t;
    }

    public T Method1182() {
        return this.maximum;
    }

    public String Method1183() {
        return this.name;
    }

    public Setting(String string, T value) {
        this.name = string;
        this.value = value;
    }

    public int getMaxProtocol() {
        return this.maxProtocol;
    }

    public T getValue() {
        return this.value;
    }

    public static boolean Method1186() {
        return true;
    }

    public T Method1187() {
        return this.minimum;
    }

    public Setting<T> setDescription(String string) {
        this.description = string;
        return this;
    }

    public boolean hasDescription() {
        return this.description.length() > 0;
    }

    public void setValue(T t) {
        if (this.minimum != null && this.maximum != null) {
            T number = t;
            Number number2 = (Number)this.minimum;
            Number number3 = (Number)this.maximum;
            this.value = number;
        } else {
            this.value = t;
        }
    }

    public Setting(String string, T value, T max, T min, T steps) {
        this.name = string;
        this.value = value;
        this.maximum = max;
        this.minimum = min;
        this.increment = steps;
    }

    public int getMinProtocol() {
        return this.minProtocol;
    }

    public Setting<T> visibleIf(BooleanSupplier booleanSupplier) {
        this.Field1220 = booleanSupplier;
        return this;
    }

    public String Method1192() {
        return this.description;
    }

    public Setting<T> setParentSetting(Setting<ParentSetting> setting) {
        this.parentSetting = setting;
        return this;
    }

    public Module getParentModule() {
        for (Module module : ModuleManager.getModules()) {
            if (ModuleManager.Method1617(module.getName(), this.Method1183()) == null) continue;
            return module;
        }
        return null;
    }

    public int Method1195(String string) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            Enum enum_ = (Enum)this.value.getClass().getEnumConstants()[i];
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            return i;
        }
        return -1;
    }

    public void setEnumValue(String string) {
        if (this.getEnumByString(string) != null) {
            this.value = (T)this.getEnumByString(string);
        }
    }

    public void Method1196(T t) {
        this.minimum = t;
    }

    public void Method1197(T t) {
        this.maximum = t;
    }

    public Enum getEnumByString(String string) {
        for (Enum enum_ : ((Enum)this.value).getClass().getEnumConstants()) {
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            return enum_;
        }
        return null;
    }

    public boolean Method1199() {
        return this.parentSetting != null;
    }

    public Setting<ParentSetting> Method1200() {
        return this.parentSetting;
    }
}