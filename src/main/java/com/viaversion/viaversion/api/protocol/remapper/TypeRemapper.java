package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;

public class TypeRemapper implements ValueReader, ValueWriter {
   private final Type type;

   public TypeRemapper(Type type) {
      this.type = type;
   }

   public Object read(PacketWrapper wrapper) throws Exception {
      return wrapper.read(this.type);
   }

   public void write(PacketWrapper output, Object inputValue) {
      output.write(this.type, inputValue);
   }
}
