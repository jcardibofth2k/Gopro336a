package dev.tigr.emojiapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

public class Emojis {
   // $FF: renamed from: MC net.minecraft.client.Minecraft
   private static final Minecraft field_3218 = Minecraft.getMinecraft();
   private static final String VERSION_URL = "https://raw.githubusercontent.com/2b2t-Utilities/emojis/master/version.json";
   private static final String ZIP_URL = "https://github.com/2b2t-Utilities/emojis/archive/master.zip";
   private static final String FOLDER = "emoji";
   private static final File LOCAL_VERSION;
   private static final Map EMOJI_MAP;

   private static void load() {
      File dir = new File("emoji");
      if (!dir.exists()) {
         dir.mkdir();
      }

      try {
         if (!LOCAL_VERSION.exists()) {
            update_emojis();
         } else {
            JsonObject globalVer = read((new URL("https://raw.githubusercontent.com/2b2t-Utilities/emojis/master/version.json")).openStream());
            JsonObject localVer = read(new FileInputStream(LOCAL_VERSION));
            if (!globalVer.has("version")) {
               update_emojis();
            } else if (globalVer.get("version").getAsInt() != localVer.get("version").getAsInt()) {
               update_emojis();
            }
         }
      } catch (Exception var4) {
      }

      try {
         Files.list((new File("emoji")).toPath()).filter((path) -> {
            return path.endsWith(".png");
         }).forEach((path) -> {
            try {
               addEmoji(path);
            } catch (Exception var2) {
            }

         });
      } catch (IOException var3) {
      }

   }

   private static JsonObject read(InputStream stream) {
      Gson gson = new Gson();
      JsonObject jsonObject = null;

      try {
         String json = IOUtils.toString(stream, Charsets.UTF_8);
         jsonObject = gson.fromJson(json, JsonObject.class);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return jsonObject;
   }

   private static void update_emojis() throws IOException {
      ZipInputStream zip = new ZipInputStream((new URL("https://github.com/2b2t-Utilities/emojis/archive/master.zip")).openStream());

      for(ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
         String filePath = "emoji" + File.separator + entry.getName().substring(entry.getName().indexOf("/"));
         if (!entry.isDirectory()) {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[4096];

            int read;
            while((read = zip.read(bytesIn)) != -1) {
               bos.write(bytesIn, 0, read);
            }

            bos.close();
         }

         zip.closeEntry();
      }

      zip.close();
   }

   private static void addEmoji(Path path) {
      File file = path.toFile();

      DynamicTexture dynamicTexture;
      try {
         BufferedImage image = ImageIO.read(file);
         dynamicTexture = new DynamicTexture(image);
         dynamicTexture.loadTexture(field_3218.getResourceManager());
      } catch (Exception var4) {
         return;
      }

      EMOJI_MAP.put(file.getName().replaceAll(".png", ""), field_3218.getTextureManager().getDynamicTextureLocation(file.getName().replaceAll(".png", ""), dynamicTexture));
   }

   public static ResourceLocation getEmoji(Emoji emoji) {
      return (ResourceLocation)EMOJI_MAP.get(emoji.getName());
   }

   public static boolean isEmoji(String name) {
      return EMOJI_MAP.containsKey(name);
   }

   static {
      LOCAL_VERSION = new File("emoji" + File.separator + "version.json");
      EMOJI_MAP = new HashMap();
      load();
   }
}
