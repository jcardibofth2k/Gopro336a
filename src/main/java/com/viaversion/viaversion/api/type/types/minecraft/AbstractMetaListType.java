package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMetaListType extends MetaListTypeTemplate {
   protected abstract Type getType();

   public List read(ByteBuf buffer) throws Exception {
      Type type = this.getType();
      ArrayList list = new ArrayList();

      Metadata meta;
      do {
         meta = (Metadata)type.read(buffer);
         if (meta != null) {
            list.add(meta);
         }
      } while(meta != null);

      return list;
   }

   public void write(ByteBuf buffer, List object) throws Exception {
      Type type = this.getType();
      Iterator var4 = object.iterator();

      while(var4.hasNext()) {
         Metadata metadata = (Metadata)var4.next();
         type.write(buffer, metadata);
      }

      this.writeEnd(type, buffer);
   }

   protected abstract void writeEnd(Type var1, ByteBuf var2) throws Exception;
}
