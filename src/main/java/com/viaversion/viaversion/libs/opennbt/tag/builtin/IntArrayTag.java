package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class IntArrayTag extends Tag {
   // $FF: renamed from: ID int
   public static final int field_44 = 11;
   private int[] value;

   public IntArrayTag() {
      this(new int[0]);
   }

   public IntArrayTag(int[] value) {
      Preconditions.checkNotNull(value);
      this.value = value;
   }

   public int[] getValue() {
      return this.value;
   }

   public void setValue(int[] value) {
      Preconditions.checkNotNull(value);
      this.value = value;
   }

   public int getValue(int index) {
      return this.value[index];
   }

   public void setValue(int index, int value) {
      this.value[index] = value;
   }

   public int length() {
      return this.value.length;
   }

   public void read(DataInput in) throws IOException {
      this.value = new int[in.readInt()];

      for(int index = 0; index < this.value.length; ++index) {
         this.value[index] = in.readInt();
      }

   }

   public void write(DataOutput out) throws IOException {
      out.writeInt(this.value.length);
      int[] var2 = this.value;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int i = var2[var4];
         out.writeInt(i);
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IntArrayTag that = (IntArrayTag)o;
         return Arrays.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.value);
   }

   public final IntArrayTag clone() {
      return new IntArrayTag((int[])this.value.clone());
   }

   public int getTagId() {
      return 11;
   }
}
