package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;

public class ArrayType extends Type {
   private final Type elementType;

   public ArrayType(Type type) {
      super(type.getTypeName() + " Array", getArrayClass(type.getOutputClass()));
      this.elementType = type;
   }

   public static Class getArrayClass(Class componentType) {
      return Array.newInstance(componentType, 0).getClass();
   }

   public Object[] read(ByteBuf buffer) throws Exception {
      int amount = Type.VAR_INT.readPrimitive(buffer);
      Object[] array = (Object[])Array.newInstance(this.elementType.getOutputClass(), amount);

      for(int i = 0; i < amount; ++i) {
         array[i] = this.elementType.read(buffer);
      }

      return array;
   }

   public void write(ByteBuf buffer, Object[] object) throws Exception {
      Type.VAR_INT.writePrimitive(buffer, object.length);
      Object[] var3 = object;
      int var4 = object.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object o = var3[var5];
         this.elementType.write(buffer, o);
      }

   }
}
