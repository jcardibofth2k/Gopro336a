package me.darki.konas.setting;
import org.jetbrains.annotations.NotNull;

public class ListenableSettingDecorator<T>
extends Setting {
    public IRunnable run = null;
    public boolean cancelled = false;

    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public void setEnumValue(String value) {
      super.setEnumValue(value);
        if(getEnumByString(value) != null) {
            run.run((T) getEnumByString(value));
        }
    }

    @Override
    public void setValue(Object value) {
        T object = getValue();
        super.setValue(value);
        run.run(value);
        if(cancelled) {
            super.setValue(object);
            cancelled = false;
        }
    }

    public ListenableSettingDecorator(String string, T t, T t2, T t3, T t4, @NotNull IRunnable run) {
        super(string, t, t2, t3, t4);
        this.run = run;
    }

    public ListenableSettingDecorator(String string, T t, @NotNull IRunnable run) {
        super(string, t);
        this.run = run;
    }
}