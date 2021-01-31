package me.darki.konas;

import me.darki.konas.setting.Setting;
import org.jetbrains.annotations.NotNull;

public class IdkWhatThisSettingThingDoes<T>
extends Setting {
    public Class529<T> Field1148 = null;
    public boolean Field1149 = false;

    public void Method1153() {
        this.Field1149 = true;
    }

    @Override
    public void Method1154(String string) {
        block0: {
            super.Method1154(string);
            if (this.Method1198(string) == null) break block0;
            this.Field1148.Method630((T)this.Method1198(string));
        }
    }

    @Override
    public void Method1155(T t) {
        Object t2 = this.getValue();
        super.setValue(t);
        this.Field1148.Method630(t);
        if (this.Field1149) {
            super.setValue(t2);
            this.Field1149 = false;
        }
    }

    public IdkWhatThisSettingThingDoes(String string, T t, T t2, T t3, T t4, @NotNull Class529<T> class529) {
        super(string, t, t2, t3, t4);
        this.Field1148 = class529;
    }

    public IdkWhatThisSettingThingDoes(String string, T t, @NotNull Class529<T> class529) {
        super(string, t);
        this.Field1148 = class529;
    }
}
