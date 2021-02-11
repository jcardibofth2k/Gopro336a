package com.viaversion.viaversion.api.minecraft;

import java.util.HashMap;
import java.util.Map;

public enum BlockFace {
   NORTH((byte)0, (byte)0, (byte)-1, BlockFace.EnumAxis.field_2824),
   SOUTH((byte)0, (byte)0, (byte)1, BlockFace.EnumAxis.field_2824),
   EAST((byte)1, (byte)0, (byte)0, BlockFace.EnumAxis.field_2822),
   WEST((byte)-1, (byte)0, (byte)0, BlockFace.EnumAxis.field_2822),
   TOP((byte)0, (byte)1, (byte)0, BlockFace.EnumAxis.field_2823),
   BOTTOM((byte)0, (byte)-1, (byte)0, BlockFace.EnumAxis.field_2823);

   private static final Map opposites = new HashMap();
   private final byte modX;
   private final byte modY;
   private final byte modZ;
   private final BlockFace.EnumAxis axis;

   BlockFace(byte modX, byte modY, byte modZ, BlockFace.EnumAxis axis) {
      this.modX = modX;
      this.modY = modY;
      this.modZ = modZ;
      this.axis = axis;
   }

   public BlockFace opposite() {
      return (BlockFace)opposites.get(this);
   }

   public byte getModX() {
      return this.modX;
   }

   public byte getModY() {
      return this.modY;
   }

   public byte getModZ() {
      return this.modZ;
   }

   public BlockFace.EnumAxis getAxis() {
      return this.axis;
   }

   static {
      opposites.put(NORTH, SOUTH);
      opposites.put(SOUTH, NORTH);
      opposites.put(EAST, WEST);
      opposites.put(WEST, EAST);
      opposites.put(TOP, BOTTOM);
      opposites.put(BOTTOM, TOP);
   }

   public
   enum EnumAxis {
      // $FF: renamed from: X com.viaversion.viaversion.api.minecraft.BlockFace$EnumAxis
      field_2822,
      // $FF: renamed from: Y com.viaversion.viaversion.api.minecraft.BlockFace$EnumAxis
      field_2823,
      // $FF: renamed from: Z com.viaversion.viaversion.api.minecraft.BlockFace$EnumAxis
      field_2824
   }
}
