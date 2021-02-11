package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class BlockIdData {
   public static final String[] PREVIOUS = new String[0];
   public static Map blockIdMapping;
   public static Map fallbackReverseMapping;
   public static Int2ObjectMap numberIdToString;

   public static void init() {
      InputStream stream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json");

      try {
         InputStreamReader reader = new InputStreamReader(stream);

         try {
            Map map = (Map)GsonUtil.getGson().fromJson(reader, (new TypeToken() {
            }).getType());
            blockIdMapping = new HashMap(map);
            fallbackReverseMapping = new HashMap();
            Iterator var3 = blockIdMapping.entrySet().iterator();

            while(var3.hasNext()) {
               Entry entry = (Entry)var3.next();
               String[] var5 = (String[])entry.getValue();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String val = var5[var7];
                  String[] previous = (String[])fallbackReverseMapping.get(val);
                  if (previous == null) {
                     previous = PREVIOUS;
                  }

                  fallbackReverseMapping.put(val, (String[])ObjectArrays.concat(previous, (String)entry.getKey()));
               }
            }
         } catch (Throwable var14) {
            try {
               reader.close();
            } catch (Throwable var13) {
               var14.addSuppressed(var13);
            }

            throw var14;
         }

         reader.close();
      } catch (IOException var15) {
         var15.printStackTrace();
      }

      InputStream blockS = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json");

      try {
         InputStreamReader blockR = new InputStreamReader(blockS);

         try {
            Map map = (Map)GsonUtil.getGson().fromJson(blockR, (new TypeToken() {
            }).getType());
            numberIdToString = new Int2ObjectOpenHashMap(map);
         } catch (Throwable var11) {
            try {
               blockR.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }

            throw var11;
         }

         blockR.close();
      } catch (IOException var12) {
         var12.printStackTrace();
      }

   }
}
