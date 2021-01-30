package com.viaversion.viaversion.api.minecraft.chunks;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface ChunkSection {
   int SIZE = 4096;

   static int index(int x, int y, int z) {
      return y << 8 | z << 4 | x;
   }

   int getFlatBlock(int var1);

   default int getFlatBlock(int x, int y, int z) {
      return this.getFlatBlock(index(x, y, z));
   }

   void setFlatBlock(int var1, int var2);

   default void setFlatBlock(int x, int y, int z, int id) {
      this.setFlatBlock(index(x, y, z), id);
   }

   default int getBlockWithoutData(int x, int y, int z) {
      return this.getFlatBlock(x, y, z) >> 4;
   }

   default int getBlockData(int x, int y, int z) {
      return this.getFlatBlock(x, y, z) & 15;
   }

   default void setBlockWithData(int x, int y, int z, int type, int data) {
      this.setFlatBlock(index(x, y, z), type << 4 | data & 15);
   }

   default void setBlockWithData(int idx, int type, int data) {
      this.setFlatBlock(idx, type << 4 | data & 15);
   }

   void setPaletteIndex(int var1, int var2);

   int getPaletteIndex(int var1);

   int getPaletteSize();

   int getPaletteEntry(int var1);

   void setPaletteEntry(int var1, int var2);

   void replacePaletteEntry(int var1, int var2);

   void addPaletteEntry(int var1);

   void clearPalette();

   int getNonAirBlocksCount();

   void setNonAirBlocksCount(int var1);

   default boolean hasLight() {
      return this.getLight() != null;
   }

   @Nullable
   ChunkSectionLight getLight();

   void setLight(@Nullable ChunkSectionLight var1);
}
