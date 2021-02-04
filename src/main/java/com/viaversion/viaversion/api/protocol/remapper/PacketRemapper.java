package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public abstract class PacketRemapper {
   private final List valueRemappers = new ArrayList();

   protected PacketRemapper() {
      this.registerMap();
   }

   public void map(Type type) {
      this.handler((wrapper) -> {
         wrapper.write(type, wrapper.read(type));
      });
   }

   public void map(Type oldType, Type newType) {
      this.handler((wrapper) -> {
         wrapper.write(newType, wrapper.read(oldType));
      });
   }

   public void map(Type oldType, Type newType, final Function transformer) {
      this.map(oldType, new ValueTransformer(newType) {
         public Object transform(PacketWrapper wrapper, Object inputValue) throws Exception {
            return transformer.apply(inputValue);
         }
      });
   }

   public void map(ValueTransformer transformer) {
      if (transformer.getInputType() == null) {
         throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
      } else {
         this.map(transformer.getInputType(), transformer);
      }
   }

   public void map(Type oldType, ValueTransformer transformer) {
      this.map(new TypeRemapper(oldType), transformer);
   }

   public void map(ValueReader inputReader, ValueWriter outputWriter) {
      this.handler((wrapper) -> {
         outputWriter.write(wrapper, inputReader.read(wrapper));
      });
   }

   public void handler(PacketHandler handler) {
      this.valueRemappers.add(handler);
   }

   public void create(Type type, Object value) {
      this.handler((wrapper) -> {
         wrapper.write(type, value);
      });
   }

   public void read(Type type) {
      this.handler((wrapper) -> {
         wrapper.read(type);
      });
   }

   public abstract void registerMap();

   public void remap(PacketWrapper packetWrapper) throws Exception {
      try {
         Iterator var2 = this.valueRemappers.iterator();

         while(var2.hasNext()) {
            PacketHandler handler = (PacketHandler)var2.next();
            handler.handle(packetWrapper);
         }

      } catch (CancelException var4) {
         throw var4;
      } catch (InformativeException var5) {
         var5.addSource(this.getClass());
         throw var5;
      } catch (Exception var6) {
         InformativeException ex = new InformativeException(var6);
         ex.addSource(this.getClass());
         throw ex;
      }
   }
}
