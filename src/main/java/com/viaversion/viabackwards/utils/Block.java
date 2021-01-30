package com.viaversion.viabackwards.utils;

public class Block {
   // $FF: renamed from: id int
   private final int field_129;
   private final short data;

   public Block(int id, int data) {
      this.field_129 = id;
      this.data = (short)data;
   }

   public Block(int id) {
      this.field_129 = id;
      this.data = 0;
   }

   public int getId() {
      return this.field_129;
   }

   public int getData() {
      return this.data;
   }

   public Block withData(int data) {
      return new Block(this.field_129, data);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Block block = (Block)o;
         if (this.field_129 != block.field_129) {
            return false;
         } else {
            return this.data == block.data;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.field_129;
      result = 31 * result + this.data;
      return result;
   }
}
