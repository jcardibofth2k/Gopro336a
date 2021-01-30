package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.InformativeException;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ValueTransformer implements ValueWriter {
   private final Type inputType;
   private final Type outputType;

   protected ValueTransformer(@Nullable Type inputType, Type outputType) {
      this.inputType = inputType;
      this.outputType = outputType;
   }

   protected ValueTransformer(Type outputType) {
      this((Type)null, outputType);
   }

   public abstract Object transform(PacketWrapper var1, Object var2) throws Exception;

   public void write(PacketWrapper writer, Object inputValue) throws Exception {
      try {
         writer.write(this.outputType, this.transform(writer, inputValue));
      } catch (InformativeException var4) {
         var4.addSource(this.getClass());
         throw var4;
      }
   }

   @Nullable
   public Type getInputType() {
      return this.inputType;
   }

   public Type getOutputType() {
      return this.outputType;
   }
}
