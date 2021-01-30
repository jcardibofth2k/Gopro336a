package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class ByteArrayTag extends Tag {
   // $FF: renamed from: ID int
   public static final int field_43 = 7;
   private byte[] value;

   public ByteArrayTag() {
      this(new byte[0]);
   }

   public ByteArrayTag(byte[] value) {
      this.value = value;
   }

   public byte[] getValue() {
      return this.value;
   }

   public void setValue(byte[] value) {
      if (value != null) {
         this.value = value;
      }
   }

   public byte getValue(int index) {
      return this.value[index];
   }

   public void setValue(int index, byte value) {
      this.value[index] = value;
   }

   public int length() {
      return this.value.length;
   }

   public void read(DataInput in) throws IOException {
      this.value = new byte[in.readInt()];
      in.readFully(this.value);
   }

   public void write(DataOutput out) throws IOException {
      out.writeInt(this.value.length);
      out.write(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ByteArrayTag that = (ByteArrayTag)o;
         return Arrays.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.value);
   }

   public final ByteArrayTag clone() {
      return new ByteArrayTag(this.value);
   }

   public int getTagId() {
      return 7;
   }
}
