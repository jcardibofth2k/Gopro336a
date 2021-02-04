package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatTag extends NumberTag {
   // $FF: renamed from: ID int
   public static final int field_49 = 5;
   private float value;

   public FloatTag() {
      this(0.0F);
   }

   public FloatTag(float value) {
      this.value = value;
   }

   /** @deprecated */
   @Deprecated
   public Float getValue() {
      return this.value;
   }

   public void setValue(float value) {
      this.value = value;
   }

   public void read(DataInput in) throws IOException {
      this.value = in.readFloat();
   }

   public void write(DataOutput out) throws IOException {
      out.writeFloat(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FloatTag floatTag = (FloatTag)o;
         return this.value == floatTag.value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Float.hashCode(this.value);
   }

   public final FloatTag clone() {
      return new FloatTag(this.value);
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
      return this.value;
   }

   public double asDouble() {
      return this.value;
   }

   public int getTagId() {
      return 5;
   }
}
