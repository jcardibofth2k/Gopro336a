package me.darki.konas.setting;

import com.viaversion.viafabric.ViaFabric;
import java.util.function.BooleanSupplier;
import me.darki.konas.Class167;
import me.darki.konas.ParentSetting;
import me.darki.konas.module.Module;

public class Setting<T> {
    public String Field1212;
    public String description = "";
    public T value;
    public T minimum;
    public T maximum;
    public T increment;
    public int Field1218 = 0;
    public int Field1219 = 1000;
    public BooleanSupplier Field1220 = Setting::Method1186;
    public Setting<ParentSetting> parentSetting = null;

    public T Method1176() {
        return this.increment;
    }

    public Setting<T> Method1177(int n, int n2) {
        this.Field1218 = n;
        this.Field1219 = n2;
        return this;
    }

    public boolean Method1178() {
        if (ViaFabric.getInstance().getVersion() < this.Field1218) {
            return false;
        }
        return ViaFabric.getInstance().getVersion() <= this.Field1219;
    }

    public void Method1179(String string) {
        this.Field1212 = string;
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

    public void Method1181(T t) {
        this.increment = t;
    }

    public T Method1182() {
        return this.maximum;
    }

    public String Method1183() {
        return this.Field1212;
    }

    public Setting(String string, T t) {
        this.Field1212 = string;
        this.value = t;
    }

    public int Method1184() {
        return this.Field1219;
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

    public Setting(String string, T t, T t2, T t3, T t4) {
        this.Field1212 = string;
        this.value = t;
        this.maximum = t2;
        this.minimum = t3;
        this.increment = t4;
    }

    public int Method1190() {
        return this.Field1218;
    }

    public Setting<T> Method1191(BooleanSupplier booleanSupplier) {
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

    public Module Method1194() {
        for (Module module : Class167.Method1619()) {
            if (Class167.Method1617(module.getName(), this.Method1183()) == null) continue;
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

    public void Method1154(String string) {
        if (this.Method1198(string) != null) {
            this.value = (T)this.Method1198(string);
        }
    }

    public void Method1196(T t) {
        this.minimum = t;
    }

    public void Method1197(T t) {
        this.maximum = t;
    }

    public Enum Method1198(String string) {
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
