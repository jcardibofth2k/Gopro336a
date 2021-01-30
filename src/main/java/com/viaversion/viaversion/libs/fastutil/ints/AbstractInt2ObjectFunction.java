package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;

public abstract class AbstractInt2ObjectFunction implements Int2ObjectFunction, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;
   protected Object defRetValue;

   protected AbstractInt2ObjectFunction() {
   }

   public void defaultReturnValue(Object rv) {
      this.defRetValue = rv;
   }

   public Object defaultReturnValue() {
      return this.defRetValue;
   }
}
