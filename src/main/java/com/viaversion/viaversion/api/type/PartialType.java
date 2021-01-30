package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;

public abstract class PartialType extends Type {
   private final Object param;

   protected PartialType(Object param, Class type) {
      super(type);
      this.param = param;
   }

   protected PartialType(Object param, String name, Class type) {
      super(name, type);
      this.param = param;
   }

   public abstract Object read(ByteBuf var1, Object var2) throws Exception;

   public abstract void write(ByteBuf var1, Object var2, Object var3) throws Exception;

   public final Object read(ByteBuf buffer) throws Exception {
      return this.read(buffer, this.param);
   }

   public final void write(ByteBuf buffer, Object object) throws Exception {
      this.write(buffer, this.param, object);
   }
}
