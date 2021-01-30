package com.viaversion.viaversion.api.minecraft;

public class BlockChangeRecord1_8 implements BlockChangeRecord {
   private final byte sectionX;
   // $FF: renamed from: y short
   private final short field_3216;
   private final byte sectionZ;
   private int blockId;

   public BlockChangeRecord1_8(byte sectionX, short y, byte sectionZ, int blockId) {
      this.sectionX = sectionX;
      this.field_3216 = y;
      this.sectionZ = sectionZ;
      this.blockId = blockId;
   }

   public BlockChangeRecord1_8(int sectionX, int y, int sectionZ, int blockId) {
      this((byte)sectionX, (short)y, (byte)sectionZ, blockId);
   }

   public byte getSectionX() {
      return this.sectionX;
   }

   public byte getSectionY() {
      return (byte)(this.field_3216 & 15);
   }

   public short getY(int chunkSectionY) {
      return this.field_3216;
   }

   public byte getSectionZ() {
      return this.sectionZ;
   }

   public int getBlockId() {
      return this.blockId;
   }

   public void setBlockId(int blockId) {
      this.blockId = blockId;
   }
}
