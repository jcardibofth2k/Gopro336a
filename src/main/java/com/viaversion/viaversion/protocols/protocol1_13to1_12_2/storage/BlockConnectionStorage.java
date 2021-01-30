package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.util.Pair;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BlockConnectionStorage implements StorableObject {
   private static final short[] REVERSE_BLOCK_MAPPINGS = new short[8582];
   private static Constructor fastUtilLongObjectHashMap;
   private final Map blockStorage = this.createLongObjectMap();

   public void store(int x, int y, int z, int blockState) {
      short mapping = REVERSE_BLOCK_MAPPINGS[blockState];
      if (mapping != -1) {
         long pair = this.getChunkSectionIndex(x, y, z);
         Pair map = this.getChunkSection(pair, (mapping & 15) != 0);
         int blockIndex = this.encodeBlockPos(x, y, z);
         ((byte[])map.getKey())[blockIndex] = (byte)(mapping >> 4);
         NibbleArray nibbleArray = (NibbleArray)map.getValue();
         if (nibbleArray != null) {
            nibbleArray.set(blockIndex, mapping);
         }

      }
   }

   public int get(int x, int y, int z) {
      long pair = this.getChunkSectionIndex(x, y, z);
      Pair map = (Pair)this.blockStorage.get(pair);
      if (map == null) {
         return 0;
      } else {
         short blockPosition = this.encodeBlockPos(x, y, z);
         NibbleArray nibbleArray = (NibbleArray)map.getValue();
         return WorldPackets.toNewId((((byte[])map.getKey())[blockPosition] & 255) << 4 | (nibbleArray == null ? 0 : nibbleArray.get(blockPosition)));
      }
   }

   public void remove(int x, int y, int z) {
      long pair = this.getChunkSectionIndex(x, y, z);
      Pair map = (Pair)this.blockStorage.get(pair);
      if (map != null) {
         int blockIndex = this.encodeBlockPos(x, y, z);
         NibbleArray nibbleArray = (NibbleArray)map.getValue();
         int i;
         if (nibbleArray != null) {
            nibbleArray.set(blockIndex, 0);
            boolean allZero = true;

            for(i = 0; i < 4096; ++i) {
               if (nibbleArray.get(i) != 0) {
                  allZero = false;
                  break;
               }
            }

            if (allZero) {
               map.setValue((Object)null);
            }
         }

         ((byte[])map.getKey())[blockIndex] = 0;
         byte[] var13 = (byte[])map.getKey();
         i = var13.length;

         for(int var11 = 0; var11 < i; ++var11) {
            short entry = (short)var13[var11];
            if (entry != 0) {
               return;
            }
         }

         this.blockStorage.remove(pair);
      }
   }

   public void clear() {
      this.blockStorage.clear();
   }

   public void unloadChunk(int x, int z) {
      for(int y = 0; y < 256; y += 16) {
         this.blockStorage.remove(this.getChunkSectionIndex(x << 4, y, z << 4));
      }

   }

   private Pair getChunkSection(long index, boolean requireNibbleArray) {
      Pair map = (Pair)this.blockStorage.get(index);
      if (map == null) {
         map = new Pair(new byte[4096], (Object)null);
         this.blockStorage.put(index, map);
      }

      if (map.getValue() == null && requireNibbleArray) {
         map.setValue(new NibbleArray(4096));
      }

      return map;
   }

   private long getChunkSectionIndex(int x, int y, int z) {
      return ((long)(x >> 4) & 67108863L) << 38 | ((long)(y >> 4) & 4095L) << 26 | (long)(z >> 4) & 67108863L;
   }

   private long getChunkSectionIndex(Position position) {
      return this.getChunkSectionIndex(position.getX(), position.getY(), position.getZ());
   }

   private short encodeBlockPos(int x, int y, int z) {
      return (short)((y & 15) << 8 | (x & 15) << 4 | z & 15);
   }

   private short encodeBlockPos(Position pos) {
      return this.encodeBlockPos(pos.getX(), pos.getY(), pos.getZ());
   }

   private Map createLongObjectMap() {
      if (fastUtilLongObjectHashMap != null) {
         try {
            return (Map)fastUtilLongObjectHashMap.newInstance();
         } catch (InstantiationException | InvocationTargetException | IllegalAccessException var2) {
            var2.printStackTrace();
         }
      }

      return new HashMap();
   }

   static {
      try {
         String className = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
         fastUtilLongObjectHashMap = Class.forName(className).getConstructor();
         Via.getPlatform().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
      } catch (NoSuchMethodException | ClassNotFoundException var2) {
      }

      Arrays.fill(REVERSE_BLOCK_MAPPINGS, (short)-1);

      for(int i = 0; i < 4096; ++i) {
         int newBlock = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(i);
         if (newBlock != -1) {
            REVERSE_BLOCK_MAPPINGS[newBlock] = (short)i;
         }
      }

   }
}
