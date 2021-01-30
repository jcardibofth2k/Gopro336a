package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FakeTileEntity {
   private static final Int2ObjectMap tileEntities = new Int2ObjectOpenHashMap();

   private static void register(int material, String name) {
      CompoundTag comp = new CompoundTag();
      comp.put(name, new StringTag());
      tileEntities.put(material, comp);
   }

   private static void register(List materials, String name) {
      Iterator var2 = materials.iterator();

      while(var2.hasNext()) {
         int id = (Integer)var2.next();
         register(id, name);
      }

   }

   public static boolean hasBlock(int block) {
      return tileEntities.containsKey(block);
   }

   public static CompoundTag getFromBlock(int x, int y, int z, int block) {
      CompoundTag originalTag = (CompoundTag)tileEntities.get(block);
      if (originalTag != null) {
         CompoundTag tag = originalTag.clone();
         tag.put("x", new IntTag(x));
         tag.put("y", new IntTag(y));
         tag.put("z", new IntTag(z));
         return tag;
      } else {
         return null;
      }
   }

   static {
      register(Arrays.asList(61, 62), "Furnace");
      register(Arrays.asList(54, 146), "Chest");
      register(130, "EnderChest");
      register(84, "RecordPlayer");
      register(23, "Trap");
      register(158, "Dropper");
      register(Arrays.asList(63, 68), "Sign");
      register(52, "MobSpawner");
      register(25, "Music");
      register(Arrays.asList(33, 34, 29, 36), "Piston");
      register(117, "Cauldron");
      register(116, "EnchantTable");
      register(Arrays.asList(119, 120), "Airportal");
      register(138, "Beacon");
      register(144, "Skull");
      register(Arrays.asList(178, 151), "DLDetector");
      register(154, "Hopper");
      register(Arrays.asList(149, 150), "Comparator");
      register(140, "FlowerPot");
      register(Arrays.asList(176, 177), "Banner");
      register(209, "EndGateway");
      register(137, "Control");
   }
}
