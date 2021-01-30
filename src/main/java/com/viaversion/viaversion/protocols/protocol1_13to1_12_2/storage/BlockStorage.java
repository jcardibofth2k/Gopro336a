package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockStorage implements StorableObject {
   private static final IntSet WHITELIST = new IntOpenHashSet(46, 1.0F);
   private final Map blocks = new ConcurrentHashMap();

   public void store(Position position, int block) {
      this.store(position, block, -1);
   }

   public void store(Position position, int block, int replacementId) {
      if (WHITELIST.contains(block)) {
         this.blocks.put(position, new BlockStorage.ReplacementData(block, replacementId));
      }
   }

   public boolean isWelcome(int block) {
      return WHITELIST.contains(block);
   }

   public boolean contains(Position position) {
      return this.blocks.containsKey(position);
   }

   public BlockStorage.ReplacementData get(Position position) {
      return (BlockStorage.ReplacementData)this.blocks.get(position);
   }

   public BlockStorage.ReplacementData remove(Position position) {
      return (BlockStorage.ReplacementData)this.blocks.remove(position);
   }

   static {
      WHITELIST.add(5266);

      int i;
      for(i = 0; i < 16; ++i) {
         WHITELIST.add(972 + i);
      }

      for(i = 0; i < 20; ++i) {
         WHITELIST.add(6854 + i);
      }

      for(i = 0; i < 4; ++i) {
         WHITELIST.add(7110 + i);
      }

      for(i = 0; i < 5; ++i) {
         WHITELIST.add(5447 + i);
      }

   }

   public static class ReplacementData {
      private int original;
      private int replacement;

      public ReplacementData(int original, int replacement) {
         this.original = original;
         this.replacement = replacement;
      }

      public int getOriginal() {
         return this.original;
      }

      public void setOriginal(int original) {
         this.original = original;
      }

      public int getReplacement() {
         return this.replacement;
      }

      public void setReplacement(int replacement) {
         this.replacement = replacement;
      }
   }
}
