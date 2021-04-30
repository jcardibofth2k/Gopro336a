package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.io.PrintStream;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class585
extends Command {
    public Object Field2638;

    public void Method2297(Setting<Enum> setting, String string) {
        if (setting.Method1195(string) != -1) {
            Class585 class585 = this;
            Setting<Enum> setting2 = setting;
            Object t = setting2.getValue();
            Enum enum_ = (Enum)t;
            Class<?> clazz = enum_.getClass();
            ?[] objArray = clazz.getEnumConstants();
            Enum[] enumArray = (Enum[])objArray;
            Setting<Enum> setting3 = setting;
            String string2 = string;
            int n = setting3.Method1195(string2);
            Enum enum_2 = enumArray[n];
            String string3 = enum_2.name();
            class585.Field2638 = string3;
            PrintStream printStream = System.out;
            Object object = this.Field2638;
            try {
                printStream.println(object);
            }
            catch (Exception exception) {
                Enum enum_3 = ((Enum[])((Enum)setting.getValue()).getClass().getEnumConstants())[setting.Method1195(string)];
                Logger.Method1118("Something went wrong lol");
                Logger.Method1118(enum_3.name());
                Logger.Method1118("" + setting.Method1195(string));
            }
        } else {
            Logger.Method1118("Please provide a String as the value!");
        }
    }

    /*
     * Unable to fully structure code
     */
    public void Method2298(Setting<Long> var1_1, String var2_2) {
        block57: {
            v0 = var2_2;
            v1 = Long.parseLong(v0);
            v2 = var1_1;
            v3 = v2.Method1187();
            v4 = (Long)v3;
            v5 = v4;
            if (v1 < v5) ** GOTO lbl38
            v6 = var2_2;
            v7 = Long.parseLong(v6);
            v8 = var1_1;
            v9 = v8.Method1182();
            v10 = (Long)v9;
            v11 = v10;
            if (v7 > v11) ** GOTO lbl38
            v12 = this;
            v13 = var2_2;
            v14 = Long.parseLong(v13);
            v15 = v14;
            v12.Field2638 = v15;
            break block57;
lbl38:
            // 2 sources

            v16 = var2_2;
            v17 = Long.parseLong(v16);
            v18 = var1_1;
            v19 = v18.Method1187();
            v20 = (Long)v19;
            v21 = v20;
            if (v17 >= v21) ** GOTO lbl71
            v22 = v23;
            v24 = v23;
            v22();
            v25 = "The minimum amount is ";
            v26 = v24.append(v25);
            v27 = var1_1;
            v28 = v27.Method1187();
            v29 = v26.append(v28);
            v30 = v29.toString();
            Logger.Method1119(v30);
            break block57;
lbl71:
            // 1 sources

            v31 = var2_2;
            v32 = Long.parseLong(v31);
            v33 = var1_1;
            v34 = v33.Method1182();
            v35 = (Long)v34;
            v36 = v35;
            if (v32 <= v36) break block57;
            v37 = v38;
            v39 = v38;
            v37();
            v40 = "The maximum amount is ";
            v41 = v39.append(v40);
            v42 = var1_1;
            v43 = v42.Method1182();
            v44 = v41.append(v43);
            v45 = v44.toString();
            try {
                Logger.Method1119(v45);
            }
            catch (Exception var3_3) {
                Logger.Method1119("Please provide a Long as the value!");
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void Method2299(Setting<Boolean> var1_1, String var2_2) {
        block16: {
            v0 = var2_2;
            v1 = "toggle";
            v2 = v0.equalsIgnoreCase(v1);
            if (!v2) ** GOTO lbl25
            v3 = this;
            v4 = var1_1;
            v5 = v4.getValue();
            v6 = (Boolean)v5;
            v7 = v6;
            v8 = v7 == false;
            v9 = v8;
            v3.Field2638 = v9;
            break block16;
lbl25:
            // 1 sources

            v10 = this;
            v11 = var2_2;
            v12 = Boolean.parseBoolean(v11);
            v13 = v12;
            try {
                v10.Field2638 = v13;
            }
            catch (Exception var3_3) {
                Logger.Method1119("Please provide a Boolean as the value!");
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void Method2300(Setting<Integer> var1_1, String var2_2) {
        block57: {
            v0 = var2_2;
            v1 = Integer.parseInt(v0);
            v2 = var1_1;
            v3 = v2.Method1187();
            v4 = (Integer)v3;
            v5 = v4;
            if (v1 < v5) ** GOTO lbl38
            v6 = var2_2;
            v7 = Integer.parseInt(v6);
            v8 = var1_1;
            v9 = v8.Method1182();
            v10 = (Integer)v9;
            v11 = v10;
            if (v7 > v11) ** GOTO lbl38
            v12 = this;
            v13 = var2_2;
            v14 = Integer.parseInt(v13);
            v15 = v14;
            v12.Field2638 = v15;
            break block57;
lbl38:
            // 2 sources

            v16 = var2_2;
            v17 = Integer.parseInt(v16);
            v18 = var1_1;
            v19 = v18.Method1187();
            v20 = (Integer)v19;
            v21 = v20;
            if (v17 >= v21) ** GOTO lbl71
            v22 = v23;
            v24 = v23;
            v22();
            v25 = "The minimum amount is ";
            v26 = v24.append(v25);
            v27 = var1_1;
            v28 = v27.Method1187();
            v29 = v26.append(v28);
            v30 = v29.toString();
            Logger.Method1119(v30);
            break block57;
lbl71:
            // 1 sources

            v31 = var2_2;
            v32 = Integer.parseInt(v31);
            v33 = var1_1;
            v34 = v33.Method1182();
            v35 = (Integer)v34;
            v36 = v35;
            if (v32 <= v36) break block57;
            v37 = v38;
            v39 = v38;
            v37();
            v40 = "The maximum amount is ";
            v41 = v39.append(v40);
            v42 = var1_1;
            v43 = v42.Method1182();
            v44 = v41.append(v43);
            v45 = v44.toString();
            try {
                Logger.Method1119(v45);
            }
            catch (Exception var3_3) {
                Logger.Method1119("Please provide an Integer as the value!");
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void Method2301(Setting<Float> var1_1, String var2_2) {
        block69: {
            v0 = var2_2;
            v1 = Float.parseFloat(v0);
            v2 = var1_1;
            v3 = v2.Method1187();
            v4 = (Float)v3;
            v5 = v4.floatValue();
            if (!(v1 >= v5)) ** GOTO lbl56
            v6 = var2_2;
            v7 = Float.parseFloat(v6);
            v8 = var1_1;
            v9 = v8.Method1182();
            v10 = (Float)v9;
            v11 = v10.floatValue();
            if (!(v7 <= v11)) ** GOTO lbl56
            v12 = this;
            v13 = var2_2;
            v14 = Float.parseFloat(v13);
            v15 = Float.valueOf(v14);
            v12.Field2638 = v15;
            v16 = System.out;
            v17 = v18;
            v19 = v18;
            v17();
            v20 = "Value set to ";
            v21 = v19.append(v20);
            v22 = this.Field2638;
            v23 = v21.append(v22);
            v24 = v23.toString();
            v16.println(v24);
            break block69;
lbl56:
            // 2 sources

            v25 = var2_2;
            v26 = Float.parseFloat(v25);
            v27 = var1_1;
            v28 = v27.Method1187();
            v29 = (Float)v28;
            v30 = v29.floatValue();
            if (!(v26 < v30)) ** GOTO lbl89
            v31 = v32;
            v33 = v32;
            v31();
            v34 = "The minimum amount is ";
            v35 = v33.append(v34);
            v36 = var1_1;
            v37 = v36.Method1187();
            v38 = v35.append(v37);
            v39 = v38.toString();
            Logger.Method1119(v39);
            break block69;
lbl89:
            // 1 sources

            v40 = var2_2;
            v41 = Float.parseFloat(v40);
            v42 = var1_1;
            v43 = v42.Method1182();
            v44 = (Float)v43;
            v45 = v44.floatValue();
            if (!(v41 > v45)) break block69;
            v46 = v47;
            v48 = v47;
            v46();
            v49 = "The maximum amount is ";
            v50 = v48.append(v49);
            v51 = var1_1;
            v52 = v51.Method1182();
            v53 = v50.append(v52);
            v54 = v53.toString();
            try {
                Logger.Method1119(v54);
            }
            catch (Exception var3_3) {
                Logger.Method1119("Please provide a Float as the value!");
            }
        }
    }

    public Setting Method2302(Module module, String string) {
        if (ModuleManager.Method1617(module.getName(), string) != null) {
            return ModuleManager.Method1617(module.getName(), string);
        }
        Logger.Method1119("Setting not found");
        return null;
    }

    public void Method2303(Setting<ColorValue> setting, String string) {
        ColorValue colorValue;
        Class585 class585 = this;
        ColorValue class4402 = colorValue;
        ColorValue class4403 = colorValue;
        String string2 = string;
        int n = ColorValue.Method777(string2);
        Setting<ColorValue> setting2 = setting;
        Object t = setting2.getValue();
        ColorValue class4404 = (ColorValue)t;
        boolean bl = class4404.Method783();
        class4402(n, bl);
        try {
            class585.Field2638 = class4403;
        }
        catch (Exception exception) {
            Logger.Method1119("Please provide an integer as the value!");
        }
    }

    public Class585() {
        super("set", "Change the values of settings", new Class605("<module>"), new SyntaxChunk("<setting>"), new SyntaxChunk("<value>"));
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length < this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        Module module = this.Method2304(stringArray[1]);
        if (module == null) {
            Logger.Method1119("Module not found!");
            return;
        }
        Setting setting = this.Method2302(module, stringArray[2]);
        if (setting == null) {
            Logger.Method1119("Setting not found!");
            return;
        }
        if (setting.getValue() instanceof Integer) {
            this.Method2300(setting, stringArray[3]);
        }
        if (setting.getValue() instanceof Long) {
            this.Method2298(setting, stringArray[3]);
        }
        if (setting.getValue() instanceof Float) {
            this.Method2301(setting, stringArray[3]);
        }
        if (setting.getValue() instanceof Double) {
            this.Method2306(setting, stringArray[3]);
        }
        if (setting.getValue() instanceof Boolean) {
            this.Method2299(setting, stringArray[3]);
        }
        if (setting.getValue() instanceof Enum) {
            this.Method2297(setting, stringArray[3]);
        }
        if (setting.getValue() instanceof ColorValue) {
            if (stringArray[3].equalsIgnoreCase("offset")) {
                if (stringArray.length < 5) {
                    Logger.Method1119("Please provide an offset value!");
                    return;
                }
                this.Method2305(setting, stringArray[4]);
            } else {
                this.Method2303(setting, stringArray[3]);
            }
        }
        if (this.Field2638 != null) {
            if (this.Field2638 instanceof String) {
                setting.setEnumValue((String)this.Field2638);
            } else {
                setting.setValue(this.Field2638);
            }
            String string = setting.Method1183();
            if (this.Field2638 instanceof ColorValue) {
                if (stringArray[3].equalsIgnoreCase("offset")) {
                    this.Field2638 = ((ColorValue)this.Field2638).Method776();
                    string = "Offset";
                } else {
                    this.Field2638 = ((ColorValue)this.Field2638).Method774();
                }
            }
            Logger.Method1118("Set " + string + " to " + this.Field2638);
            this.Field2638 = null;
        } else {
            Logger.Method1119("Value is null!");
        }
    }

    public Module Method2304(String string) {
        if (ModuleManager.Method1612(string) != null) {
            return ModuleManager.Method1612(string);
        }
        return null;
    }

    public void Method2305(Setting<ColorValue> setting, String string) {
        ColorValue colorValue;
        Class585 class585 = this;
        ColorValue class4402 = colorValue;
        ColorValue class4403 = colorValue;
        Setting<ColorValue> setting2 = setting;
        Object t = setting2.getValue();
        ColorValue class4404 = (ColorValue)t;
        int n = class4404.Method774();
        Setting<ColorValue> setting3 = setting;
        Object t2 = setting3.getValue();
        ColorValue class4405 = (ColorValue)t2;
        boolean bl = class4405.Method783();
        String string2 = string;
        int n2 = Integer.parseInt(string2);
        class4402(n, bl, n2);
        try {
            class585.Field2638 = class4403;
        }
        catch (Exception exception) {
            Logger.Method1119("Please provide an integer as the offset!");
        }
    }

    /*
     * Unable to fully structure code
     */
    public void Method2306(Setting<Double> var1_1, String var2_2) {
        block57: {
            v0 = var2_2;
            v1 = Double.parseDouble(v0);
            v2 = var1_1;
            v3 = v2.Method1187();
            v4 = (Double)v3;
            v5 = v4;
            if (!(v1 >= v5)) ** GOTO lbl38
            v6 = var2_2;
            v7 = Double.parseDouble(v6);
            v8 = var1_1;
            v9 = v8.Method1182();
            v10 = (Double)v9;
            v11 = v10;
            if (!(v7 <= v11)) ** GOTO lbl38
            v12 = this;
            v13 = var2_2;
            v14 = Double.parseDouble(v13);
            v15 = v14;
            v12.Field2638 = v15;
            break block57;
lbl38:
            // 2 sources

            v16 = var2_2;
            v17 = Double.parseDouble(v16);
            v18 = var1_1;
            v19 = v18.Method1187();
            v20 = (Double)v19;
            v21 = v20;
            if (!(v17 < v21)) ** GOTO lbl71
            v22 = v23;
            v24 = v23;
            v22();
            v25 = "The minimum amount is ";
            v26 = v24.append(v25);
            v27 = var1_1;
            v28 = v27.Method1187();
            v29 = v26.append(v28);
            v30 = v29.toString();
            Logger.Method1119(v30);
            break block57;
lbl71:
            // 1 sources

            v31 = var2_2;
            v32 = Double.parseDouble(v31);
            v33 = var1_1;
            v34 = v33.Method1182();
            v35 = (Double)v34;
            v36 = v35;
            if (!(v32 > v36)) break block57;
            v37 = v38;
            v39 = v38;
            v37();
            v40 = "The maximum amount is ";
            v41 = v39.append(v40);
            v42 = var1_1;
            v43 = v42.Method1182();
            v44 = v41.append(v43);
            v45 = v44.toString();
            try {
                Logger.Method1119(v45);
            }
            catch (Exception var3_3) {
                Logger.Method1119("Please provide a Double as the value!");
            }
        }
    }
}