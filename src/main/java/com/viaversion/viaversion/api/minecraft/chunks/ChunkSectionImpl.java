package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ChunkSectionImpl implements ChunkSection {
   private final IntList palette;
   private final Int2IntMap inversePalette;
   private final int[] blocks = new int[4096];
   private ChunkSectionLight light;
   private int nonAirBlocksCount;

   public ChunkSectionImpl(boolean holdsLight) {
      this.palette = new IntArrayList();
      this.inversePalette = new Int2IntOpenHashMap();
      this.inversePalette.defaultReturnValue(-1);
      if (holdsLight) {
         this.light = new ChunkSectionLightImpl();
      }

   }

   public ChunkSectionImpl(boolean holdsLight, int expectedPaletteLength) {
      if (holdsLight) {
         this.light = new ChunkSectionLightImpl();
      }

      this.palette = new IntArrayList(expectedPaletteLength);
      this.inversePalette = new Int2IntOpenHashMap(expectedPaletteLength);
      this.inversePalette.defaultReturnValue(-1);
   }

   public int getFlatBlock(int idx) {
      int index = this.blocks[idx];
      return this.palette.getInt(index);
   }

   public void setFlatBlock(int idx, int id) {
      int index = this.inversePalette.get(id);
      if (index == -1) {
         index = this.palette.size();
         this.palette.add(id);
         this.inversePalette.put(id, index);
      }

      this.blocks[idx] = index;
   }

   public int getPaletteIndex(int idx) {
      return this.blocks[idx];
   }

   public void setPaletteIndex(int idx, int index) {
      this.blocks[idx] = index;
   }

   public int getPaletteSize() {
      return this.palette.size();
   }

   public int getPaletteEntry(int index) {
      return this.palette.getInt(index);
   }

   public void setPaletteEntry(int index, int id) {
      int oldId = this.palette.set(index, id);
      if (oldId != id) {
         this.inversePalette.put(id, index);
         if (this.inversePalette.get(oldId) == index) {
            this.inversePalette.remove(oldId);

            for(int i = 0; i < this.palette.size(); ++i) {
               if (this.palette.getInt(i) == oldId) {
                  this.inversePalette.put(oldId, i);
                  break;
               }
            }
         }

      }
   }

   public void replacePaletteEntry(int oldId, int newId) {
      int index = this.inversePalette.remove(oldId);
      if (index != -1) {
         this.inversePalette.put(newId, index);

         for(int i = 0; i < this.palette.size(); ++i) {
            if (this.palette.getInt(i) == oldId) {
               this.palette.set(i, newId);
            }
         }

      }
   }

   public void addPaletteEntry(int id) {
      this.inversePalette.put(id, this.palette.size());
      this.palette.add(id);
   }

   public void clearPalette() {
      this.palette.clear();
      this.inversePalette.clear();
   }

   public int getNonAirBlocksCount() {
      return this.nonAirBlocksCount;
   }

   public void setNonAirBlocksCount(int nonAirBlocksCount) {
      this.nonAirBlocksCount = nonAirBlocksCount;
   }

   @Nullable
   public ChunkSectionLight getLight() {
      return this.light;
   }

   public void setLight(@Nullable ChunkSectionLight light) {
      this.light = light;
   }
}
