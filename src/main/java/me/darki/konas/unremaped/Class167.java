//Fernflower
package me.darki.konas.unremaped;

import com.konasclient.loader.Loader;
import cookiedragon.eventsystem.EventDispatcher;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import me.darki.konas.setting.IdkWhatThisSettingThingDoes;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.launchwrapper.Launch;

public class Class167 {
    public static ArrayList<Module> Field1690 = new ArrayList();
    public static String Field1691;
    public static Class607 Field1692 = new Class607();

    public static void Method1607(String var0, byte[] var1) {
        Object var10000 = null;
        if (var0.startsWith("me.darki.konas")) {
            try {
                Class var2 = Launch.classLoader.loadClass(var0);
                if (!Modifier.isAbstract(var2.getModifiers()) && Module.class.isAssignableFrom(var2)) {
                    Constructor[] var3 = var2.getConstructors();
                    int var4 = var3.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        Constructor var6 = var3[var5];
                        if (var6.getParameterCount() == 0) {
                            Module var7 = (Module)var2.newInstance();
                            Field1690.add(var7);
                        }
                    }
                }
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException var8) {
            }
        }

    }

    public static void Method1608() {
        Object var10000 = null;
        Iterator var0 = Field1690.iterator();

        while(var0.hasNext()) {
            Module var1 = (Module)var0.next();
            if (!var1.isValidViaFabricVers()) {
                if (var1.isEnabled()) {
                    var1.toggle();
                }

                EventDispatcher.Companion.unsubscribe((Object)var1);
            }
        }

    }

    public static void Method1609(ArrayList var0, Module var1) {
        Object var10000 = null;
        if (var1.isEnabled()) {
            var0.add(var1);
        }

    }

    public static Module Method1610(Class<? extends Module> var0) {
        Object var10000 = null;
        Iterator var1 = Field1690.iterator();

        Module var2;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            var2 = (Module)var1.next();
        } while(var2.getClass() != var0);

        return var2;
    }

    public static ArrayList<Module> Method1611() {
        Object var10000 = null;
        ArrayList var0 = new ArrayList();
        Field1690.forEach(Class167::Method1609);
        return var0;
    }

    public static Module Method1612(String var0) {
        Object var10000 = null;
        Iterator var1 = Field1690.iterator();

        while(var1.hasNext()) {
            Module var2 = (Module)var1.next();
            if (var2.getName().equalsIgnoreCase(var0)) {
                return var2;
            }

            Iterator var3 = var2.Method1638().iterator();

            while(var3.hasNext()) {
                String var4 = (String)var3.next();
                if (var4.toLowerCase().startsWith(var0.toLowerCase())) {
                    return var2;
                }
            }
        }

        return null;
    }

    public static ArrayList<Module> Method1613() {
        Object var10000 = null;
        return (ArrayList)Field1690.stream().filter(Class167::Method1621).filter(Class167::Method1614).collect(Collectors.toList());
    }

    public static boolean Method1614(Module var0) {
        Object var10000 = null;
        return var0.Method1635();
    }

    public static ArrayList<Setting> Method1615(Module var0) {
        Object var10000 = null;
        Module var1 = (Module)var0.getClass().getSuperclass().cast(var0);
        ArrayList var2 = new ArrayList();
        Field[] var3 = var1.getClass().getDeclaredFields();
        int var4 = var3.length;

        int var5;
        Field var6;
        for(var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            if (Setting.class.isAssignableFrom(var6.getType())) {
                var6.setAccessible(true);

                try {
                    if (IdkWhatThisSettingThingDoes.class.isAssignableFrom(var6.getType())) {
                        var2.add((IdkWhatThisSettingThingDoes)var6.get(var1));
                    } else {
                        var2.add((Setting)var6.get(var1));
                    }
                } catch (IllegalAccessException var9) {
                    var9.printStackTrace();
                }
            }
        }

        var3 = var1.getClass().getSuperclass().getDeclaredFields();
        var4 = var3.length;

        for(var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            if (Setting.class.isAssignableFrom(var6.getType())) {
                var6.setAccessible(true);

                try {
                    var2.add((Setting)var6.get(var1));
                } catch (IllegalAccessException var8) {
                    var8.printStackTrace();
                }
            }
        }

        return var2;
    }

    public static ArrayList<Module> Method1616(Category var0) {
        Object var10000 = null;
        ArrayList var1 = new ArrayList();
        Field1690.forEach(Class167::Method1618);
        return var1;
    }

    public static Setting Method1617(String var0, String var1) {
        Object var10000 = null;
        if (Method1612(var0) != null) {
            Iterator var2 = Method1615(Method1612(var0)).iterator();

            while(var2.hasNext()) {
                Setting var3 = (Setting)var2.next();
                if (var3.Method1183().equalsIgnoreCase(var1)) {
                    return var3;
                }
            }
        }

        return null;
    }

    public static void Method1618(Category var0, ArrayList var1, Module var2) {
        Object var10000 = null;
        if (var2.getCategory() == var0) {
            var1.add(var2);
        }

    }

    public static ArrayList<Module> Method1619() {
        Object var10000 = null;
        return Field1690;
    }

    public static void Method1620() {
        Object var10000 = null;

        try {
            Field1692.join();
            Field1691 = Field1692.Method203(Field1691);
            boolean var0 = true;
            int var1 = Integer.parseInt(Field1691);
            if (var1 <= 1) {
                var0 = false;
            }

            for(int var2 = 2; (double)var2 <= Math.sqrt((double)var1); ++var2) {
                if (var1 % var2 == 0) {
                    var0 = false;
                    break;
                }
            }

            if (!var0) {
                return;
            }

            EventDispatcher.Companion.dispatch(new Class10());
            Loader.getResourceCache().forEach(Class167::Method1607);
            EventDispatcher.Companion.dispatch(new Class7());
        } catch (Exception var3) {
        }

    }

    public static boolean Method1621(Module var0) {
        Object var10000 = null;
        return var0.isEnabled();
    }
}