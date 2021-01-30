package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DoubleTag extends NumberTag {
   // $FF: renamed from: ID int
   public static final int field_50 = 6;
   private double value;

   public DoubleTag() {
      this(0.0D);
   }

   public DoubleTag(double value) {
      this.value = value;
   }

   /** @deprecated */
   @Deprecated
   public Double getValue() {
      return this.value;
   }

   public void setValue(double value) {
      this.value = value;
   }

   public void read(DataInput in) throws IOException {
      this.value = in.readDouble();
   }

   public void write(DataOutput out) throws IOException {
      out.writeDouble(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DoubleTag doubleTag = (DoubleTag)o;
         return this.value == doubleTag.value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Double.hashCode(this.value);
   }

   public final DoubleTag clone() {
      return new DoubleTag(this.value);
   }

   public byte asByte() {
      return (byte)((int)this.value);
   }

   public short asShort() {
      return (short)((int)this.value);
   }

   public int asInt() {
      return (int)this.value;
   }

   public long asLong() {
      return (long)this.value;
   }

   public float asFloat() {
      return (float)this.value;
   }

   public double asDouble() {
      return this.value;
   }

   public int getTagId() {
      return 6;
   }
}
