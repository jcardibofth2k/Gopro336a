package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;

public abstract class AbstractObject2IntFunction implements Object2IntFunction, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;
   protected int defRetValue;

   protected AbstractObject2IntFunction() {
   }

   public void defaultReturnValue(int rv) {
      this.defRetValue = rv;
   }

   public int defaultReturnValue() {
      return this.defRetValue;
   }
}
