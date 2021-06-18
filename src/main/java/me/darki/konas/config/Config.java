package me.darki.konas.config;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.frame.CategoryFrame;
import me.darki.konas.gui.clickgui.frame.Frame;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class180;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class88;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.misc.BlockAura;
import me.darki.konas.module.misc.Spammer;
import me.darki.konas.module.render.Hud;
import me.darki.konas.module.render.NameTags;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.KonasMod;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.module.misc.Announcer;
import me.darki.konas.module.misc.AutoGG;
import me.darki.konas.unremaped.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import me.darki.konas.gui.hud.Element;
import java.util.function.Consumer;
import com.google.gson.Gson;
import me.darki.konas.setting.Setting;
import net.minecraft.client.settings.GameSettings;
import java.util.Comparator;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.io.PrintStream;
import java.io.IOException;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import me.darki.konas.command.commands.fontCommand;
import me.darki.konas.command.Command;
import com.google.gson.JsonObject;
import java.util.Iterator;
import com.google.gson.JsonElement;
import me.darki.konas.module.Module;
import com.google.gson.JsonArray;
import java.io.File;
import net.minecraft.client.Minecraft;

public class Config
{
    //this class is so fucked bro
    //obf stripped so much data :(
    public static Minecraft mc = Minecraft.getMinecraft();
    
    public static File KONAS_FOLDER = new File(mc.mcDataDir, "Konas");
    public static File CONFIG = new File(KONAS_FOLDER, "config.json");
    public static File CONFIGS = new File(KONAS_FOLDER, "configs");
    public static File ACCOUNTS = new File(KONAS_FOLDER, "accounts.json");
    public static File currentConfig;
    @Deprecated
    public static File oldConfigs = new File(mc.mcDataDir + File.separator + "KonasConfig.json");
    @Deprecated
    public static File oldAccounts = new File(mc.mcDataDir + File.separator + "accounts.json");;
    
    public static JsonArray Method2217() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator<Module> iterator = ModuleManager.getModules().iterator();
        while (iterator.hasNext()) {
            jsonArray.add((JsonElement)Method2226(iterator.next()));
        }
        return jsonArray;
    }
    
    public static JsonArray Method2218() {
        final JsonArray jsonArray = new JsonArray();
        for (final Frame class90 : KonasGlobals.INSTANCE.Field1130.Method119()) {
            if (class90 instanceof Class88) {
                continue;
            }
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("X", (Number)class90.getPosX());
            jsonObject.addProperty("Y", (Number)class90.Method921());
            jsonObject.addProperty("Extended", class90.isExtended());
            final JsonObject jsonObject2 = new JsonObject();
            jsonObject2.add(class90.Method920(), (JsonElement)jsonObject);
            jsonArray.add((JsonElement)jsonObject2);
        }
        return jsonArray;
    }
    
    public static void Method2219(final File file) {
        Label_0847: {
            PrintStream err;
            String x;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                final JsonArray jsonArray = new JsonArray();
                final JsonObject jsonObject = new JsonObject();
                jsonObject.add("Modules", (JsonElement)Method2217());
                jsonArray.add((JsonElement)jsonObject);
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.add("Containers", (JsonElement)Method2273());
                jsonArray.add((JsonElement)jsonObject2);
                final JsonObject jsonObject3 = new JsonObject();
                jsonObject3.add("Panels", (JsonElement)Method2218());
                jsonArray.add((JsonElement)jsonObject3);
                final JsonObject jsonObject4 = new JsonObject();
                jsonObject4.add("Friends", (JsonElement)Method2245());
                jsonArray.add((JsonElement)jsonObject4);
                final JsonObject jsonObject5 = new JsonObject();
                jsonObject5.addProperty("Prefix", Command.Method190());
                jsonArray.add((JsonElement)jsonObject5);
                final JsonObject jsonObject6 = new JsonObject();
                jsonObject6.addProperty("Language", Class332.Field435);
                jsonArray.add((JsonElement)jsonObject6);
                final JsonObject jsonObject7 = new JsonObject();
                jsonObject7.add("Macros", (JsonElement)Method2249());
                jsonArray.add((JsonElement)jsonObject7);
                final JsonObject jsonObject8 = new JsonObject();
                jsonObject8.add("Waypoints", (JsonElement)Method2257());
                jsonArray.add((JsonElement)jsonObject8);
                jsonArray.add(fontCommand.Field1351);
                final JsonObject jsonObject9 = new JsonObject();
                jsonObject9.add("Muted", (JsonElement)Method2275());
                jsonArray.add((JsonElement)jsonObject9);
                final JsonObject jsonObject10 = new JsonObject();
                jsonObject10.addProperty("Watermark", KonasMod.NAME);
                jsonArray.add((JsonElement)jsonObject10);
                final FileWriter fileWriter = new FileWriter(file);
                new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)jsonArray, (Appendable)fileWriter);
                fileWriter.close();
                break Label_0847;
            }
            catch (IOException ex) {
                err = System.err;
                x = "Cant write to config file!";
            }
            err.println(x);
        }
        Method2232();
    }
    
    public static List<File> Method2220() {
        if (!Config.KONAS_FOLDER.exists() || Config.KONAS_FOLDER.listFiles() == null) {
            return null;
        }
        if (Config.CONFIGS.listFiles() != null) {
            return Arrays.stream(Config.CONFIGS.listFiles()).filter(Config::Method2235).collect(Collectors.toList());
        }
        return null;
    }
    
    public static void Method2221() {
        IOException ex;
        try {
            final File parent = new File(Config.KONAS_FOLDER, "extrachat");
            final File file = new File(parent, "taunts.txt");
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                final Iterator<String> iterator = ExtraChat.Field1463.iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(iterator.next() + "\n");
                }
                bufferedWriter.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final ArrayList<String> c = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    c.add(line);
                }
            }
            ExtraChat.Field1463.clear();
            ExtraChat.Field1463.addAll(c);
            return;
        }
        catch (IOException ex2) {
            ex = ex2;
        }
        ex.printStackTrace();
    }
    
    public static void Method2222(final JsonElement jsonElement) {
        PrintStream err;
        String message;
        try {
            Method2258(jsonElement.getAsJsonObject());
            return;
        }
        catch (NullPointerException ex) {
            err = System.err;
            message = ex.getMessage();
        }
        err.println(message);
    }
    
    public static File Method2223() {
        if (!Config.KONAS_FOLDER.exists() || Config.KONAS_FOLDER.listFiles() == null) {
            Config.KONAS_FOLDER.mkdir();
            return Config.CONFIG;
        }
        if (Config.CONFIGS.listFiles() != null) {
            final List<? super File> list = Arrays.stream(Config.CONFIGS.listFiles()).filter(Config::Method2246).collect(Collectors.toList());
            list.add(Config.CONFIG);
            return list.stream().max(Comparator.comparingLong(File::lastModified)).orElse(Config.CONFIG);
        }
        return Config.CONFIG;
    }
    
    public static void Method2224(final Component class183) {
        if (class183 instanceof Class174) {
            ((Class174)class183).Method1510();
        }
    }
    
    public static void Method2225(final JsonObject jsonObject) {
        if (jsonObject.get("Name") != null && jsonObject.get("UUID") != null) {
            Class492.Method1990(jsonObject.get("Name").getAsString(), jsonObject.get("UUID").getAsString());
        }
    }
    
    public static JsonObject Method2226(final Module module) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Bind", GameSettings.getKeyDisplayString(module.Method1646()));
        jsonObject.addProperty("Enabled", Boolean.valueOf(module.isEnabled()));
        jsonObject.addProperty("Visible", Boolean.valueOf(module.isVisible()));
        if (ModuleManager.Method1615(module) != null) {
            for (final Setting setting : ModuleManager.Method1615(module)) {
                if (setting.getValue() instanceof Number) {
                    jsonObject.addProperty(setting.Method1183(), (Number)setting.getValue());
                }
                else if (setting.getValue() instanceof Boolean) {
                    jsonObject.addProperty(setting.Method1183(), (Boolean)setting.getValue());
                }
                else if (setting.getValue() instanceof ParentSetting) {
                    jsonObject.addProperty(setting.Method1183(), Boolean.valueOf(((ParentSetting)setting.getValue()).Method1230()));
                }
                else if (setting.getValue() instanceof Class537) {
                    jsonObject.addProperty(setting.Method1183(), (Number)((Class537)setting.getValue()).Method851());
                }
                else if (setting.getValue() instanceof Enum || setting.getValue() instanceof String) {
                    jsonObject.addProperty(setting.Method1183(), String.valueOf(setting.getValue()));
                }
                else if (setting.getValue() instanceof ColorValue) {
                    final JsonArray jsonArray = new JsonArray();
                    jsonArray.add((Number)((ColorValue)setting.getValue()).Method778());
                    jsonArray.add(Boolean.valueOf(((ColorValue)setting.getValue()).Method783()));
                    jsonArray.add((Number)((ColorValue)setting.getValue()).Method776());
                    jsonObject.add(setting.Method1183(), (JsonElement)jsonArray);
                }
                else if (setting.getValue() instanceof Class443) {
                    final JsonArray jsonArray2 = new JsonArray();
                    final Iterator<String> iterator2 = ((Class443)setting.getValue()).Method684().iterator();
                    while (iterator2.hasNext()) {
                        jsonArray2.add((String)iterator2.next());
                    }
                    jsonObject.add(setting.Method1183(), (JsonElement)jsonArray2);
                }
                else if (setting.getValue() instanceof Class531) {
                    final JsonArray jsonArray3 = new JsonArray();
                    final Iterator<String> iterator3 = ((Class531)setting.getValue()).Method1146().iterator();
                    while (iterator3.hasNext()) {
                        jsonArray3.add((String)iterator3.next());
                    }
                    jsonObject.add(setting.Method1183(), (JsonElement)jsonArray3);
                }
                else {
                    if (!(setting.getValue() instanceof Class526)) {
                        continue;
                    }
                    final JsonArray jsonArray4 = new JsonArray();
                    final Iterator<String> iterator4 = ((Class526)setting.getValue()).Method1225().iterator();
                    while (iterator4.hasNext()) {
                        jsonArray4.add((String)iterator4.next());
                    }
                    jsonObject.add(setting.Method1183(), (JsonElement)jsonArray4);
                }
            }
        }
        final JsonObject jsonObject2 = new JsonObject();
        jsonObject2.add(module.getName(), (JsonElement)jsonObject);
        return jsonObject2;
    }
    
    public static void Method2227(final JsonElement jsonElement) {
        PrintStream err;
        String message;
        try {
            Method2259(jsonElement.getAsJsonObject());
            return;
        }
        catch (NullPointerException ex) {
            err = System.err;
            message = ex.getMessage();
        }
        err.println(message);
    }
    
    public static JsonObject Method2228(final Class469 class469) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Name", class469.Method2205());
        jsonObject.addProperty("UUID", class469.Method2206());
        return jsonObject;
    }
    
    public static void Method2229(final JsonElement jsonElement) {
        PrintStream err;
        String message;
        try {
            Method2250(jsonElement.getAsJsonObject());
            return;
        }
        catch (NullPointerException ex) {
            err = System.err;
            message = ex.getMessage();
        }
        err.println(message);
    }
    
    public static void Method2230() {
        PrintStream err;
        String x;
        try {
            final File file = new File(Config.mc.mcDataDir + File.separator + "Konas" + File.separator + "announcer");
            final File file2 = new File(Config.mc.mcDataDir + File.separator + "Konas" + File.separator + "announcer" + File.separator + "welcome.txt");
            final File file3 = new File(Config.mc.mcDataDir + File.separator + "Konas" + File.separator + "announcer" + File.separator + "goodbye.txt");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file2.exists()) {
                file2.createNewFile();
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
                final Iterator<String> iterator = Announcer.arrayList.iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(iterator.next() + "\n");
                }
                bufferedWriter.close();
            }
            if (!file3.exists()) {
                file3.createNewFile();
                final BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file3));
                final Iterator<String> iterator2 = Announcer.arrayList1.iterator();
                while (iterator2.hasNext()) {
                    bufferedWriter2.write(iterator2.next() + "\n");
                }
                bufferedWriter2.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
            final ArrayList<String> c = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    c.add(line);
                }
            }
            Announcer.arrayList.clear();
            Announcer.arrayList.addAll(c);
            final BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file3));
            final ArrayList<String> c2 = new ArrayList<String>();
            String line2;
            while ((line2 = bufferedReader2.readLine()) != null) {
                if (!line2.equals("")) {
                    c2.add(line2);
                }
            }
            Announcer.arrayList1.clear();
            Announcer.arrayList1.addAll(c2);
            return;
        }
        catch (IOException ex) {
            err = System.err;
            x = "Cant load AutoGG Files!";
        }
        err.println(x);
    }
    
    public static void Method2231(final JsonObject jsonObject) {
        Class561 class561 = null;
        for (final Class561 class562 : Class561.class.getEnumConstants()) {
            if (class562.name().equalsIgnoreCase(jsonObject.get("Type").getAsString())) {
                class561 = class562;
            }
        }
        KonasGlobals.INSTANCE.Field1138.Method760(new Class559(jsonObject.get("Name").getAsString(), jsonObject.get("X").getAsDouble(), jsonObject.get("Y").getAsDouble(), jsonObject.get("Z").getAsDouble(), jsonObject.get("Dimension").getAsInt(), jsonObject.get("Server").getAsString(), class561));
    }
    
    public static void Method2232() {
        PrintStream err;
        String x;
        try {
            if (!Config.ACCOUNTS.exists()) {
                Config.ACCOUNTS.createNewFile();
            }
            final Gson create = new GsonBuilder().setPrettyPrinting().create();
            final FileWriter fileWriter = new FileWriter(Config.ACCOUNTS);
            final JsonArray jsonArray = new JsonArray();
            final Iterator<Class170> iterator = (Iterator<Class170>) KonasGlobals.INSTANCE.Field1132.Field1739.iterator();
            while (iterator.hasNext()) {
                final Class68 field1542 = iterator.next().Field1542;
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Name", field1542.Method309());
                jsonObject.addProperty("E-Mail", field1542.Method303());
                jsonObject.addProperty("Password", field1542.Method317());
                jsonObject.addProperty("UUID", field1542.Method305());
                jsonObject.addProperty("Token", field1542.Method312());
                jsonObject.addProperty("Last-Login", (Number)field1542.Method307());
                jsonObject.addProperty("Microsoft", Boolean.valueOf(field1542.Method302()));
                jsonArray.add((JsonElement)jsonObject);
            }
            create.toJson((JsonElement)jsonArray, (Appendable)fileWriter);
            fileWriter.close();
            return;
        }
        catch (IOException ex) {
            err = System.err;
            x = "Cant write to account file";
        }
        err.println(x);
    }
    
    public static void Method2233(final JsonElement jsonElement) {
        Method2225(jsonElement.getAsJsonObject());
    }
    
    public static void Method2234(final Setting setting, final JsonElement jsonElement) {
        ((Class443)setting.getValue()).Method681(jsonElement.getAsString());
    }
    
    public static boolean Method2235(final File file) {
        return file.getName().endsWith(".json");
    }
    
    public static void Method2236(final JsonObject jsonObject) {
        try {
            Class332.Field435 = jsonObject.get("Language").getAsString();
        }
        catch (NullPointerException ex) {}
    }
    
    public static boolean Method2237(final JsonObject jsonObject, final Frame class90) {
        return jsonObject.getAsJsonObject(class90.Method920()) != null;
    }
    
    public static void Method2238(final Component class183) {
        if (class183 instanceof Class180) {
            ((Class180)class183).Method672().forEach(Config::Method2255);
        }
    }
    
    public static void Method2239(final JsonElement jsonElement) {
        PrintStream err;
        String message;
        try {
            Method2231(jsonElement.getAsJsonObject());
            return;
        }
        catch (NullPointerException ex) {
            err = System.err;
            message = ex.getMessage();
        }
        err.println(message);
    }
    
    public static void Method2240(final Setting setting, final JsonElement jsonElement) {
        ((Class443)setting.getValue()).Method681(jsonElement.getAsString());
    }
    
    public static void Method2241(final Module module, final File file) {
        final JsonArray method2256 = Method2256(file);
        JsonArray asJsonArray;
        try {
            asJsonArray = ((JsonObject)method2256.get(0)).getAsJsonArray("Modules");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Module Array not found!");
            return;
        }
        int n = -1;
        for (int i = 0; i < asJsonArray.size(); ++i) {
            if (asJsonArray.get(i).getAsJsonObject().getAsJsonObject(module.getName()) != null) {
                n = i;
            }
        }
        if (n != -1) {
            asJsonArray.set(n, (JsonElement)Method2226(module));
            final JsonObject jsonObject = new JsonObject();
            jsonObject.add("Modules", (JsonElement)asJsonArray);
            method2256.set(0, (JsonElement)jsonObject);
            Method2261(method2256, file);
        }
    }
    
    static {
        Config.mc = Minecraft.getMinecraft();
        Config.KONAS_FOLDER = new File(Config.mc.mcDataDir, "Konas");
        Config.CONFIG = new File(Config.KONAS_FOLDER, "config.json");
        Config.CONFIGS = new File(Config.KONAS_FOLDER, "configs");
        Config.ACCOUNTS = new File(Config.KONAS_FOLDER, "accounts.json");
        Config.currentConfig = null;
        Config.oldConfigs = new File(Config.mc.mcDataDir + File.separator + "KonasConfig.json");
        Config.oldAccounts = new File(Config.mc.mcDataDir + File.separator + "accounts.json");
    }
    
    public static void Method2242(final JsonObject jsonObject) {
        try {
            Command.Method196(jsonObject.get("Prefix").getAsString());
        }
        catch (NullPointerException ex) {}
    }
    
    public static void Method2243(final JsonObject jsonObject) {
        try {
            KonasMod.NAME = jsonObject.get("Watermark").getAsString();
        }
        catch (NullPointerException ex) {}
    }
    
    public static boolean Method2244(final File file) {
        return file.delete();
    }
    
    public static JsonArray Method2245() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator<Class469> iterator = Class492.Method1993().iterator();
        while (iterator.hasNext()) {
            jsonArray.add((JsonElement)Method2228(iterator.next()));
        }
        return jsonArray;
    }
    
    public static boolean Method2246(final File file) {
        return file.getName().endsWith(".json");
    }
    
    public static void Method2247(final File file) {
        PrintStream err;
        String x;
        try {
            final JsonArray method2256 = Method2256(file);
            JsonArray asJsonArray;
            try {
                asJsonArray = ((JsonObject)method2256.get(3)).getAsJsonArray("Friends");
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Friends Array not found!");
                return;
            }
            if (me.darki.konas.module.client.Config.overwriteFriends.getValue()) {
                Class492.Method1986();
            }
            final Iterator<JsonElement> iterator = asJsonArray.iterator();
            while (iterator.hasNext()) {
                Method2225(iterator.next().getAsJsonObject());
            }
            return;
        }
        catch (Exception ex2) {
            err = System.err;
            x = "Error while loading friends";
        }
        err.println(x);
    }
    
    public static boolean Method2248(final JsonObject jsonObject, final Module module) {
        return jsonObject.getAsJsonObject(module.getName()) != null;
    }
    
    public static JsonArray Method2249() {
        final JsonArray jsonArray = new JsonArray();
        for (final Class152 class152 : Class157.Method1704()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", class152.Method1760());
            jsonObject.addProperty("Bind", GameSettings.getKeyDisplayString(class152.Method1757()));
            jsonObject.addProperty("Text", class152.Method1756());
            jsonArray.add((JsonElement)jsonObject);
        }
        return jsonArray;
    }
    
    public static void Method2250(final JsonObject jsonObject) throws NullPointerException {
        final Element element = KonasGlobals.INSTANCE.Field1136.Method2196().stream().filter(Config::Method2269).findFirst().orElse(null);
        if (element != null) {
            final JsonObject asJsonObject = jsonObject.getAsJsonObject(element.Method2316());
            try {
                element.Method2325(asJsonObject.getAsJsonPrimitive("Enabled").getAsBoolean());
                final int asInt = asJsonObject.getAsJsonPrimitive("A").getAsInt();
                final float asFloat = asJsonObject.getAsJsonPrimitive("X").getAsFloat();
                final float asFloat2 = asJsonObject.getAsJsonPrimitive("Y").getAsFloat();
                element.Method2334(asInt);
                element.Method2326(asFloat);
                element.Method2331(asFloat2);
                if (Class109.Method2198(element).length > 0) {
                    for (final Setting setting : Class109.Method2198(element)) {
                        if (setting.getValue() instanceof Float) {
                            setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsFloat());
                        }
                        else if (setting.getValue() instanceof Double) {
                            setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsDouble());
                        }
                        else if (setting.getValue() instanceof Integer) {
                            setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsInt());
                        }
                        else if (setting.getValue() instanceof Boolean) {
                            setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsBoolean());
                        }
                        else if (setting.getValue() instanceof Enum) {
                            setting.setEnumValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsString());
                        }
                        else if (setting.getValue() instanceof String) {
                            setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsString());
                        }
                        else if (setting.getValue() instanceof ColorValue) {
                            final JsonArray asJsonArray = asJsonObject.getAsJsonArray(setting.Method1183());
                            ((ColorValue)setting.getValue()).Method771(asJsonArray.get(0).getAsInt());
                            ((ColorValue)setting.getValue()).Method780(asJsonArray.get(1).getAsBoolean());
                        }
                        else if (setting.getValue() instanceof Class443) {
                            asJsonObject.getAsJsonArray(setting.Method1183()).forEach(Config::Method2240);
                            ((Class443)setting.getValue()).Method680();
                        }
                        else {
                            if (!(setting.getValue() instanceof Class531)) {
                                continue;
                            }
                            asJsonObject.getAsJsonArray(setting.Method1183()).forEach(Config::Method2271);
                        }
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new NullPointerException("Error loading container: " + element.Method2316());
            }
            return;
        }
        throw new NullPointerException("Couldn't find container");
    }
    
    public static void Method2251() {
        IOException ex;
        try {
            final File parent = new File(Config.KONAS_FOLDER, "blockaura");
            final File file = new File(parent, "sign.txt");
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                final Iterator<String> iterator = BlockAura.Field1780.iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(iterator.next() + "\n");
                }
                bufferedWriter.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final ArrayList<String> c = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    c.add(line);
                }
            }
            BlockAura.Field1780.clear();
            BlockAura.Field1780.addAll(c);
            if (c.size() < 4) {
                for (int i = 0; i <= 4 - c.size(); ++i) {
                    BlockAura.Field1780.add("");
                }
            }
            return;
        }
        catch (IOException ex2) {
            ex = ex2;
        }
        ex.printStackTrace();
    }
    
    public static void Method2252(final File file, final File file2) {
        if (file.exists()) {
            if (file2.exists()) {
                file.delete();
            }
            else {
                IOException ex;
                try {
                    Files.move(file.toPath(), file2.toPath(), new CopyOption[0]);
                    return;
                }
                catch (IOException ex2) {
                    ex = ex2;
                }
                ex.printStackTrace();
            }
        }
    }
    
    public static void Method2253(final JsonObject jsonObject) throws NullPointerException {
        final Module module = ModuleManager.getModules().stream().filter(Config::Method2248).findFirst().orElse(null);
        if (module != null) {
            final JsonObject asJsonObject = jsonObject.getAsJsonObject(module.getName());
            try {
                module.setBind(Method2262(asJsonObject.getAsJsonPrimitive("Bind").getAsString()));
                if (module.isEnabled() != asJsonObject.getAsJsonPrimitive("Enabled").getAsBoolean()) {
                    module.toggle();
                }
                module.setVisible(asJsonObject.getAsJsonPrimitive("Visible").getAsBoolean());
                if (!ModuleManager.Method1615(module).isEmpty()) {
                    for (final Setting setting : ModuleManager.Method1615(module)) {
                        try {
                            if (setting.getValue() instanceof Float) {
                                setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsFloat());
                            }
                            else if (setting.getValue() instanceof Double) {
                                setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsDouble());
                            }
                            else if (setting.getValue() instanceof Integer) {
                                setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsInt());
                            }
                            else if (setting.getValue() instanceof Boolean) {
                                setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsBoolean());
                            }
                            else if (setting.getValue() instanceof ParentSetting) {
                                ((ParentSetting)setting.getValue()).Method1231(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsBoolean());
                            }
                            else if (setting.getValue() instanceof Enum) {
                                setting.setEnumValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsString());
                            }
                            else if (setting.getValue() instanceof Class537) {
                                setting.setValue(new Class537(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsInt()));
                            }
                            else if (setting.getValue() instanceof String) {
                                setting.setValue(asJsonObject.getAsJsonPrimitive(setting.Method1183()).getAsString());
                            }
                            else if (setting.getValue() instanceof ColorValue) {
                                final JsonArray asJsonArray = asJsonObject.getAsJsonArray(setting.Method1183());
                                ((ColorValue)setting.getValue()).Method771(asJsonArray.get(0).getAsInt());
                                ((ColorValue)setting.getValue()).Method780(asJsonArray.get(1).getAsBoolean());
                                ((ColorValue)setting.getValue()).Method781(asJsonArray.get(2).getAsInt());
                            }
                            else if (setting.getValue() instanceof Class443) {
                                asJsonObject.getAsJsonArray(setting.Method1183()).forEach(Config::Method2234);
                                ((Class443)setting.getValue()).Method680();
                            }
                            else if (setting.getValue() instanceof Class531) {
                                asJsonObject.getAsJsonArray(setting.Method1183()).forEach(Config::Method2254);
                            }
                            else {
                                if (!(setting.getValue() instanceof Class526)) {
                                    continue;
                                }
                                asJsonObject.getAsJsonArray(setting.Method1183()).forEach(Config::Method2260);
                            }
                        }
                        catch (Exception ex) {}
                    }
                }
            }
            catch (Exception ex2) {
                throw new NullPointerException("Error loading module: " + module.getName());
            }
            return;
        }
        throw new NullPointerException("Couldn't find module");
    }
    
    public static void Method2254(final Setting setting, final JsonElement jsonElement) {
        ((Class531)setting.getValue()).Method1147(jsonElement.getAsString());
    }
    
    public static void Method2255(final Component class183) {
        if (class183 instanceof Class174) {
            ((Class174)class183).Method1510();
        }
    }
    
    public static JsonArray Method2256(final File file) {
        try {
            final FileReader fileReader = new FileReader(file);
            final JsonArray jsonArray = (JsonArray)new JsonParser().parse((Reader)fileReader);
            fileReader.close();
            return jsonArray;
        }
        catch (Exception ex) {
            System.err.println("Couldn't load config");
            return null;
        }
    }
    
    public static JsonArray Method2257() {
        final JsonArray jsonArray = new JsonArray();
        for (final Class559 class559 : KonasGlobals.INSTANCE.Field1138.Method758()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", class559.Method819());
            jsonObject.addProperty("X", (Number)class559.Method821());
            jsonObject.addProperty("Y", (Number)class559.Method820());
            jsonObject.addProperty("Z", (Number)class559.Method818());
            jsonObject.addProperty("Dimension", (Number)class559.Method814());
            jsonObject.addProperty("Server", class559.Method813());
            jsonObject.addProperty("Type", class559.Method815().toString());
            jsonArray.add((JsonElement)jsonObject);
        }
        return jsonArray;
    }
    
    public static void Method2258(final JsonObject jsonObject) {
        Class157.Method1705(new Class152(jsonObject.get("Name").getAsString(), jsonObject.get("Text").getAsString(), Keyboard.getKeyIndex(jsonObject.get("Bind").getAsString())));
    }
    
    public static void Method2259(final JsonObject jsonObject) throws NullPointerException {
        final Frame class90 = KonasGlobals.INSTANCE.Field1130.Method119().stream().filter(Config::Method2237).findFirst().orElse(null);
        if (class90 instanceof Class88) {
            return;
        }
        if (class90 != null) {
            final JsonObject asJsonObject = jsonObject.getAsJsonObject(class90.Method920());
            try {
                class90.setPosX((float)asJsonObject.getAsJsonPrimitive("X").getAsInt());
                class90.Method442((float)asJsonObject.getAsJsonPrimitive("Y").getAsInt());
                class90.setExtended(asJsonObject.getAsJsonPrimitive("Extended").getAsBoolean());
            }
            catch (Exception ex) {
                throw new NullPointerException("Error parsing frame: " + class90.Method920());
            }
            return;
        }
        throw new NullPointerException("Frame not found");
    }
    
    public static void Method2260(final Setting setting, final JsonElement jsonElement) {
        ((Class526)setting.getValue()).Method1227(jsonElement.getAsString());
    }
    
    public static void Method2261(final JsonArray jsonArray, final File file) {
        PrintStream err;
        String x;
        try {
            final FileWriter fileWriter = new FileWriter(file);
            new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)jsonArray, (Appendable)fileWriter);
            fileWriter.close();
            return;
        }
        catch (Exception ex) {
            err = System.err;
            x = "Cant write to config file!";
        }
        err.println(x);
    }
    
    public static int Method2262(final String s) {
        final String format = I18n.format("key.mouse.left", new Object[0]);
        final String format2 = I18n.format("key.mouse.right", new Object[0]);
        final String format3 = I18n.format("key.mouse.middle", new Object[0]);
        if (s.equalsIgnoreCase(format)) {
            return -100;
        }
        if (s.equalsIgnoreCase(format2)) {
            return -99;
        }
        if (s.equalsIgnoreCase(format3)) {
            return -98;
        }
        for (int i = 0; i < 15; ++i) {
            if (s.equalsIgnoreCase(I18n.format("key.mouseButton", new Object[] { i + 4 }))) {
                return i + 4 - 101;
            }
        }
        return Keyboard.getKeyIndex(s);
    }
    
    public static void Method2263(final JsonObject jsonObject) {
        Class484.Method2067(jsonObject.get("Name").getAsString());
    }
    
    public static void Method2264(final File file, final String str) {
        PrintStream err;
        String string;
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final ArrayList<String> c = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                c.add(line);
            }
            boolean b = false;
            final Iterator<String> iterator = c.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals("")) {
                    b = true;
                    break;
                }
            }
            Spammer.Field795.clear();
            final ArrayList<String> field795 = new ArrayList<String>();
            if (b) {
                StringBuilder sb = new StringBuilder();
                for (final String str2 : c) {
                    if (str2.equals("")) {
                        if (sb.length() <= 0) {
                            continue;
                        }
                        field795.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    else {
                        sb.append(str2).append(" ");
                    }
                }
                field795.add(sb.toString());
            }
            else {
                field795.addAll(c);
            }
            Spammer.Field795 = field795;
            return;
        }
        catch (Exception ex) {
            err = System.err;
            string = "Could not load spammer file " + str;
        }
        err.println(string);
    }
    
    public static void Method2265(final Module module, final File file) {
        PrintStream err2;
        String string;
        try {
            final FileReader fileReader = new FileReader(file);
            final JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = null;
            try {
                jsonArray = (JsonArray)jsonParser.parse((Reader)fileReader);
            }
            catch (ClassCastException ex) {}
            JsonArray asJsonArray = null;
            Label_0152: {
                PrintStream err;
                String x;
                try {
                    asJsonArray = ((JsonObject)jsonArray.get(0)).getAsJsonArray("Modules");
                    break Label_0152;
                }
                catch (Exception ex2) {
                    err = System.err;
                    x = "Module Array not found!";
                }
                err.println(x);
            }
            final Iterator<JsonElement> iterator = asJsonArray.iterator();
            while (iterator.hasNext()) {
                final JsonObject asJsonObject = iterator.next().getAsJsonObject();
                if (asJsonObject.getAsJsonObject(module.getName()) != null) {
                    Method2253(asJsonObject);
                }
            }
            return;
        }
        catch (Exception ex3) {
            err2 = System.err;
            string = "Error while loading module " + module.getName();
        }
        err2.println(string);
    }
    
    public static void Method2266() {
        PrintStream err2;
        String x2;
        try {
            if (!Config.ACCOUNTS.exists()) {
                Method2232();
                return;
            }
            final FileReader fileReader = new FileReader(Config.ACCOUNTS);
            final JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = null;
            Label_0114: {
                try {
                    jsonArray = (JsonArray)jsonParser.parse((Reader)fileReader);
                    break Label_0114;
                }
                catch (ClassCastException ex) {}
                Method2232();
            }
            if (jsonArray != null && !jsonArray.isJsonNull()) {
                for (final JsonElement jsonElement : jsonArray) {
                    PrintStream err;
                    String x;
                    try {
                        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                        final String asString = asJsonObject.getAsJsonPrimitive("Name").getAsString();
                        final String asString2 = asJsonObject.getAsJsonPrimitive("E-Mail").getAsString();
                        final String asString3 = asJsonObject.getAsJsonPrimitive("Password").getAsString();
                        final String asString4 = asJsonObject.getAsJsonPrimitive("UUID").getAsString();
                        final String asString5 = asJsonObject.getAsJsonPrimitive("Token").getAsString();
                        final boolean asBoolean = asJsonObject.getAsJsonPrimitive("Microsoft").getAsBoolean();
                        final long asLong = asJsonObject.getAsJsonPrimitive("Last-Login").getAsLong();
                        final Class68 class68 = new Class68(asString2, asString3, asString3.equals(""), asBoolean);
                        class68.Method304(asString);
                        class68.Method318(asLong);
                        class68.Method306(asString4);
                        class68.Method310(asString5);
                        KonasGlobals.INSTANCE.Field1132.Field1739.add(new Class170(class68));
                        continue;
                    }
                    catch (NullPointerException ex2) {
                        err = System.err;
                        x = "Failed to load account";
                    }
                    err.println(x);
                }
            }
            return;
        }
        catch (IOException ex3) {
            err2 = System.err;
            x2 = "Error loading accounts";
        }
        err2.println(x2);
    }
    
    public static void Method2267(final JsonElement jsonElement) {
        Method2263(jsonElement.getAsJsonObject());
    }
    
    public static void Method2268(final Component class183) {
        if (class183 instanceof Class201) {
            ((Class201)class183).Method671().forEach(Config::Method2224);
        }
    }
    
    public static boolean Method2269(final JsonObject jsonObject, final Element element) {
        return jsonObject.getAsJsonObject(element.Method2316()) != null;
    }
    
    public static void Method2270() {
        Method2219(Config.CONFIG);
    }
    
    public static void Method2271(final Setting setting, final JsonElement jsonElement) {
        ((Class531)setting.getValue()).Method1147(jsonElement.getAsString());
    }
    
    public static void Method2272(final JsonElement jsonElement) {
        PrintStream err;
        String message;
        try {
            Method2253(jsonElement.getAsJsonObject());
            return;
        }
        catch (NullPointerException ex) {
            err = System.err;
            message = ex.getMessage();
        }
        err.println(message);
    }
    
    public static JsonArray Method2273() {
        final JsonArray jsonArray = new JsonArray();
        for (final Element element : KonasGlobals.INSTANCE.Field1136.Method2196()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("A", (Number)element.Method2317());
            jsonObject.addProperty("X", (Number)element.Method2336());
            jsonObject.addProperty("Y", (Number)element.Method2315());
            jsonObject.addProperty("Enabled", Boolean.valueOf(element.Method2338()));
            jsonObject.addProperty("Visible", Boolean.valueOf(element.Method2338()));
            if (Class109.Method2198(element).length > 0) {
                for (final Setting setting : Class109.Method2198(element)) {
                    if (setting.getValue() instanceof Number) {
                        jsonObject.addProperty(setting.Method1183(), (Number)setting.getValue());
                    }
                    else if (setting.getValue() instanceof Boolean) {
                        jsonObject.addProperty(setting.Method1183(), (Boolean)setting.getValue());
                    }
                    else if (setting.getValue() instanceof Enum || setting.getValue() instanceof String) {
                        jsonObject.addProperty(setting.Method1183(), String.valueOf(setting.getValue()));
                    }
                    else if (setting.getValue() instanceof ColorValue) {
                        final JsonArray jsonArray2 = new JsonArray();
                        jsonArray2.add((Number)((ColorValue)setting.getValue()).Method778());
                        jsonArray2.add(Boolean.valueOf(((ColorValue)setting.getValue()).Method783()));
                        jsonObject.add(setting.Method1183(), (JsonElement)jsonArray2);
                    }
                    else if (setting.getValue() instanceof Class443) {
                        final JsonArray jsonArray3 = new JsonArray();
                        final Iterator<String> iterator3 = ((Class443)setting.getValue()).Method684().iterator();
                        while (iterator3.hasNext()) {
                            jsonArray3.add((String)iterator3.next());
                        }
                        jsonObject.add(setting.Method1183(), (JsonElement)jsonArray3);
                    }
                    else {
                        if (!(setting.getValue() instanceof Class531)) {
                            continue;
                        }
                        final JsonArray jsonArray4 = new JsonArray();
                        final Iterator<String> iterator4 = ((Class531)setting.getValue()).Method1146().iterator();
                        while (iterator4.hasNext()) {
                            jsonArray4.add((String)iterator4.next());
                        }
                        jsonObject.add(setting.Method1183(), (JsonElement)jsonArray4);
                    }
                }
            }
            final JsonObject jsonObject2 = new JsonObject();
            jsonObject2.add(element.Method2316(), (JsonElement)jsonObject);
            jsonArray.add((JsonElement)jsonObject2);
        }
        return jsonArray;
    }
    
    public static void Method2274(final File file, final boolean b) {
        if (!file.exists() || !Config.ACCOUNTS.exists()) {
            Method2219(file);
        }
        Class202.Field678 = true;
        KonasChat.Field645 = true;
        Label_1402: {
            IOException ex;
            try {
                final FileReader fileReader = new FileReader(file);
                final JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = null;
                Label_0139: {
                    try {
                        jsonArray = (JsonArray)jsonParser.parse((Reader)fileReader);
                        break Label_0139;
                    }
                    catch (ClassCastException ex3) {}
                    Method2219(file);
                }
                JsonArray asJsonArray = null;
                Label_0216: {
                    PrintStream err;
                    String x;
                    try {
                        asJsonArray = ((JsonObject)jsonArray.get(0)).getAsJsonArray("Modules");
                        break Label_0216;
                    }
                    catch (Exception ex4) {
                        err = System.err;
                        x = "Module Array not found, skipping!";
                    }
                    err.println(x);
                }
                JsonArray asJsonArray2 = null;
                Label_0299: {
                    PrintStream err2;
                    String x2;
                    try {
                        asJsonArray2 = ((JsonObject)jsonArray.get(1)).getAsJsonArray("Containers");
                        break Label_0299;
                    }
                    catch (Exception ex5) {
                        err2 = System.err;
                        x2 = "Container Array not found, skipping!";
                    }
                    err2.println(x2);
                }
                JsonArray asJsonArray3 = null;
                Label_0376: {
                    PrintStream err3;
                    String x3;
                    try {
                        asJsonArray3 = ((JsonObject)jsonArray.get(2)).getAsJsonArray("Panels");
                        break Label_0376;
                    }
                    catch (Exception ex6) {
                        err3 = System.err;
                        x3 = "Panel Array not found, skipping!";
                    }
                    err3.println(x3);
                }
                JsonArray asJsonArray4 = null;
                Label_0459: {
                    PrintStream err4;
                    String x4;
                    try {
                        asJsonArray4 = ((JsonObject)jsonArray.get(3)).getAsJsonArray("Friends");
                        break Label_0459;
                    }
                    catch (Exception ex7) {
                        err4 = System.err;
                        x4 = "Friend Array not found, skipping!";
                    }
                    err4.println(x4);
                }
                JsonObject jsonObject = null;
                Label_0517: {
                    PrintStream err5;
                    String x5;
                    try {
                        jsonObject = (JsonObject)jsonArray.get(4);
                        break Label_0517;
                    }
                    catch (Exception ex8) {
                        err5 = System.err;
                        x5 = "Prefix Object not found, skipping!";
                    }
                    err5.println(x5);
                }
                JsonObject jsonObject2 = null;
                Label_0569: {
                    PrintStream err6;
                    String x6;
                    try {
                        jsonObject2 = (JsonObject)jsonArray.get(5);
                        break Label_0569;
                    }
                    catch (Exception ex9) {
                        err6 = System.err;
                        x6 = "Language Object not found, skipping!";
                    }
                    err6.println(x6);
                }
                JsonArray asJsonArray5 = null;
                Label_0653: {
                    PrintStream err7;
                    String x7;
                    try {
                        asJsonArray5 = ((JsonObject)jsonArray.get(6)).getAsJsonArray("Macros");
                        break Label_0653;
                    }
                    catch (Exception ex10) {
                        err7 = System.err;
                        x7 = "Macro Array not found, skipping!";
                    }
                    err7.println(x7);
                }
                JsonArray asJsonArray6 = null;
                Label_0896: {
                    PrintStream err9 = null;
                    String x9 = null;
                    Label_0731: {
                        PrintStream err8;
                        String x8;
                        try {
                            asJsonArray6 = ((JsonObject)jsonArray.get(7)).getAsJsonArray("Waypoints");
                            break Label_0731;
                        }
                        catch (Exception ex11) {
                            err8 = System.err;
                            x8 = "Waypoints Array not found, skipping!";
                        }
                        err8.println(x8);
                        try {
                            fontCommand.Field1351 = jsonArray.get(8).getAsString();
                            Hud.Field1405 = new CfontRenderer(fontCommand.Field1351, 18.0f);
                            ClickGUIModule.Field1536 = new CfontRenderer(fontCommand.Field1351, 17.0f);
                            NameTags.Field957 = new CfontRenderer(fontCommand.Field1351, 20.0f);
                            NameTags.Field958 = new CfontRenderer(fontCommand.Field1351, 60.0f);
                            break Label_0896;
                        }
                        catch (Exception ex12) {
                            err9 = System.err;
                            x9 = "Font not found, skipping!";
                        }
                    }
                    err9.println(x9);
                }
                JsonArray asJsonArray7 = null;
                Label_0974: {
                    PrintStream err10;
                    String x10;
                    try {
                        asJsonArray7 = ((JsonObject)jsonArray.get(9)).getAsJsonArray("Muted");
                        break Label_0974;
                    }
                    catch (Exception ex13) {
                        err10 = System.err;
                        x10 = "Friend Array not found, skipping!";
                    }
                    err10.println(x10);
                }
                if (asJsonArray != null) {
                    asJsonArray.forEach((Consumer) Config::Method2272);
                }
                JsonObject jsonObject3 = null;
                Label_1050: {
                    PrintStream err11;
                    String x11;
                    try {
                        jsonObject3 = (JsonObject)jsonArray.get(10);
                        break Label_1050;
                    }
                    catch (Exception ex14) {
                        err11 = System.err;
                        x11 = "Prefix Object not found, skipping!";
                    }
                    err11.println(x11);
                }
                NameTags.Method955((Class427) NameTags.Field954.getValue());
                if (asJsonArray2 != null) {
                    asJsonArray2.forEach((Consumer) Config::Method2229);
                }
                if (asJsonArray3 != null) {
                    asJsonArray3.forEach((Consumer) Config::Method2227);
                }
                if (asJsonArray5 != null) {
                    Class157.Method1707();
                    asJsonArray5.forEach((Consumer) Config::Method2222);
                }
                if (asJsonArray6 != null) {
                    KonasGlobals.INSTANCE.Field1138.Method765();
                    asJsonArray6.forEach((Consumer) Config::Method2239);
                }
                if (asJsonArray4 != null) {
                    Class492.Method1986();
                    asJsonArray4.forEach((Consumer) Config::Method2233);
                }
                if (jsonObject != null) {
                    Method2242(jsonObject);
                }
                if (jsonObject2 != null) {
                    Method2236(jsonObject2);
                }
                KonasGlobals.INSTANCE.Field1130.Method119().forEach(Config::Method2278);
                if (asJsonArray7 != null) {
                    Class484.Method2070();
                    asJsonArray7.forEach((Consumer) Config::Method2267);
                }
                if (jsonObject3 != null) {
                    Method2243(jsonObject3);
                }
                break Label_1402;
            }
            catch (IOException ex2) {
                ex = ex2;
            }
            ex.printStackTrace();
        }
        Config.currentConfig = file;
        Method2276();
        Method2230();
        Method2221();
        Method2251();
        KonasChat.Field645 = false;
        Class202.Field678 = false;
        if (b) {
            Method2266();
        }
    }
    
    public static JsonArray Method2275() {
        final JsonArray jsonArray = new JsonArray();
        for (final String s : Class484.Method2068()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", s);
            jsonArray.add((JsonElement)jsonObject);
        }
        return jsonArray;
    }
    
    public static void Method2276() {
        PrintStream err;
        String x;
        try {
            final File parent = new File(Config.KONAS_FOLDER, "autogg");
            final File file = new File(parent, "gg.txt");
            final File file2 = new File(parent, "ez.txt");
            final File file3 = new File(parent, "log.txt");
            final File file4 = new File(parent, "excuse.txt");
            final File file5 = new File(parent, "pop.txt");
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                final Iterator<String> iterator = AutoGG.Field1976.iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(iterator.next() + "\n");
                }
                bufferedWriter.close();
            }
            if (!file2.exists()) {
                file2.createNewFile();
                final BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file2));
                final Iterator<String> iterator2 = AutoGG.Field1977.iterator();
                while (iterator2.hasNext()) {
                    bufferedWriter2.write(iterator2.next() + "\n");
                }
                bufferedWriter2.close();
            }
            if (!file3.exists()) {
                file3.createNewFile();
                final BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter(file3));
                final Iterator<String> iterator3 = AutoGG.Field1979.iterator();
                while (iterator3.hasNext()) {
                    bufferedWriter3.write(iterator3.next() + "\n");
                }
                bufferedWriter3.close();
            }
            if (!file4.exists()) {
                file4.createNewFile();
                final BufferedWriter bufferedWriter4 = new BufferedWriter(new FileWriter(file4));
                final Iterator<String> iterator4 = AutoGG.Field1980.iterator();
                while (iterator4.hasNext()) {
                    bufferedWriter4.write(iterator4.next() + "\n");
                }
                bufferedWriter4.close();
            }
            if (!file5.exists()) {
                file5.createNewFile();
                final BufferedWriter bufferedWriter5 = new BufferedWriter(new FileWriter(file5));
                final Iterator<String> iterator5 = AutoGG.Field1978.iterator();
                while (iterator5.hasNext()) {
                    bufferedWriter5.write(iterator5.next() + "\n");
                }
                bufferedWriter5.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final ArrayList<String> c = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    c.add(line);
                }
            }
            AutoGG.Field1976.clear();
            AutoGG.Field1976.addAll(c);
            final BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
            final ArrayList<String> c2 = new ArrayList<String>();
            String line2;
            while ((line2 = bufferedReader2.readLine()) != null) {
                if (!line2.equals("")) {
                    c2.add(line2);
                }
            }
            AutoGG.Field1977.clear();
            AutoGG.Field1977.addAll(c2);
            final BufferedReader bufferedReader3 = new BufferedReader(new FileReader(file3));
            final ArrayList<String> c3 = new ArrayList<String>();
            String line3;
            while ((line3 = bufferedReader3.readLine()) != null) {
                if (!line3.equals("")) {
                    c3.add(line3);
                }
            }
            AutoGG.Field1979.clear();
            AutoGG.Field1979.addAll(c3);
            final BufferedReader bufferedReader4 = new BufferedReader(new FileReader(file4));
            final ArrayList<String> c4 = new ArrayList<String>();
            String line4;
            while ((line4 = bufferedReader4.readLine()) != null) {
                if (!line4.equals("")) {
                    c4.add(line4);
                }
            }
            AutoGG.Field1980.clear();
            AutoGG.Field1980.addAll(c4);
            final BufferedReader bufferedReader5 = new BufferedReader(new FileReader(file5));
            final ArrayList<String> c5 = new ArrayList<String>();
            String line5;
            while ((line5 = bufferedReader5.readLine()) != null) {
                if (!line5.equals("")) {
                    c5.add(line5);
                }
            }
            AutoGG.Field1978.clear();
            AutoGG.Field1978.addAll(c5);
            return;
        }
        catch (IOException ex) {
            err = System.err;
            x = "Cant load AutoGG Files!";
        }
        err.println(x);
    }
    
    public static void Method2277(final String s, final boolean b) {
        PrintStream err;
        String string;
        try {
            final File parent = new File(Config.KONAS_FOLDER, "spammer");
            if (!parent.exists()) {
                parent.mkdirs();
            }
            final File file = new File(parent, s);
            if (b && !file.exists()) {
                file.createNewFile();
            }
            new Thread(Config::Method2264).start();
            return;
        }
        catch (IOException ex) {
            err = System.err;
            string = "Could not load spammer file " + s;
        }
        err.println(string);
    }
    
    public static void Method2278(final Frame class90) {
        if (class90 instanceof Class200) {
            ((Class200)class90).Method706().forEach(Config::Method2268);
        }
        else if (class90 instanceof CategoryFrame) {
            ((CategoryFrame)class90).getComponents().forEach(Config::Method2238);
        }
    }
    
    public static void Method2279(final File file) {
        PrintStream err;
        String x;
        try {
            final JsonArray method2256 = Method2256(file);
            if (me.darki.konas.module.client.Config.overwriteFriends.getValue()) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.add("Friends", (JsonElement)Method2245());
                method2256.set(3, (JsonElement)jsonObject);
                Method2261(method2256, file);
            }
            else {
                JsonArray asJsonArray;
                try {
                    asJsonArray = ((JsonObject)method2256.get(3)).getAsJsonArray("Friends");
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println("Friends Array not found!");
                    return;
                }
                for (final JsonElement jsonElement : Method2245()) {
                    if (!asJsonArray.contains(jsonElement)) {
                        asJsonArray.add(jsonElement);
                    }
                }
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.add("Friends", (JsonElement)asJsonArray);
                method2256.set(3, (JsonElement)jsonObject2);
                Method2261(method2256, file);
            }
            return;
        }
        catch (Exception ex2) {
            err = System.err;
            x = "Error while saving friends!";
        }
        err.println(x);
    }
}