package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

public abstract class Config implements ConfigurationProvider {
   private static final ThreadLocal YAML = ThreadLocal.withInitial(() -> {
      DumperOptions options = new DumperOptions();
      options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
      options.setPrettyFlow(false);
      options.setIndent(2);
      return new Yaml(new YamlConstructor(), new Representer(), options);
   });
   private final CommentStore commentStore = new CommentStore('.', 2);
   private final File configFile;
   private Map config;

   public Config(File configFile) {
      this.configFile = configFile;
   }

   public abstract URL getDefaultConfigURL();

   public synchronized Map loadConfig(File location) {
      List unsupported = this.getUnsupportedOptions();
      URL jarConfigFile = this.getDefaultConfigURL();

      try {
         this.commentStore.storeComments(jarConfigFile.openStream());
         Iterator var4 = unsupported.iterator();

         while(var4.hasNext()) {
            String option = (String)var4.next();
            List comments = this.commentStore.header(option);
            if (comments != null) {
               comments.clear();
            }
         }
      } catch (IOException var16) {
         var16.printStackTrace();
      }

      Map config = null;
      if (location.exists()) {
         try {
            FileInputStream input = new FileInputStream(location);

            try {
               config = (Map)((Yaml)YAML.get()).load((InputStream)input);
            } catch (Throwable var11) {
               try {
                  input.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
               }

               throw var11;
            }

            input.close();
         } catch (FileNotFoundException var12) {
            var12.printStackTrace();
         } catch (IOException var13) {
            var13.printStackTrace();
         }
      }

      if (config == null) {
         config = new HashMap();
      }

      Object defaults = config;

      try {
         InputStream stream = jarConfigFile.openStream();

         try {
            defaults = (Map)((Yaml)YAML.get()).load(stream);
            Iterator var7 = unsupported.iterator();

            while(var7.hasNext()) {
               String option = (String)var7.next();
               ((Map)defaults).remove(option);
            }

            var7 = ((Map)config).entrySet().iterator();

            while(var7.hasNext()) {
               Entry entry = (Entry)var7.next();
               if (((Map)defaults).containsKey(entry.getKey()) && !unsupported.contains(entry.getKey())) {
                  ((Map)defaults).put((String)entry.getKey(), entry.getValue());
               }
            }
         } catch (Throwable var14) {
            if (stream != null) {
               try {
                  stream.close();
               } catch (Throwable var9) {
                  var14.addSuppressed(var9);
               }
            }

            throw var14;
         }

         if (stream != null) {
            stream.close();
         }
      } catch (IOException var15) {
         var15.printStackTrace();
      }

      this.handleConfig((Map)defaults);
      this.saveConfig(location, (Map)defaults);
      return (Map)defaults;
   }

   protected abstract void handleConfig(Map var1);

   public synchronized void saveConfig(File location, Map config) {
      try {
         this.commentStore.writeComments(((Yaml)YAML.get()).dump(config), location);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public abstract List getUnsupportedOptions();

   public void set(String path, Object value) {
      this.config.put(path, value);
   }

   public void saveConfig() {
      this.configFile.getParentFile().mkdirs();
      this.saveConfig(this.configFile, this.config);
   }

   public void reloadConfig() {
      this.configFile.getParentFile().mkdirs();
      this.config = new ConcurrentSkipListMap(this.loadConfig(this.configFile));
   }

   public Map getValues() {
      return this.config;
   }

   @Nullable
   public Object get(String key, Class clazz, Object def) {
      Object o = this.config.get(key);
      return o != null ? o : def;
   }

   public boolean getBoolean(String key, boolean def) {
      Object o = this.config.get(key);
      return o != null ? (Boolean)o : def;
   }

   @Nullable
   public String getString(String key, @Nullable String def) {
      Object o = this.config.get(key);
      return o != null ? (String)o : def;
   }

   public int getInt(String key, int def) {
      Object o = this.config.get(key);
      if (o != null) {
         return o instanceof Number ? ((Number)o).intValue() : def;
      } else {
         return def;
      }
   }

   public double getDouble(String key, double def) {
      Object o = this.config.get(key);
      if (o != null) {
         return o instanceof Number ? ((Number)o).doubleValue() : def;
      } else {
         return def;
      }
   }

   public List getIntegerList(String key) {
      Object o = this.config.get(key);
      return (List)(o != null ? (List)o : new ArrayList());
   }

   @Nullable
   public JsonElement getSerializedComponent(String key) {
      Object o = this.config.get(key);
      return o != null && !((String)o).isEmpty() ? GsonComponentSerializer.gson().serializeToTree(LegacyComponentSerializer.legacySection().deserialize((String)o)) : null;
   }
}
